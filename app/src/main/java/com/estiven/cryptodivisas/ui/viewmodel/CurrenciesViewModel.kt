package com.estiven.cryptodivisas.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.estiven.cryptodivisas.data.model.Currencies
import com.estiven.cryptodivisas.repository.ApiRepository
import com.estiven.cryptodivisas.utils.Messages
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class CurrenciesViewModel @Inject constructor(private val apiRepository: ApiRepository) :
    ViewModel() {

    private var _loading = MutableLiveData<Boolean>()
    private var _message = MutableLiveData<String>()
    private var _refreshLayout = MutableLiveData<Boolean>()
    private var _list = MutableLiveData<Collection<Currencies>>()
    private var _key = MutableLiveData<String>()
    val loading: LiveData<Boolean> = _loading
    val message: LiveData<String> = _message
    val list: LiveData<Collection<Currencies>> = _list
    val refreshLayout: LiveData<Boolean> = _refreshLayout
    val key: LiveData<String> = _key

    fun getCurrencies(position: Int) {
        viewModelScope.launch {
            _loading.value = true
            _refreshLayout.value = false
            val currencies = apiRepository.getCurrencies()
            try {
                currencies.enqueue(object : Callback<Map<String, Currencies>> {
                    override fun onResponse(
                        call: Call<Map<String, Currencies>>,
                        response: Response<Map<String, Currencies>>
                    ) {
                        val list = response.body()?.values
                        _loading.value = false
                        _refreshLayout.value = false
                        _list.value = list ?: emptyList()
                        _key.value = response.body()?.keys?.elementAt(position)
                    }

                    override fun onFailure(call: Call<Map<String, Currencies>>, t: Throwable) {
                        _loading.value = false
                        _refreshLayout.value = false
                        _message.value = "${Messages.errorFatal} ${t.message}"
                    }
                })
            } catch (e: Exception) {
                _loading.value = false
                _refreshLayout.value = false
                _message.value = "${Messages.errorFatal} ${e.message}"
            }
        }
    }
}