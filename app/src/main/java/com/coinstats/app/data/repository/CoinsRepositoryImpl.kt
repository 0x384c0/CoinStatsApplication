package com.coinstats.app.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.paging.RemoteMediator
import com.coinstats.app.BuildConfig
import com.coinstats.app.data.repository.paging.PageKeyedRemoteMediator
import com.coinstats.app.data.source.local.AppDatabase
import com.coinstats.app.data.source.remote.CoinStatsApi
import com.coinstats.app.domain.model.Coin
import com.coinstats.app.domain.repository.CoinsRepository
import io.reactivex.Observable
import io.reactivex.Single

class CoinsRepositoryImpl(
    private val dataBase: AppDatabase,
    private val coinStatsApi: CoinStatsApi
) : CoinsRepository {
    override fun getCoins(page: Int): Observable<List<Coin>> {
        val skip = page * BuildConfig.ITEMS_PER_PAGE
        val remoteSource = coinStatsApi
            .getCoins(skip = skip)
            .map { it.coins }
            .map {
                if (it.isNotEmpty())
                    dataBase.coinDao.createAll(it)
                it
            }
        val localSource = dataBase.coinDao.getAll()

        return Observable.concatDelayError(
            listOf(
                localSource.toObservable(),
                remoteSource.toObservable(),
            )
        )
    }

    /**
     * public CoinStats api does not support search by keywords, only private api does, so here only dataBase used
     */
    override fun searchCoins(keyword: String?): Single<List<Coin>> {
        return if (keyword.isNullOrBlank())
            dataBase.coinDao.getAll().toSingle()
        else
            dataBase.coinDao.search(keyword)
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getRemoteMediator(): RemoteMediator<Int, Coin> {
        return PageKeyedRemoteMediator(dataBase, coinStatsApi)
    }

    override fun getPagingSource(): PagingSource<Int, Coin> {
        return dataBase.coinDao.getPagingSource()
    }
}