package com.joshtalks.task.adapters

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.joshtalks.task.R
import com.joshtalks.task.common.ImageLoader
import com.joshtalks.task.common.Utility
import com.joshtalks.task.databinding.ItemEventBinding
import com.joshtalks.task.modals.Posts

class EvenPostsAdapter(private val mImageLoader: ImageLoader, private var mPostList: List<Posts>) : RecyclerView.Adapter<EvenPostsAdapter.PostviewHolder>() {
    private var mPostClickListener: OnPostClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostviewHolder {
        val mBinding = DataBindingUtil.inflate<ItemEventBinding>(LayoutInflater.from(parent.context), R.layout.item_event, parent, false)
        return PostviewHolder(mBinding)
    }

    override fun getItemCount() = mPostList.size

    override fun onBindViewHolder(holder: PostviewHolder, position: Int) {
        holder.bind(mPostList[position])
        holder.mBinding.root.setOnClickListener { mPostClickListener?.onPostClicked(mPostList[position]) }
    }

    inner class PostviewHolder(val mBinding: ItemEventBinding) : RecyclerView.ViewHolder(mBinding.root) {
        fun bind(mPost: Posts) {
            mBinding.mUtility = Utility
            mBinding.mEvent = mPost
            mImageLoader.loadImage(mPost.thumbnail_image, mBinding.ivEvent)
        }
    }

    fun updatePostList(mPostList: List<Posts>) {
        this.mPostList = mPostList
        notifyDataSetChanged()
    }

    fun setOnPostClickListener(mPostClickListener: OnPostClickListener) {
        this.mPostClickListener = mPostClickListener
    }

    interface OnPostClickListener {
        fun onPostClicked(mPost: Posts)
    }
}