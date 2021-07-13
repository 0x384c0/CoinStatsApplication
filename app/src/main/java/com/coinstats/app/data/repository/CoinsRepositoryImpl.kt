package com.coinstats.app.data.repository

import com.coinstats.app.data.source.CoinStatsApi
import com.coinstats.app.data.source.local.AppDatabase
import com.coinstats.app.domain.model.Coin
import com.coinstats.app.domain.repository.CoinsRepository
import io.reactivex.Observable

class CoinsRepositoryImpl(
    private val database: AppDatabase,
    private val coinStatsApi: CoinStatsApi
): CoinsRepository {
    override fun getCoins(searchQuery: String?): Observable<List<Coin>> {
        return coinStatsApi
            .getCoins()
            .map { it.coins }
            .toObservable()
    }
}