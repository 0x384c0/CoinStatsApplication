package com.coinstats.app.domain.usecase

import com.coinstats.app.domain.model.Coin
import com.coinstats.app.domain.repository.CoinsRepository
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class GetCoinsUseCase @Inject constructor(private val repository: CoinsRepository) {
    fun getCoins(): Observable<List<Coin>> {
        return repository.getCoins()
    }

    fun searchCoins(keyword: String?): Single<List<Coin>> {
        return repository.searchCoins(keyword)
    }
}