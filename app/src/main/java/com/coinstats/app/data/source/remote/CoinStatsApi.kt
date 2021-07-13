package com.coinstats.app.data.source.remote

import com.coinstats.app.BuildConfig
import com.coinstats.app.domain.model.CoinResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CoinStatsApi {
    @GET("coins")
    fun getCoins(
        @Query("limit") limit: Int = BuildConfig.ITEMS_PER_PAGE
    ): Single<CoinResponse>
}