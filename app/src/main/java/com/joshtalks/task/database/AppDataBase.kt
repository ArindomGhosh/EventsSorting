package com.joshtalks.task.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.joshtalks.task.dao.PostDao
import com.joshtalks.task.modals.Posts

@Database(entities = [Posts::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {
    abstract fun postModal(): PostDao

    companion object {
        private var INSTANCE: AppDataBase? = null
        private val DATABASE_NAME = "JosTalks.db"
        fun getInMemoryDatabase(context: Context): AppDataBase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.applicationContext, AppDataBase::class.java, DATABASE_NAME)
                        // To simplify the codelab, allow queries on the main thread.
                        // Don't do this on a real app! See PersistenceBasicSample for an example.
//                        .allowMainThreadQueries()
                        .build()
            }
            return INSTANCE as AppDataBase
        }
    }

    fun destroyInstance() {
        INSTANCE = null
    }
}