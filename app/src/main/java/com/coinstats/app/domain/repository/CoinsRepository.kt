package com.coinstats.app.domain.repository

import com.coinstats.app.domain.model.Coin
import io.reactivex.Observable

interface CoinsRepository {
    fun getCoins(searchQuery:String?): Observable<List<Coin>>
}