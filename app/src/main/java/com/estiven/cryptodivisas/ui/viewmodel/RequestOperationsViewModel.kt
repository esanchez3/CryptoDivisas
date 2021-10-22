package com.estiven.cryptodivisas.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.estiven.cryptodivisas.data.model.Operations
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
class RequestOperationsViewModel @Inject constructor(private val apiRepository: ApiRepository) :
    ViewModel() {

    private var _loading = MutableLiveData<Boolean>()
    private var _message = MutableLiveData<String>()
    private var _key = MutableLiveData<String>()
    private var _list = MutableLiveData<Collection<Operations>>()
    val list: LiveData<Collection<Operations>> = _list
    val loading: LiveData<Boolean> = _loading
    val message: LiveData<String> = _message

    fun requestOperations(symbol: String, position: Int) {
        viewModelScope.launch {
            _loading.value = true
            val currencies = apiRepository.getSymbols(symbol)
            try {
                currencies.enqueue(object : Callback<Map<String, List<Operations>>> {
                    override fun onResponse(
                        call: Call<Map<String, List<Operations>>>,
                        response: Response<Map<String, List<Operations>>>
                    ) {
                        response.body()?.mapValues { listOperations ->
                            _list.value = listOperations.value
                        }
                        _loading.value = false
                        _key.value = response.body()?.keys?.elementAt(position)
                    }

                    override fun onFailure(
                        call: Call<Map<String, List<Operations>>>,
                        t: Throwable
                    ) {
                        _loading.value = false
                        _message.value = "${Messages.errorFatal} ${t.message}"

                    }
                })
            } catch (e: Exception) {
                _loading.value = false
                _message.value = "${Messages.errorFatal} ${e.message}"
            }
        }
    }
}