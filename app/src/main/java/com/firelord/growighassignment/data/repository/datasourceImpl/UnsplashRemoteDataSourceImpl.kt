package com.firelord.growighassignment.data.repository.datasourceImpl

import com.firelord.growighassignment.data.api.UnsplashAPIService
import com.firelord.growighassignment.data.model.RemoteFetch
import com.firelord.growighassignment.data.repository.datasource.UnsplashRemoteDataSource
import retrofit2.Response

class UnsplashRemoteDataSourceImpl(
    private val unsplashAPIService: UnsplashAPIService,
): UnsplashRemoteDataSource {
    override suspend fun getPhotos(page:Int): Response<RemoteFetch> {
        return unsplashAPIService.getPhotos(page)
    }

}