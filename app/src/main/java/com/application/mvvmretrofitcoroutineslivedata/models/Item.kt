package com.application.mvvmretrofitcoroutineslivedata.models

data class Item(val name: String, val desc: String, val imageUrl: String, var deleted: Boolean, var favorite: Boolean)