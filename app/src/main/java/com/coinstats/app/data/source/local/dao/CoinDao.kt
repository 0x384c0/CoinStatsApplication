package com.coinstats.app.data.source.local.dao

import androidx.room.*
import com.coinstats.app.domain.model.Coin
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface CoinDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createAll(objects: List<Coin>)

    @Delete
    fun deleteAll(objects: List<Coin>)

    @Query("SELECT * FROM Coin")
    fun getAll(): Maybe<List<Coin>>

    @Query("SELECT * FROM Coin WHERE name LIKE '%' || :search || '%'")
    fun search(search: String): Single<List<Coin>>
}