package com.example.imagegallery.listener

import com.example.imagegallery.entity.Image

interface ImageClickedListener {
    fun onImageClicked(image : Image)
}