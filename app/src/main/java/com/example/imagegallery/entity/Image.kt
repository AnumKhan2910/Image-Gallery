package com.example.imagegallery.entity

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("id")
    var id : String,

    @SerializedName("previewURL")
    var previewUrl : String,

    @SerializedName("previewWidth")
    var previewWidth : Int,

    @SerializedName("previewHeight")
    var previewHeight : Int,

    @SerializedName("largeImageURL")
    var imageUrl : String,

    @SerializedName("imageWidth")
    var imageWidth : Int,

    @SerializedName("imageHeight")
    var imageHeight : Int) : Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readInt(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(previewUrl)
        parcel.writeInt(previewWidth)
        parcel.writeInt(previewHeight)
        parcel.writeString(imageUrl)
        parcel.writeInt(imageWidth)
        parcel.writeInt(imageHeight)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Image> {
        override fun createFromParcel(parcel: Parcel): Image {
            return Image(parcel)
        }

        override fun newArray(size: Int): Array<Image?> {
            return arrayOfNulls(size)
        }
    }
}

