package com.joshtalks.task

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.joshtalks.task.repositories.MainActivityRepository
import com.joshtalks.task.viewmodals.MainActivityViewModal

class ViewModalFactory(val mMainActivityRepository: MainActivityRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            when (modelClass) {
                MainActivityViewModal::class.java -> MainActivityViewModal(mMainActivityRepository) as T
                else -> throw IllegalArgumentException("Unknown model class.")

            }
}