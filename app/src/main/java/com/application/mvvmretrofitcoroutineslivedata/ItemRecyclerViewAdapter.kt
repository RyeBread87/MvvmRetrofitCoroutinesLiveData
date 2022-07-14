package com.application.mvvmretrofitcoroutineslivedata

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.application.mvvmretrofitcoroutineslivedata.databinding.FragmentItemBinding
import com.application.mvvmretrofitcoroutineslivedata.models.Item
import com.bumptech.glide.Glide

class ItemRecyclerViewAdapter constructor(private val onItemClickListener: OnItemClickListener):
    RecyclerView.Adapter<ItemRecyclerViewAdapter.ViewHolder>() {

    private var itemList = mutableListOf<Item>()
    private val deletedItems = HashMap<Int, Item>()

    fun hideItem(position: Int) {
        /*for (i in itemList.indices) {
            if (itemList[i].deleted) {
                deletedItems[position] = itemList[position]
            }
        }*/
        deletedItems[position] = itemList[position]
        itemList.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FragmentItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            ,onItemClickListener
        )
    }

    fun setItemList(items: List<Item>) {
        itemList = items.toMutableList()
        notifyDataSetChanged()      // TODO replace with DiffUtil
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.binding.name.text = item.name
        holder.binding.desc.text = item.desc
        Glide.with(holder.itemView.context).load(item.imageUrl).into(holder.binding.thumbnail)
        holder.itemView.setOnClickListener(holder)
        if (item.deleted) {
            holder.itemView.visibility = View.GONE
        }
        if (item.favorite) {
            holder.binding.favorite.setBackgroundColor(Color.GREEN)
        }
        holder.binding.delete.setOnClickListener {
            onItemClickListener.onDeleteClick(position)
        }
        holder.binding.favorite.setOnClickListener {
            onItemClickListener.onFavoriteClick(position)
        }
    }

    override fun getItemCount(): Int = itemList.size

    inner class ViewHolder(val binding: FragmentItemBinding, private val onItemClickListener: OnItemClickListener) : RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {

        override fun toString(): String {
            return super.toString() + " '" + binding.desc.text + "'"
        }

        override fun onClick(p0: View?) {
            onItemClickListener.onItemClick(bindingAdapterPosition)
        }
    }
}

interface OnItemClickListener {
    fun onItemClick(pos: Int)
    fun onDeleteClick(pos: Int)
    fun onFavoriteClick(pos: Int)
}