package com.joshtalks.task.database

import android.content.Context
import android.content.Context.MODE_PRIVATE

class GlobalSharedPreferance(mContext: Context) {
    private val sharedPrefName = "JOSH_PREF"
    private var mGlobalSharedPreferance = mContext.applicationContext.getSharedPreferences(sharedPrefName, MODE_PRIVATE)

    fun putScrollID(Key: String) {
        mGlobalSharedPreferance.edit()
                .putString("SCROLL_KEY", Key)
                .apply()
    }

    fun getScrollKey() = mGlobalSharedPreferance.getString("SCROLL_KEY", "")
}