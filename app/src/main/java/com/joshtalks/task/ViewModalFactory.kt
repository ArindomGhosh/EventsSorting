package com.joshtalks.task

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.joshtalks.task.dao.ItemBoundaryCallback
import com.joshtalks.task.database.AppDataBase
import com.joshtalks.task.viewmodals.MainActivityViewModal

class ViewModalFactory(val mItemBoundaryCallback: ItemBoundaryCallback,
                       val mAppDataBase: AppDataBase) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            when (modelClass) {
                MainActivityViewModal::class.java -> MainActivityViewModal(mItemBoundaryCallback) as T
                else -> throw IllegalArgumentException("Unknown model class.")

            }
}