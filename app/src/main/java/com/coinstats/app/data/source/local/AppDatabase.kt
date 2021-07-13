package com.coinstats.app.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.coinstats.app.data.source.local.dao.CoinDao
import com.coinstats.app.domain.model.Coin

@Database(entities = [Coin::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract val coinDao: CoinDao

    companion object {
        const val DB_NAME = "CoinsDatabase.db"
    }
}