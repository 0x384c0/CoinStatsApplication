package com.coinstats.app.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.paging.rxjava2.RxRemoteMediator
import com.coinstats.app.data.repository.paging.PageKeyedRemoteMediator
import com.coinstats.app.data.source.local.AppDatabase
import com.coinstats.app.data.source.remote.CoinStatsApi
import com.coinstats.app.domain.model.Coin
import com.coinstats.app.domain.repository.CoinsRepository
import io.reactivex.Single

/**
 * Implementation of CoinsRepository
 */
class CoinsRepositoryImpl(
    private val dataBase: AppDatabase,
    private val coinStatsApi: CoinStatsApi
) : CoinsRepository {
    /**
     * get PagingSource for Paging Library
     */
    @OptIn(ExperimentalPagingApi::class)
    override fun getRemoteMediator(): RxRemoteMediator<Int, Coin> {
        return PageKeyedRemoteMediator(dataBase, coinStatsApi)
    }

    /**
     * get remote mediator for Paging Library
     */
    override fun getPagingSource(): PagingSource<Int, Coin> {
        return dataBase.coinDao.getAllPagingSource()
    }

    /**
     * perform a search within a database
     * public CoinStats api does not support search by keywords, only private api does, so here only dataBase used
     *
     * @property keyword search query
     */
    override fun searchCoins(keyword: String?): Single<List<Coin>> {
        return if (keyword.isNullOrBlank())
            dataBase.coinDao.getAll().toSingle()
        else
            dataBase.coinDao.search(keyword)
    }
}