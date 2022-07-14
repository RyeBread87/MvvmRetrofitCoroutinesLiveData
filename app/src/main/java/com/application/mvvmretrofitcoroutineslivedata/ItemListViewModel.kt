package com.application.mvvmretrofitcoroutineslivedata

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.application.mvvmretrofitcoroutineslivedata.models.Item
import com.application.mvvmretrofitcoroutineslivedata.repos.ItemRepository
import kotlinx.coroutines.*

class ItemListViewModel constructor(private val repository: ItemRepository): ViewModel() {

    val itemList = MutableLiveData<List<Item>>()
    private val errorMessage = MutableLiveData<String>()        // TODO - show error message
    private val loading = MutableLiveData<Boolean>()            // TODO - show progress dialog
    private val handler = CoroutineExceptionHandler { _, exception ->
        println("CoroutineExceptionHandler got $exception")
    }

    fun getItemList() {
        loading.value = true
        CoroutineScope(Dispatchers.IO + handler).launch {                 // TODO - check if we're connected to network
            val response = repository.getAllThings()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    itemList.postValue(response.body())
                }
                else {
                    errorMessage.postValue(response.message())
                }
            }
            loading.postValue(false)
        }
    }
}