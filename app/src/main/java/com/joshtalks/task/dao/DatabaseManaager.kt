package com.joshtalks.task.dao

import com.joshtalks.task.database.AppDataBase
import com.joshtalks.task.modals.Posts
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class DatabaseManager(private val mAppDataBase: AppDataBase) {
    private val ioExecutor: Executor= Executors.newSingleThreadExecutor()
    fun insertData(mPosts: List<Posts>) {
        ioExecutor.execute {
            mAppDataBase.postModal().insertPostAll(mPosts)
        }
    }

    fun deleteAll() {
        ioExecutor.execute {
            mAppDataBase.postModal().deleteAll()
        }
    }
}