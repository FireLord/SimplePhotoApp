package com.firelord.growighassignment.presentation.di

import com.firelord.growighassignment.domain.repository.PhotoRepository
import com.firelord.growighassignment.domain.usecase.GetPhotoUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {
    @Provides
    @Singleton
    fun provideGetPhotoUseCase(
        photoRepository: PhotoRepository
    ):GetPhotoUseCase{
        return GetPhotoUseCase(photoRepository)
    }
}