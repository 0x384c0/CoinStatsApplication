package com.coinstats.app.domain.repository

import com.coinstats.app.domain.model.Coin
import io.reactivex.Observable
import io.reactivex.Single

interface CoinsRepository {
    fun getCoins(page: Int): Observable<List<Coin>>
    fun searchCoins(keyword: String?): Single<List<Coin>>
}