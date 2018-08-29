package com.joshtalks.task.views

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedList
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.joshtalks.task.R
import com.joshtalks.task.ViewModalFactory
import com.joshtalks.task.adapters.EventPostPagedListAdapter
import com.joshtalks.task.common.BaseActivity
import com.joshtalks.task.common.ImageLoader
import com.joshtalks.task.database.AppDataBase
import com.joshtalks.task.modals.Posts
import com.joshtalks.task.viewmodals.MainActivityViewModal
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.generic.instance

class MainActivity : BaseActivity(), EventPostPagedListAdapter.OnPostClickListener {

    private val mViewModalFactory: ViewModalFactory by instance()

    private val mMainActivityViewModal by lazy { ViewModelProviders.of(this, mViewModalFactory).get(MainActivityViewModal::class.java) }

    private val adapter by lazy { EventPostPagedListAdapter(ImageLoader(this)) }

    private val linearLayoutManager by lazy { LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false) }

    private val mAppDataBase: AppDataBase by instance()

    private val mDataBaseObserver = Observer<PagedList<Posts>> { it ->
        it?.let {
//            println("size ${it.size}")
            if (it.isEmpty()) Snackbar.make(postContainer, "No data to show now.", Snackbar.LENGTH_LONG).show()
            adapter.updatePostList(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "Posts"
        adapter.setOnPostClickListener(this)
        rvItems.adapter = adapter
        rvItems.layoutManager = linearLayoutManager
        progress.visibility = View.GONE

        mMainActivityViewModal.getPostLiveData(mAppDataBase.postModal().getAllPostsDataSource()).observe(this, mDataBaseObserver)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_activity, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.date -> {
                mMainActivityViewModal.getPostLiveData(mAppDataBase.postModal().getAllPostsDataSourceByDate()).observe(this, mDataBaseObserver)
                true
            }
            R.id.shares -> {
                mMainActivityViewModal.getPostLiveData(mAppDataBase.postModal().getAllPostsDataSourceByShares()).observe(this, mDataBaseObserver)
                true
            }
            R.id.likes -> {
                mMainActivityViewModal.getPostLiveData(mAppDataBase.postModal().getAllPostsDataSourceByLikes()).observe(this, mDataBaseObserver)
                true
            }
            R.id.views -> {
                mMainActivityViewModal.getPostLiveData(mAppDataBase.postModal().getAllPostsDataSourceByViews()).observe(this, mDataBaseObserver)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onPostClicked(mPost: Posts) {

    }
}
