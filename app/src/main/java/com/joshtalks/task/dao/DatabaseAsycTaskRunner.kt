package com.joshtalks.task.dao

import android.os.AsyncTask
import com.joshtalks.task.database.AppDataBase
import com.joshtalks.task.modals.Posts
import com.joshtalks.task.repositories.MainActivityRepository
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class InsertPost(val mDatabase: AppDataBase) : AsyncTask<List<Posts>, Void, Unit>() {
    override fun doInBackground(vararg params: List<Posts>) {
        params[0].forEach { mDatabase.postModal().insertPost(it) }
    }
}

class DeletePost(val mDatabase: AppDataBase) : AsyncTask<Unit, Void, Unit>() {
    override fun doInBackground(vararg params: Unit?) {
        mDatabase.postModal().deleteAll()
    }
}