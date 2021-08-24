package com.example.imagegallery.ui.fragments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.imagegallery.data.repository.ImageDataRepository
import com.example.imagegallery.entity.Image
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class ImageListViewModel
@Inject constructor(private var imageDataRepository: ImageDataRepository):
    ViewModel() {

    fun fetchImageList(category: String) : Flow<PagingData<Image>> {
        return imageDataRepository.fetchImageList(category)
            .map { it }
            .cachedIn(viewModelScope)
    }


    fun searchImageList(query: String) : Flow<PagingData<Image>> {
        return imageDataRepository.searchImageList(query)
            .map { it }
            .cachedIn(viewModelScope)
    }

}