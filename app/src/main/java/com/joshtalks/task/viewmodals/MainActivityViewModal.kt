package com.joshtalks.task.viewmodals

import android.arch.lifecycle.ViewModel
import android.arch.paging.DataSource
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.joshtalks.task.dao.ItemBoundaryCallback
import com.joshtalks.task.modals.Posts

class MainActivityViewModal(private val mItemBoundaryCallback: ItemBoundaryCallback) : ViewModel() {

    private val config = PagedList.Config.Builder()
            .setPageSize(8)
            .setEnablePlaceholders(true)
            .build()

    fun getPostLiveData(mQuey:DataSource.Factory<Int,Posts>) = LivePagedListBuilder(mQuey, config)
            .setBoundaryCallback(mItemBoundaryCallback)
            .build()

}