package com.womeiyouyuming.recyclerviewdroptest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.womeiyouyuming.recyclerviewdroptest.databinding.FragmentPhotoBinding


/**
 * A simple [Fragment] subclass.
 * Use the [PhotoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PhotoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentPhotoBinding>(inflater, R.layout.fragment_photo, container, false)
        val resId = arguments?.getInt("imageResId") ?: return null
        binding.imageResId = resId
        return binding.root

    }





}