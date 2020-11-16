package com.womeiyouyuming.recyclerviewdroptest

import android.graphics.Canvas
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.womeiyouyuming.recyclerviewdroptest.databinding.FragmentGalleryBinding
import java.util.*

class GalleryFragment : Fragment() {

    private lateinit var binding: FragmentGalleryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_gallery, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val photoAdapter = PhotoAdapter {
            findNavController().navigate(R.id.action_galleryFragment_to_photoFragment, bundleOf("imageResId" to it))
        }

        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = photoAdapter
        }


        val photoList = DataUtil.getPhotoList(requireContext())
        photoAdapter.submitList(photoList)

        val callback = object : ItemTouchHelper.Callback() {

            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                val dragFlag = ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
                val swipeFlag = 0
                return makeMovementFlags(dragFlag, swipeFlag)
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {

                // 记得先改变数据 再通知 adpater更新，否则会出现视图错误
                val bean1 = photoList[viewHolder.adapterPosition]
                photoList.remove(bean1)
                photoList.add(target.adapterPosition, bean1)

                photoAdapter.notifyItemMoved(viewHolder.adapterPosition, target.adapterPosition)

                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

            }

            override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
                super.onSelectedChanged(viewHolder, actionState)
                if (viewHolder == null) return
                (viewHolder as PhotoAdapter.PhotoHolder).setSelect(true)
            }

            override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
                super.clearView(recyclerView, viewHolder)
                (viewHolder as PhotoAdapter.PhotoHolder).setSelect(false)

            }

            override fun isLongPressDragEnabled() = true
        }

        val helper = ItemTouchHelper(callback)

        helper.attachToRecyclerView(binding.recyclerView)

    }

}