package com.coinstats.app.data.repository.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.rxjava2.RxRemoteMediator
import com.coinstats.app.BuildConfig
import com.coinstats.app.data.source.local.AppDatabase
import com.coinstats.app.data.source.local.model.CoinRemoteKeys
import com.coinstats.app.data.source.remote.CoinStatsApi
import com.coinstats.app.domain.model.Coin
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

//TODO: move to datasource
@ExperimentalPagingApi
class PageKeyedRemoteMediator(
    private val db: AppDatabase,
    private val api: CoinStatsApi
) : RxRemoteMediator<Int, Coin>() {
    override fun loadSingle(
        loadType: LoadType,
        state: PagingState<Int, Coin>
    ): Single<MediatorResult> {
        return Single.just(loadType)
            .subscribeOn(Schedulers.io())
            .map {
                val result = when (it) {
                    LoadType.PREPEND -> INVALID_PAGE
                    LoadType.REFRESH -> 1
                    LoadType.APPEND -> {
                        val remoteKeys = getRemoteKeyForLastItem(state)
                        remoteKeys?.nextKey ?: 2
                    }
                }
                result
            }
            .flatMap { page ->

                if (page == INVALID_PAGE) {
                    Single.just(MediatorResult.Success(endOfPaginationReached = true))
                } else {
                    api.getCoins(
                        skip = (page - 1) * BuildConfig.ITEMS_PER_PAGE,
                        limit = BuildConfig.ITEMS_PER_PAGE
                    )
                        .map { it.coins }
                        .map { insertToDb(page, loadType, it) }
                        .map<MediatorResult> { MediatorResult.Success(endOfPaginationReached = it.isEmpty()) }
                        .onErrorReturn { MediatorResult.Error(it) }
                }

            }
            .onErrorReturn { MediatorResult.Error(it) }
    }

    @Suppress("DEPRECATION")
    private fun insertToDb(page: Int, loadType: LoadType, data: List<Coin>): List<Coin> {
        db.beginTransaction()

        try {
            if (loadType == LoadType.REFRESH) {
                db.coinRemoteKeysDao.deleteAll()
                db.coinDao.deleteAll()
            }

            val prevKey = if (page == 1) null else page - 1
            val nextKey = if (data.isEmpty()) null else page + 1
            val keys = data.map {
                CoinRemoteKeys(coinId = it.id, prevKey = prevKey, nextKey = nextKey)
            }
            db.coinRemoteKeysDao.insertAll(keys)
            db.coinDao.insertAll(data)
            db.setTransactionSuccessful()

        } finally {
            db.endTransaction()
        }

        return data
    }

    private fun getRemoteKeyForLastItem(state: PagingState<Int, Coin>): CoinRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { repo ->
            db.coinRemoteKeysDao.delete(repo.id)
        }
    }

    companion object {
        const val INVALID_PAGE = -1
    }

}