package com.coinstats.app.domain.usecase

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.paging.rxjava2.RxRemoteMediator
import com.coinstats.app.domain.model.Coin
import com.coinstats.app.domain.repository.CoinsRepository
import io.reactivex.Single
import javax.inject.Inject

/**
 * UseCase for showing and searching Coins
 */
class GetCoinsUseCase @Inject constructor(private val repository: CoinsRepository) {
    /**
     * get remote mediator for Paging Library
     */
    @OptIn(ExperimentalPagingApi::class)
    fun getRemoteMediator(): RxRemoteMediator<Int, Coin> {
        return repository.getRemoteMediator()
    }

    /**
     * get PagingSource for Paging Library
     */
    fun getPagingSource(): PagingSource<Int, Coin> {
        return repository.getPagingSource()
    }

    /**
     * perform a search within a database
     *
     * @property keyword search query
     */
    fun searchCoins(keyword: String?): Single<List<Coin>> {
        return repository.searchCoins(keyword)
    }
}