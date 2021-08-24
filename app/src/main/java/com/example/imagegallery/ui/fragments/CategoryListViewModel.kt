package com.example.imagegallery.ui.fragments

import androidx.lifecycle.ViewModel
import com.example.imagegallery.data.repository.ImageDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CategoryListViewModel @Inject
constructor(private var imageDataRepository: ImageDataRepository): ViewModel() {

    fun getCategoryList() : List<String> {
        return imageDataRepository.fetchCategoryList()
    }
    
}