package com.example.imagegallery.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.imagegallery.databinding.ItemCategoryBinding
import com.example.imagegallery.listener.ItemClickedListener
import java.util.*

class ImageCategoryAdapter(private var list: List<String>,
                           private var itemClickedListener: ItemClickedListener)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding : ItemCategoryBinding = ItemCategoryBinding.inflate(
            layoutInflater,
            parent,
            false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CategoryViewHolder){
            holder.textView.text = list[position].uppercase(Locale.getDefault())

            holder.itemView.setOnClickListener {
                itemClickedListener.onItemClicked(list[position])
            }
        }
    }

    class CategoryViewHolder(binding: ItemCategoryBinding)
        : RecyclerView.ViewHolder(binding.root) {
        val textView = binding.textCategory
    }

    override fun getItemCount(): Int {
       return list.size
    }
}