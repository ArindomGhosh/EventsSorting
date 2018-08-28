package com.joshtalks.task.adapters

import android.arch.paging.PagedList
import android.arch.paging.PagedListAdapter
import android.databinding.DataBindingUtil
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.joshtalks.task.R
import com.joshtalks.task.common.ImageLoader
import com.joshtalks.task.common.Utility
import com.joshtalks.task.databinding.ItemEventBinding
import com.joshtalks.task.modals.Posts

class EventPostPagedListAdapter(private val mImageLoader: ImageLoader) : PagedListAdapter<Posts, EventPostPagedListAdapter.PostViewHolder>(REPO_COMPARATOR) {
    private var mPostClickListener: OnPostClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val mBinding = DataBindingUtil.inflate<ItemEventBinding>(LayoutInflater.from(parent.context), R.layout.item_event, parent, false)
        return PostViewHolder(mBinding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        getItem(position)?.let { it ->
            holder.bind(it)
        }
        holder.mBinding.root.setOnClickListener { mPostClickListener?.onPostClicked(getItem(position)!!) }
    }

    inner class PostViewHolder(val mBinding: ItemEventBinding) : RecyclerView.ViewHolder(mBinding.root) {
        fun bind(mPost: Posts) {
            mBinding.mUtility = Utility
            mBinding.mEvent = mPost
            mImageLoader.loadImage(mPost.thumbnail_image, mBinding.ivEvent)
        }
    }

    fun updatePostList(mPostList: PagedList<Posts>) {
        this.submitList(mPostList)
    }

    fun setOnPostClickListener(mPostClickListener: OnPostClickListener) {
        this.mPostClickListener = mPostClickListener
    }

    interface OnPostClickListener {
        fun onPostClicked(mPost: Posts)
    }

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<Posts>() {
            override fun areItemsTheSame(oldItem: Posts, newItem: Posts): Boolean =
                    oldItem.event_name == newItem.event_name

            override fun areContentsTheSame(oldItem: Posts, newItem: Posts): Boolean =
                    oldItem == newItem
        }
    }
}