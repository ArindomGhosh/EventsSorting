package com.joshtalks.task.dao

import android.arch.paging.PagedList
import android.text.TextUtils
import com.joshtalks.task.database.GlobalSharedPreferance
import com.joshtalks.task.modals.EventResponse
import com.joshtalks.task.modals.Posts
import com.joshtalks.task.repositories.KEY_ONE
import com.joshtalks.task.repositories.KEY_THREE
import com.joshtalks.task.repositories.KEY_TWO
import com.joshtalks.task.repositories.MainActivityRepository
import java.util.concurrent.Executors

class ItemBoundaryCallback(private val mMainActivityRepository: MainActivityRepository,
                           private val mDatabaseManager: DatabaseManager,
                           private val mGlobalSharedPreferance: GlobalSharedPreferance) : PagedList.BoundaryCallback<Posts>() {
    private var isLoading = false
    private var apiKey = mGlobalSharedPreferance.getScrollKey()

    override fun onItemAtEndLoaded(itemAtEnd: Posts) {
        if (TextUtils.isEmpty(apiKey)) return
        if (isLoading) return
        isLoading = true
        mMainActivityRepository.getPosts(apiKey, object : MainActivityRepository.PostAPIListner<EventResponse, String> {
            override fun onSuccess(response: EventResponse) {
                isLoading = false
                setNextPage(response)
                mDatabaseManager.insertData(response.posts)
            }

            override fun fail(failed: String) {

            }
        })
    }

    fun setNextPage(response: EventResponse) {
        apiKey = when (response.page) {
            1 -> KEY_TWO
            2 -> KEY_THREE
            else -> ""
        }
        mGlobalSharedPreferance.putScrollID(apiKey)
    }

    override fun onItemAtFrontLoaded(itemAtFront: Posts) {
//        println("at Front ${itemAtFront.event_name}")
//        println("at front ${itemAtFront.primaryKey}")
    }

    override fun onZeroItemsLoaded() {
        mMainActivityRepository.getPosts(KEY_ONE, object : MainActivityRepository.PostAPIListner<EventResponse, String> {
            override fun onSuccess(response: EventResponse) {
                Executors.newSingleThreadExecutor().execute {
                    mDatabaseManager.insertData(response.posts)
                    setNextPage(response)
                    mGlobalSharedPreferance.putScrollID(KEY_TWO)
                }
            }

            override fun fail(failed: String) {

            }
        })
    }
}