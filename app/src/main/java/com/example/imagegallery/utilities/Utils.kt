package com.example.imagegallery.utilities

import android.content.Context
import android.util.DisplayMetrics
import android.os.Environment
import android.app.DownloadManager
import android.content.Context.DOWNLOAD_SERVICE
import android.net.Uri
import com.example.imagegallery.R


class Utils {
    companion object {
        fun Int.toPx(context: Context) =
            this * context.resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT

        fun downloadImage(context: Context, url : String, name: String){
            val uri: Uri = Uri.parse(url)
            val mgr : DownloadManager = context.getSystemService(DOWNLOAD_SERVICE) as DownloadManager

            Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                .mkdirs()

            mgr.enqueue(
                DownloadManager.Request(uri)
                    .setAllowedNetworkTypes(
                        DownloadManager.Request.NETWORK_WIFI or
                                DownloadManager.Request.NETWORK_MOBILE
                    )
                    .setTitle(context.getString(R.string.app_name))
                    .setDescription(context.getString(R.string.text_download_image))
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    .setDestinationInExternalPublicDir(
                        Environment.DIRECTORY_DOWNLOADS,
                        "$name.png"
                    )
            )
        }

    }
}