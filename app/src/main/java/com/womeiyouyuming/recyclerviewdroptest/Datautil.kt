package com.womeiyouyuming.recyclerviewdroptest

import android.content.Context

/**
 * Created by Yue on 2020/11/16.
 */

object DataUtil {

    private var photoList: MutableList<PhotoBean>? = null


    fun getPhotoList(context: Context): MutableList<PhotoBean> {

        if (photoList == null) {
            photoList = mutableListOf()
            val resources = context.resources
            val packageName = context.packageName

            repeat(31) {
                val imageResId = resources.getIdentifier("img_$it", "mipmap", packageName)
                photoList?.add(PhotoBean(imageResId))
            }
        }

        return photoList!!
    }
}

