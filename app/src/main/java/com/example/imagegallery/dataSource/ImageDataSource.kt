package com.example.imagegallery.dataSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.imagegallery.data.remote.WebServices
import com.example.imagegallery.entity.Image
import com.example.imagegallery.response.ImageResponse

class ImageDataSource(private var webServices: WebServices,
                      private var category : String = "",
                      private var query: String = "")
    : PagingSource<Int, Image>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Image> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = webServices.fetchImages(nextPageNumber, category, query)
            val responseBody = response.body() as ImageResponse
            LoadResult.Page(
                data = responseBody.imageList,
                prevKey = if (nextPageNumber > 0) nextPageNumber - 1 else null,
                nextKey = if (nextPageNumber < responseBody.totalHits/20) nextPageNumber + 1 else null
            )
        } catch (e : Exception){
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Image>): Int? {
        // Try to find the page key of the closest page to anchorPosition, from
        // either the prevKey or the nextKey, but you need to handle nullability
        // here:
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey null -> anchorPage is the initial page, so
        //    just return null.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}