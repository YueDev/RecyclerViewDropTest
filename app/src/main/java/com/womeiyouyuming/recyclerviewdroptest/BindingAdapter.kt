package com.womeiyouyuming.recyclerviewdroptest


import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

/**
 * Created by Yue on 2020/11/16.
 */
@BindingAdapter("app:setImgResId")
fun setImageResWithGlide(imageView: ImageView, @DrawableRes imgResId: Int) {
    Glide.with(imageView).load(imgResId).into(imageView)
}