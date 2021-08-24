package com.example.imagegallery.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.paging.LoadState
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.imagegallery.adapter.ImageDataAdapter
import com.example.imagegallery.databinding.FragmentImageListBinding
import com.example.imagegallery.entity.Image
import com.example.imagegallery.listener.ImageClickedListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ImageListFragment : Fragment(), ImageClickedListener {

    private lateinit var adapter: ImageDataAdapter
    private lateinit var binding : FragmentImageListBinding
    private val viewModel : ImageListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentImageListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observeData()
    }


    private fun initViews(){
        adapter = ImageDataAdapter(requireContext(), this)
        StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL).also {
            binding.recyclerView.layoutManager = it
        }
        binding.recyclerView.adapter = adapter

        adapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Loading ||
                loadState.append is LoadState.Loading){
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }

    }

    private fun observeData(){
        arguments.let {
            val safeArgs = it?.let { it1 -> ImageListFragmentArgs.fromBundle(it1) }
            val category = safeArgs?.category
            val query = safeArgs?.query
            val search : Boolean = safeArgs?.search!!

            if (search){
                query?.let { q ->
                    lifecycleScope.launch {
                        viewModel.searchImageList(q).collectLatest { pagingData ->
                            adapter.submitData(pagingData)
                        }
                    }
                }
            } else {
                category?.let { ImageCategory ->
                    lifecycleScope.launch {
                        viewModel.fetchImageList(ImageCategory).collectLatest { pagingData ->
                            adapter.submitData(pagingData)
                        }
                    }
                }
            }
        }
    }

    override fun onImageClicked(image: Image) {
        val action = ImageListFragmentDirections.actionImageListFragmentToImageFragment(image)
        view?.let { Navigation.findNavController(it).navigate(action) }
    }
}