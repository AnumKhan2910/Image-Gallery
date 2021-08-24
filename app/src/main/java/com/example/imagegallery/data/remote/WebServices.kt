package com.example.imagegallery.data.remote

import com.example.imagegallery.response.ImageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WebServices {

    @GET("/api?key=23005364-605dbbef97058562b22e72217&orientation=horizontal")
    suspend fun fetchImages(@Query("page") page : Int,
                            @Query("category") category: String = "",
                            @Query("q") query: String = ""): Response<ImageResponse>

}