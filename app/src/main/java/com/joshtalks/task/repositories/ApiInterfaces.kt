package com.joshtalks.task.repositories

import com.joshtalks.task.modals.EventResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterfaces {
    @GET("{key}")
    fun postAPI(@Path("key") key:String): Call<EventResponse>
}