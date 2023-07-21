package com.firelord.growighassignment.presentation.di

import com.firelord.growighassignment.presentation.adapter.CommentAdapter
import com.firelord.growighassignment.presentation.adapter.PhotosAdapter
import com.firelord.growighassignment.presentation.adapter.VideoAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AdapterModule {
    @Provides
    @Singleton
    fun providePhotoAdapter():PhotosAdapter{
        return PhotosAdapter()
    }
    @Provides
    @Singleton
    fun provideVideoAdapter():VideoAdapter{
        return VideoAdapter()
    }

    @Provides
    @Singleton
    fun provideCommentAdapter():CommentAdapter{
        return CommentAdapter()
    }
}