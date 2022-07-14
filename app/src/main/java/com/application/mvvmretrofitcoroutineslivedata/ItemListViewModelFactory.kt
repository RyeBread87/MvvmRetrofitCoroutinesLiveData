package com.application.mvvmretrofitcoroutineslivedata

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.application.mvvmretrofitcoroutineslivedata.repos.ItemRepository
import java.lang.IllegalArgumentException

class ItemListViewModelFactory constructor(private val repository: ItemRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ItemListViewModel::class.java)) {
            return ItemListViewModel(repository) as T
        }
        else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}