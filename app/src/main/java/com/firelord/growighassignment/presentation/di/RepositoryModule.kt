package com.firelord.growighassignment.presentation.di

import com.firelord.growighassignment.data.repository.PhotoRepositoryImpl
import com.firelord.growighassignment.data.repository.datasource.UnsplashRemoteDataSource
import com.firelord.growighassignment.domain.repository.PhotoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun providePhotoRepository(
        unsplashRemoteDataSource: UnsplashRemoteDataSource
    ):PhotoRepository{
        return PhotoRepositoryImpl(unsplashRemoteDataSource)
    }
}