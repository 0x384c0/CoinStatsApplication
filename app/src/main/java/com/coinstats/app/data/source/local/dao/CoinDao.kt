package com.coinstats.app.data.source.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.coinstats.app.domain.model.Coin
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface CoinDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createAll(objects: List<Coin>)  //TODO: rename to insert all

    @Query("DELETE FROM Coin")
    fun deleteAll()

    @Query("SELECT * FROM Coin")
    fun getAll(): Maybe<List<Coin>>

    @Query("SELECT * FROM Coin WHERE name LIKE '%' || :search || '%'")
    fun search(search: String): Single<List<Coin>>

    @Query("SELECT * FROM Coin")
    fun getAllPaging(): PagingSource<Int, Coin>
}