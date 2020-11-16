package com.womeiyouyuming.recyclerviewdroptest

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.womeiyouyuming.recyclerviewdroptest.databinding.ItemPhotoBinding


/**
 * Created by Yue on 2020/11/16.
 */
class PhotoAdapter(private val itemClick: (Int) -> Unit) : ListAdapter<PhotoBean, PhotoAdapter.PhotoHolder>(PhotoDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemPhotoBinding>(inflater, R.layout.item_photo, parent, false)
        return PhotoHolder(binding).also {
            binding.imageView.setOnClickListener { _ ->
                itemClick(getItem(it.adapterPosition).imgResId)
            }
        }
    }

    override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
        holder.bind(getItem(position))
    }


    object PhotoDiffCallback : DiffUtil.ItemCallback<PhotoBean>() {
        override fun areItemsTheSame(oldItem: PhotoBean, newItem: PhotoBean) = oldItem.imgResId == newItem.imgResId

        override fun areContentsTheSame(oldItem: PhotoBean, newItem: PhotoBean) = oldItem == newItem
    }

    class PhotoHolder(private val binding: ItemPhotoBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(photoBean: PhotoBean) {
            binding.photoBean = photoBean
        }

        fun setSelect(isSelect: Boolean) {
            binding.cardView.foreground = if (isSelect) ContextCompat.getDrawable(binding.cardView.context, R.drawable.item_select) else null
        }

    }

}