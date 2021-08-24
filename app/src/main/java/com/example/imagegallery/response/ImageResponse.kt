package com.example.imagegallery.response

import com.example.imagegallery.entity.Image
import com.google.gson.annotations.SerializedName

data class ImageResponse(
    @SerializedName("hits")
    var imageList : List<Image>
)
