package com.estiven.cryptodivisas.data.api

import com.estiven.cryptodivisas.data.model.Currencies
import com.estiven.cryptodivisas.data.model.Operations
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    // get all currencies
    @GET("currency")
    fun getCurrencies(): Call<Map<String, Currencies>>

    // get currency
    @GET("trades")
    fun getSymbols(
        @Query("symbol") symbol: String
    ): Call<Map<String, List<Operations>>>

}