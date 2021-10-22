package com.estiven.cryptodivisas.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import com.estiven.cryptodivisas.data.model.Currency

@Database(
    entities = [Currency::class],
    version = 1
)
abstract class CurrenciesDatabase : RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao
}