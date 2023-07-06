package com.firelord.growighassignment.presentation.di

import com.firelord.growighassignment.data.api.UnsplashAPIService
import com.firelord.growighassignment.data.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetModule {
    @Provides
    @Singleton
    fun provideRetrofit():Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    }

    @Provides
    @Singleton
    fun provideUnsplashAPIService(retrofit: Retrofit):UnsplashAPIService{
        return retrofit.create(UnsplashAPIService::class.java)
    }
}