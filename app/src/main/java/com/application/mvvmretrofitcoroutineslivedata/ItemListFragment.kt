package com.application.mvvmretrofitcoroutineslivedata

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.application.mvvmretrofitcoroutineslivedata.databinding.FragmentItemListBinding
import com.application.mvvmretrofitcoroutineslivedata.network.RetrofitService
import com.application.mvvmretrofitcoroutineslivedata.repos.ItemRepository

class ItemListFragment : Fragment(), OnItemClickListener {

    private lateinit var viewModel: ItemListViewModel
    private lateinit var binding: FragmentItemListBinding
    private val adapter = ItemRecyclerViewAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this, ItemListViewModelFactory(ItemRepository(RetrofitService.getInstance())))[ItemListViewModel::class.java]
        if (viewModel.itemList.value == null) {
            viewModel.getItemList()
        }
        viewModel.itemList.removeObservers(viewLifecycleOwner);
        viewModel.itemList.observe(viewLifecycleOwner) {
            adapter.setItemList(it)
        }
        binding = FragmentItemListBinding.inflate(inflater, container, false)
        binding.list.layoutManager = LinearLayoutManager(context)
        binding.list.adapter = this.adapter
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = ItemListFragment()
    }

    override fun onItemClick(pos: Int) {
        val detailFragment = ItemFragment.newInstance(viewModel.itemList.value!![pos].name, viewModel.itemList.value!![pos].desc, viewModel.itemList.value!![pos].imageUrl)
        (activity as MainActivity).pushFragment(detailFragment)
    }

    override fun onDeleteClick(pos: Int) {
        viewModel.itemList.value!![pos].deleted = true
        adapter.hideItem(pos)
    }

    override fun onFavoriteClick(pos: Int) {
        viewModel.itemList.value!![pos].favorite = true
    }
}