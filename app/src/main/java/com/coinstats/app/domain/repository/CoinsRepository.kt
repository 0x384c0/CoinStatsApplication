package com.coinstats.app.domain.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.paging.rxjava2.RxRemoteMediator
import com.coinstats.app.domain.model.Coin
import io.reactivex.Single

interface CoinsRepository {
    fun searchCoins(keyword: String?): Single<List<Coin>>

    @OptIn(ExperimentalPagingApi::class)
    fun getRemoteMediator(): RxRemoteMediator<Int, Coin>
    fun getPagingSource(): PagingSource<Int, Coin>
}