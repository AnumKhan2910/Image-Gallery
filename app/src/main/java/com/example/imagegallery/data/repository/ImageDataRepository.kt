package com.example.imagegallery.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.imagegallery.data.remote.WebServices
import com.example.imagegallery.dataSource.ImageDataSource
import com.example.imagegallery.entity.Image
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ImageDataRepository @Inject constructor(private var webServices: WebServices) {

    companion object {
        private const val NETWORK_PAGE_SIZE = 10
    }

    fun fetchImageList(category: String) : Flow<PagingData<Image>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {
                ImageDataSource(webServices, category = category)
            }
        ).flow

    }


    fun searchImageList(query: String) : Flow<PagingData<Image>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {
                ImageDataSource(webServices, query = query)
            }
        ).flow

    }

    fun fetchCategoryList(): List<String> {
        return listOf(
            "education", "science", "health",
            "food", "sports", "travel", "business", "music", "industry", "computer"
        )
    }
}