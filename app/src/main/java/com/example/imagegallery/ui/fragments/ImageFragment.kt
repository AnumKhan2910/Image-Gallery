package com.example.imagegallery.ui.fragments

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.imagegallery.R
import com.example.imagegallery.databinding.FragmentImageBinding
import android.content.Intent
import android.content.IntentFilter
import android.widget.Toast
import com.example.imagegallery.entity.Image
import com.example.imagegallery.utilities.Utils.Companion.downloadImage


class ImageFragment : Fragment() {

    private lateinit var binding: FragmentImageBinding
    private var image : Image? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    override fun onStart() {
        super.onStart()
        requireContext().registerReceiver(downloadComplete,
            IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
    }

    private fun initViews(){
        arguments?.let {
            val safeArgs = ImageFragmentArgs.fromBundle(it)
            image = safeArgs.image

            image?.let {
                Glide.with(this)
                    .load(image!!.imageUrl)
                    .transform(RoundedCorners(20))
                    .placeholder(R.color.grey )
                    .into(binding.image)

                binding.btnDownload.setOnClickListener {
                    Toast.makeText(requireContext(), getString(R.string.text_download_image), Toast.LENGTH_SHORT).show()
                    binding.btnDownload.alpha = 0.4f
                    downloadImage(requireContext(), image!!.imageUrl, image!!.id)
                }
            }

        }

    }


    private val downloadComplete : BroadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(p0: Context?, p1: Intent?) {
            Toast.makeText(requireContext(), getString(R.string.text_image_downloaded), Toast.LENGTH_LONG).show()
            binding.btnDownload.alpha = 1f
        }
    }

    override fun onStop() {
        super.onStop()
        requireContext().unregisterReceiver(downloadComplete)
    }

}