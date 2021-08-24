package com.example.imagegallery.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.imagegallery.adapter.ImageCategoryAdapter
import com.example.imagegallery.databinding.FragmentImageCategoryListBinding
import com.example.imagegallery.listener.ItemClickedListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImageCategoryListFragment : Fragment(), ItemClickedListener {

    private val viewModel : CategoryListViewModel by viewModels()
    private lateinit var categoryAdapter : ImageCategoryAdapter
    private lateinit var binding: FragmentImageCategoryListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentImageCategoryListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
        initViews()
    }

    private fun initViews(){
        categoryAdapter = ImageCategoryAdapter(viewModel.getCategoryList(), this)
        StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL).also {
            binding.recyclerView.layoutManager = it
        }

        binding.recyclerView.adapter = categoryAdapter

        binding.searchView.isIconified = false
        binding.btnSearch.setOnClickListener {
            val action = ImageCategoryListFragmentDirections
                .actionImageCategoryListFragmentToImageListFragment(
                    query = binding.searchView.query.toString(),
                    search = true)
            view?.let {
                Navigation.findNavController(it).navigate(action)
            }
        }
    }

    override fun onItemClicked(category: String) {
        val action = ImageCategoryListFragmentDirections
            .actionImageCategoryListFragmentToImageListFragment(category = category, search = false)
        view?.let { Navigation.findNavController(it).navigate(action) }
    }

    private val callback: OnBackPressedCallback =
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }
        }
}