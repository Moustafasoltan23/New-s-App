package com.example.mynews.data.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynews.data.network.RetrtofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    val _newResponse = MutableStateFlow<NewsResponse?>(null)
    val newsResponse : StateFlow<NewsResponse?> = _newResponse
    val api = RetrtofitInstance.apiClient

    fun getNews(topic: String){
        viewModelScope.launch {
            val response = api.everytgingsapi(q = topic , apiKey = "55dba45065ca47d8b85f4a54fe32365a")
            if (response.isSuccessful){
                _newResponse.value = response.body()
            }
        }
    }


}