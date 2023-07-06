package com.firelord.growighassignment.presentation.di

import com.firelord.growighassignment.data.api.UnsplashAPIService
import com.firelord.growighassignment.data.repository.datasource.UnsplashRemoteDataSource
import com.firelord.growighassignment.data.repository.datasourceImpl.UnsplashRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteDataModule {
    @Provides
    @Singleton
    fun provideUnsplashDataSource(
        unsplashAPIService: UnsplashAPIService
    ):UnsplashRemoteDataSource{
        return UnsplashRemoteDataSourceImpl(unsplashAPIService)
    }
}