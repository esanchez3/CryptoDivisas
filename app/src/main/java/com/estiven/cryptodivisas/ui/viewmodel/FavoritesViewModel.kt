package com.estiven.cryptodivisas.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.estiven.cryptodivisas.CurrenciesApplication
import com.estiven.cryptodivisas.data.model.Currency
import kotlinx.coroutines.launch

class FavoritesViewModel : ViewModel() {

    private var _list = MutableLiveData<List<Currency>>()
    val list: LiveData<List<Currency>> = _list

    fun getFavorites() {
        viewModelScope.launch {
            val list = CurrenciesApplication.database.currencyDao().getCurrencies()
            _list.value = list
        }
    }
}