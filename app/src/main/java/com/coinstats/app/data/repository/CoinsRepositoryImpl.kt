package com.coinstats.app.data.repository

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
    override fun getCoins(): Observable<List<Coin>> {
        val remoteSource = coinStatsApi
            .getCoins()
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
}