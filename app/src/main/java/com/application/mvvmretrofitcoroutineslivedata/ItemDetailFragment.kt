package com.application.mvvmretrofitcoroutineslivedata

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.application.mvvmretrofitcoroutineslivedata.databinding.FragmentItemDetailBinding
import com.bumptech.glide.Glide

class ItemFragment : Fragment() {

    private var name: String? = null
    private var desc: String? = null
    private var imageUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            name = it.getString("name")
            desc = it.getString("desc")
            imageUrl = it.getString("imageUrl")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding = FragmentItemDetailBinding.inflate(layoutInflater, container, false)
        binding.nameDetail.text = name
        binding.descDetail.text = desc
        Glide.with(requireContext()).load(imageUrl).into(binding.imageView)

        return binding.root
    }

    companion object {
        @JvmStatic fun newInstance(param1: String, param2: String, param3: String) =
            ItemFragment().apply {
                arguments = Bundle().apply {
                    putString("name", param1)
                    putString("desc", param2)
                    putString("imageUrl", param3)
                }
            }
    }
}