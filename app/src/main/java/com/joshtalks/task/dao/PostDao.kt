package com.joshtalks.task.dao

import android.arch.lifecycle.LiveData
import android.arch.paging.DataSource
import android.arch.persistence.room.*
import com.joshtalks.task.modals.Posts

@Dao
interface PostDao {

    @Query("select * from PostTable")
    fun getAllPostsDataSource(): DataSource.Factory<Int, Posts>

    @Query("select * from PostTable order by event_date desc")
    fun getAllPostsDataSourceByDate(): DataSource.Factory<Int, Posts>

    @Query("select * from PostTable order by views desc")
    fun getAllPostsDataSourceByViews(): DataSource.Factory<Int, Posts>

    @Query("select * from PostTable order by likes desc")
    fun getAllPostsDataSourceByLikes(): DataSource.Factory<Int, Posts>

    @Query("select * from PostTable order by shares desc")
    fun getAllPostsDataSourceByShares(): DataSource.Factory<Int, Posts>

    @Query("select * from PostTable")
    fun getAllPosts(): LiveData<List<Posts>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPost(mPosts: Posts)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPostAll(mPosts: List<Posts>)

    @Query("Delete from PostTable")
    fun deleteAll()



}