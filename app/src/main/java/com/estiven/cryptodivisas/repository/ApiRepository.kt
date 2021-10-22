package com.estiven.cryptodivisas.repository

import com.estiven.cryptodivisas.data.api.ApiService
import javax.inject.Inject

class ApiRepository @Inject constructor(private val apiService: ApiService) {

    fun getCurrencies() = apiService.getCurrencies()
    fun getSymbols(symbol: String) = apiService.getSymbols(symbol)
}