package com.joshtalks.task.views

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.joshtalks.task.R
import com.joshtalks.task.ViewModalFactory
import com.joshtalks.task.adapters.EvenPostsAdapter
import com.joshtalks.task.adapters.PageinationListener
import com.joshtalks.task.common.BaseActivity
import com.joshtalks.task.common.ImageLoader
import com.joshtalks.task.common.NetworkUtil
import com.joshtalks.task.common.NetworkUtil.TYPE_NOT_CONNECTED
import com.joshtalks.task.dao.DeletePost
import com.joshtalks.task.dao.InsertPost
import com.joshtalks.task.database.AppDataBase
import com.joshtalks.task.modals.Posts
import com.joshtalks.task.repositories.KEY_ONE
import com.joshtalks.task.repositories.KEY_THREE
import com.joshtalks.task.repositories.KEY_TWO
import com.joshtalks.task.viewmodals.MainActivityViewModal
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.generic.instance
import java.util.concurrent.CopyOnWriteArrayList

enum class CompareParameter {
    Date, Likes, Views, Shares
}

class MainActivity : BaseActivity(), EvenPostsAdapter.OnPostClickListener {

    private val mViewModalFactory: ViewModalFactory by instance()

    private val mMainActivityViewModal by lazy { ViewModelProviders.of(this, mViewModalFactory).get(MainActivityViewModal::class.java) }

    private val adapter by lazy { EvenPostsAdapter(ImageLoader(this), emptyList()) }

    private val linearLayoutManager by lazy { LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false) }

    private var mEventList = CopyOnWriteArrayList<Posts>()

    private val mAppDataBase: AppDataBase by instance()


    private val mPaginationListener by lazy {
        object : PageinationListener(linearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                println("page $page")
                println("totalItemCount $totalItemsCount")
                when (page) {
                    1 -> {
                        getPosts(KEY_TWO)
                    }
                    2 -> {
                        getPosts(KEY_THREE)
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "Posts"
        adapter.setOnPostClickListener(this)
        rvItems.adapter = adapter
//        rvItems.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        rvItems.layoutManager = linearLayoutManager
        rvItems.addOnScrollListener(mPaginationListener)
        getPosts()
        srContent.setOnRefreshListener {
            srContent.isRefreshing = true
            getPosts()
        }
    }

    private fun getPosts(apiKEy: String = KEY_ONE) {
        if (NetworkUtil.getConnectivityStatus(this) == TYPE_NOT_CONNECTED) {
            progress.visibility = View.GONE
            Snackbar.make(postContainer, "Offline  mode.", Snackbar.LENGTH_LONG).show()
            srContent.isRefreshing = false
            getDataFromDatabase()
        } else {
            getDataFromAPI(apiKEy)
        }
    }

    private fun getDataFromDatabase() {
        mMainActivityViewModal.getPostLiveData(mAppDataBase).observe(this, Observer { it ->
            it?.let {
                if (!mEventList.isEmpty()) {
                    updateDataBase(mEventList)
                } else {
                    mEventList.addAll(it)
                }
                adapter.updatePostList(mEventList)
            }
        })
    }

    private fun getDataFromAPI(apiKEy: String) {
        mMainActivityViewModal.getPostLiveData(apiKEy).observe(this, Observer { it ->
            it?.let {
                progress.visibility = View.GONE
                srContent.isRefreshing = false
                if (it.posts.isEmpty())
                    Snackbar.make(postContainer, "No data available now", Snackbar.LENGTH_LONG).show()
                if (it.page == 1) {
                    mEventList.clear()
                    mPaginationListener.resetState()
                }
                mEventList.addAll(it.posts)
                adapter.updatePostList(mEventList)
            }
        })
    }

    private fun updateDataBase(mEventList: CopyOnWriteArrayList<Posts>) {
        DeletePost(mAppDataBase).execute()
        InsertPost(mAppDataBase).execute(mEventList)
    }

    private fun updateAdapter(list: List<Posts>, mCompareParameter: CompareParameter = CompareParameter.Date) {

        when (mCompareParameter) {
            CompareParameter.Date -> adapter.updatePostList(list.sortedWith(compareBy { it.event_date }))
            CompareParameter.Likes -> adapter.updatePostList(list.sortedWith(compareBy { it.likes }).reversed())
            CompareParameter.Shares -> adapter.updatePostList(list.sortedWith(compareBy { it.shares }).reversed())
            CompareParameter.Views -> adapter.updatePostList(list.sortedWith(compareBy { it.views }).reversed())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_activity, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.date -> {
                updateAdapter(mEventList, CompareParameter.Date)
                true
            }
            R.id.shares -> {
                updateAdapter(mEventList, CompareParameter.Shares)
                true
            }
            R.id.likes -> {
                updateAdapter(mEventList, CompareParameter.Likes)
                true
            }
            R.id.views -> {
                updateAdapter(mEventList, CompareParameter.Views)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onPostClicked(mPost: Posts) {

    }

    override fun onPause() {
        super.onPause()
        updateDataBase(mEventList)
        super.onBackPressed()
    }
}
