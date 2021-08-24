package com.example.imagegallery.di.module

import com.example.imagegallery.constants.AppConstants
import com.example.imagegallery.data.remote.WebServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {



    @Singleton
    @Provides
    fun providesOkHttpClient() : OkHttpClient {
        return OkHttpClient.Builder().build()
    }


    @Singleton
    @Provides
    fun providesRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(AppConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Singleton
    @Provides
    fun providesWebService(retrofit: Retrofit): WebServices {
        return retrofit.create(WebServices::class.java)
    }
}