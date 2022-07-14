package com.application.mvvmretrofitcoroutineslivedata.repos

import com.application.mvvmretrofitcoroutineslivedata.models.Item
import com.application.mvvmretrofitcoroutineslivedata.network.RetrofitService
import retrofit2.Response

class ItemRepository constructor(private val retrofitService: RetrofitService) {

    suspend fun getAllThings(): Response<List<Item>> {
        return retrofitService.getAllItems()
    }
}