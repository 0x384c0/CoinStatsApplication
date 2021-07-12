package com.coinstats.app.data.source

import com.coinstats.app.domain.model.CoinResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CoinStatsApi {
    @GET("coins")
    fun getCoins(
        @Query("skip") skip: Int = 0,
        @Query("limit") limit: Int = 20
    ): Single<CoinResponse>
}