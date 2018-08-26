package com.joshtalks.task.viewmodals

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.joshtalks.task.dao.DeletePost
import com.joshtalks.task.dao.InsertPost
import com.joshtalks.task.database.AppDataBase
import com.joshtalks.task.modals.EventResponse
import com.joshtalks.task.modals.Posts
import com.joshtalks.task.repositories.MainActivityRepository
import java.util.concurrent.CopyOnWriteArrayList

class MainActivityViewModal(private val mMainActivityRepository: MainActivityRepository,private val mAppDatabase: AppDataBase) : ViewModel() {
    fun getPostLiveData(apiKey: String): LiveData<EventResponse> {
        val mEventResponseLiveData = MutableLiveData<EventResponse>()
        mMainActivityRepository.getPosts(apiKey, object : MainActivityRepository.PostAPIListner<EventResponse, String> {
            override fun onSuccess(response: EventResponse) {
                mEventResponseLiveData.value = response
            }

            override fun fail(failed: String) {

            }
        })
        return mEventResponseLiveData
    }

    fun getPostLiveData(): LiveData<List<Posts>> {
        return mAppDatabase.postModal().getAllPosts()
    }
}