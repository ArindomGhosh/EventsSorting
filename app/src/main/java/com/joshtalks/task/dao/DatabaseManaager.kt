package com.joshtalks.task.dao

import android.os.AsyncTask
import com.joshtalks.task.database.AppDataBase
import com.joshtalks.task.modals.Posts
import java.util.concurrent.CopyOnWriteArrayList

class InsertPost(val mDatabase: AppDataBase) : AsyncTask<CopyOnWriteArrayList<Posts>, Void, Unit>() {
     override fun doInBackground(vararg params: CopyOnWriteArrayList <Posts>) {
        params[0].forEach { mDatabase.postModal().insertPost(it) }
    }
}

class DeletePost(val mDatabase: AppDataBase) : AsyncTask<Unit, Void, Unit>() {
    override fun doInBackground(vararg params: Unit?) {
        mDatabase.postModal().deleteAll()
    }
}