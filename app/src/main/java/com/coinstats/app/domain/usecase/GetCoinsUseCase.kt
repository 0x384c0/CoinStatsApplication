package com.coinstats.app.domain.usecase

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.paging.RemoteMediator
import com.coinstats.app.domain.model.Coin
import com.coinstats.app.domain.repository.CoinsRepository
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class GetCoinsUseCase @Inject constructor(private val repository: CoinsRepository) {
    @OptIn(ExperimentalPagingApi::class)
    fun getRemoteMediator(): RemoteMediator<Int, Coin> {
        return repository.getRemoteMediator()
    }

    fun getPagingSource(): PagingSource<Int, Coin> {
        return repository.getPagingSource()
    }

    fun getCoins(page: Int): Observable<List<Coin>> {
        return repository.getCoins(page)
    }

    fun searchCoins(keyword: String?): Single<List<Coin>> {
        return repository.searchCoins(keyword)
    }
}