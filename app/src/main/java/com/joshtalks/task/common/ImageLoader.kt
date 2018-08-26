package com.joshtalks.task.common

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

class ImageLoader(mContext:Context){
    private val mGlideObject=Glide.with(mContext)
    fun loadImage(url:String,mImageView:ImageView) {
        mGlideObject.load(url)
                .into(mImageView)
    }
}