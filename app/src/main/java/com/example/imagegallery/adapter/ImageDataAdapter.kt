package com.example.imagegallery.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.imagegallery.R
import com.example.imagegallery.databinding.ItemImageBinding
import com.example.imagegallery.entity.Image
import com.example.imagegallery.listener.ImageClickedListener
import com.example.imagegallery.utilities.Utils.Companion.toPx

class ImageDataAdapter(private var context: Context,
                       private var imageClickedListener: ImageClickedListener) :
    PagingDataAdapter<Image, RecyclerView.ViewHolder>(DiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val imageBinding : ItemImageBinding = ItemImageBinding.inflate(
            layoutInflater,
            parent,
            false)
        return ImageViewHolder(imageBinding)

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ImageViewHolder && getItem(position) != null){

            Glide.with(context)
                .load(getItem(position)?.previewUrl)
                .transform(RoundedCorners(10))
                .placeholder(R.color.grey)
                .into(holder.imageView)

            holder.imageView.updateLayoutParams {
                width = (getItem(position)?.previewWidth)?.toPx(context)!!
                height = (getItem(position)?.previewHeight)?.toPx(context)!!
            }

            holder.itemView.setOnClickListener {
                getItem(position)?.let { it1 -> imageClickedListener.onImageClicked(it1) }
            }
        }
    }


    class ImageViewHolder(binding: ItemImageBinding)
        : RecyclerView.ViewHolder(binding.root){
        var imageView = binding.image
    }


    class DiffCallBack : DiffUtil.ItemCallback<Image>(){
        override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
            return oldItem == newItem
        }

    }
}