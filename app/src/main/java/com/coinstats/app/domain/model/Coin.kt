package com.coinstats.app.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Coin")
data class Coin(
        @PrimaryKey
        val id: String,
        val icon: String,
        val name: String,
        val price: Double
) {
    override fun equals(other: Any?): Boolean {
        return (other as? Coin)?.id == id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}