package com.coinstats.app.data.source.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CoinRemoteKeys")
data class CoinRemoteKeys(
    @PrimaryKey val coinId: String,
    val prevKey: Int?,
    val nextKey: Int?
)