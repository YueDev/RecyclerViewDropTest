package com.womeiyouyuming.recyclerviewdroptest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.womeiyouyuming.recyclerviewdroptest.databinding.FragmentGalleryBinding

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


        //各种初始化recycleview和adapter 各种样板代码
        val photoAdapter = PhotoAdapter {
            findNavController().navigate(R.id.action_galleryFragment_to_photoFragment, bundleOf("imageResId" to it))
        }

        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = photoAdapter
        }

        val photoList = DataUtil.getPhotoList(requireContext())
        photoAdapter.submitList(photoList)


        //ItemTouchHelper的callback，拖动排序的实现
        val callback = object : ItemTouchHelper.Callback() {

            //拖动滑动的方向flag
            override fun getMovementFlags(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder
            ): Int {
                //拖动view的方向，位运算。由于使用了GridLayoutManager，因此四个方向都要用到。
                val dragFlag = ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
                //滑动的方向，类似滑动删除item那种，这里不用，给0
                val swipeFlag = 0
                return makeMovementFlags(dragFlag, swipeFlag)
            }


            //拖动事件的监听
            override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
            ): Boolean {
                // 记得先改变数据 再通知 adapter更新，否则会出现视图错误
                val bean1 = photoList[viewHolder.adapterPosition]
                photoList.remove(bean1)
                photoList.add(target.adapterPosition, bean1)
                photoAdapter.notifyItemMoved(viewHolder.adapterPosition, target.adapterPosition)
                return true
            }

            //滑动事件的监听，这里没有用到
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}

            //开始拖动/滑动的监听，这里给viewHolder加了一个选中效果
            override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
                super.onSelectedChanged(viewHolder, actionState)
                if (viewHolder == null) return
                (viewHolder as PhotoAdapter.PhotoHolder).setSelect(true)
            }

            //拖动/滑动结束的监听，取消viewHolder的选中效果
            override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
                super.clearView(recyclerView, viewHolder)
                (viewHolder as PhotoAdapter.PhotoHolder).setSelect(false)
            }

            //长按拖动滑动
            override fun isLongPressDragEnabled() = true
        }

        //ItemTouchHelper 绑定recyclerview
        val helper = ItemTouchHelper(callback)
        helper.attachToRecyclerView(binding.recyclerView)

    }

}