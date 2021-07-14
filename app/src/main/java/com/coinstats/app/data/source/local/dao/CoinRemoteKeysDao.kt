package com.coinstats.app.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.coinstats.app.data.source.local.model.CoinRemoteKeys

@Dao
interface CoinRemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(remoteKey: List<CoinRemoteKeys>)

    @Query("SELECT * FROM CoinRemoteKeys WHERE coinId = :coinId")
    fun remoteKeysByCoinId(coinId: String): CoinRemoteKeys?

    @Query("DELETE FROM CoinRemoteKeys")
    fun clearRemoteKeys()
}