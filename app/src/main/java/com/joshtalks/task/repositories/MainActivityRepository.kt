package com.joshtalks.task.repositories

import com.joshtalks.task.modals.EventResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityRepository {
    fun getPosts(apiKey:String,listener: PostAPIListner<EventResponse, String>) {
        ApiServices.getInteface()
                .postAPI(apiKey)
                .enqueue(object : Callback<EventResponse> {
                    override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                        listener.fail("Some error occured.")
                    }

                    override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>?) {
                        response?.body()?.let { listener.onSuccess(it) }
                    }
                })
    }

    interface PostAPIListner<T, S> {
        fun onSuccess(response: T)
        fun fail(failed: S)
    }
}