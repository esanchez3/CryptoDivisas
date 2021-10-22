package com.estiven.cryptodivisas.repository

import androidx.room.*
import com.estiven.cryptodivisas.data.model.Currency

@Dao
interface CurrencyDao {

    @Query("SELECT * FROM currencies")
    suspend fun getCurrencies(): List<Currency>

    @Query("SELECT * FROM currencies WHERE id_currency = :id_currency")
    suspend fun getFavorite(id_currency: Int): Currency

    @Insert
    suspend fun insert(currency: Currency)

    @Delete
    suspend fun delete(currency: Currency)

    @Update
    suspend fun update(currency: Currency)

    @Query("SELECT * FROM currencies where id_currency = :id")
    suspend fun getCurrency(id: Long): Currency
}