package com.coinstats.app.domain.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.paging.RemoteMediator
import com.coinstats.app.domain.model.Coin
import io.reactivex.Observable
import io.reactivex.Single

interface CoinsRepository {
    fun getCoins(page: Int): Observable<List<Coin>>
    fun searchCoins(keyword: String?): Single<List<Coin>>

    @OptIn(ExperimentalPagingApi::class)
    fun getRemoteMediator(): RemoteMediator<Int, Coin>
    fun getPagingSource(): PagingSource<Int, Coin>
}