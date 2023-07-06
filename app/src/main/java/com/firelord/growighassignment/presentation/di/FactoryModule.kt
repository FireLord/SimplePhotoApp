package com.firelord.growighassignment.presentation.di

import android.app.Application
import com.firelord.growighassignment.domain.usecase.GetPhotoUseCase
import com.firelord.growighassignment.presentation.viewmodel.GrowignViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FactoryModule {
    @Singleton
    @Provides
    fun provideGrowignViewModelFactory(
        application: Application,
        getPhotoUseCase: GetPhotoUseCase
    ):GrowignViewModelFactory{
        return GrowignViewModelFactory(application,getPhotoUseCase)
    }
}