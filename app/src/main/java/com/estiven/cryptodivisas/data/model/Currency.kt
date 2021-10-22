package com.estiven.cryptodivisas.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currencies")
data class Currency(
    @PrimaryKey
    @ColumnInfo(name = "id_currency")
    val id: Int = 0,
    @ColumnInfo(name = "network")
    val network: String? = "",
    @ColumnInfo(name = "favorite")
    val favorite: Boolean = false
)
