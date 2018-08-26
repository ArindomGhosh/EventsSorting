package com.joshtalks.task.repositories

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiServices {
    private fun createRetrofitClient() = Retrofit.Builder()
            .baseUrl("http://www.mocky.io/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    fun getInteface() = createRetrofitClient().create(ApiInterfaces::class.java)
}