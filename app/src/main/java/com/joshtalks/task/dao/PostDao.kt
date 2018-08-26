package com.joshtalks.task.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.joshtalks.task.modals.Posts

@Dao
interface PostDao {

    @Query("select * from PostTable")
    fun getAllPosts(): LiveData<List<Posts>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPost(mPosts: Posts)

    @Query("Delete from PostTable")
    fun deleteAll()

}