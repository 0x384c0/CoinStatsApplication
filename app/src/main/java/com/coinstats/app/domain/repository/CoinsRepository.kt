package com.coinstats.app.domain.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.paging.rxjava2.RxRemoteMediator
import com.coinstats.app.domain.model.Coin
import io.reactivex.Single

/**
 * this interface describes the source of Coins data
 */
interface CoinsRepository {
    /**
     * get remote mediator for Paging Library
     */
    @OptIn(ExperimentalPagingApi::class)
    fun getRemoteMediator(): RxRemoteMediator<Int, Coin>

    /**
     * get PagingSource for Paging Library
     */
    fun getPagingSource(): PagingSource<Int, Coin>

    /**
     * perform a search within a database
     *
     * @property keyword search query
     */
    fun searchCoins(keyword: String?): Single<List<Coin>>
}