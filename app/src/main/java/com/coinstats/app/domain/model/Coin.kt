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
)