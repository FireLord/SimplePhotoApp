package com.firelord.growighassignment.presentation.di

import com.firelord.growighassignment.data.repository.PhotoRepositoryImpl
import com.firelord.growighassignment.data.repository.datasource.UnsplashRemoteDataSource
import com.firelord.growighassignment.domain.repository.PhotoRepository
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    fun providePhotoRepository(
        unsplashRemoteDataSource: UnsplashRemoteDataSource
    ):PhotoRepository{
        return PhotoRepositoryImpl(unsplashRemoteDataSource)
    }
}