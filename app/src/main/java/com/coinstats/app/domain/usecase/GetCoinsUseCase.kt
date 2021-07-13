package com.coinstats.app.domain.usecase

import com.coinstats.app.domain.model.Coin
import com.coinstats.app.domain.repository.CoinsRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetCoinsUseCase @Inject constructor(private val repository: CoinsRepository){
    fun getCoins(searchQuery:String?): Observable<List<Coin>> {
        return repository.getCoins(searchQuery)
    }
}