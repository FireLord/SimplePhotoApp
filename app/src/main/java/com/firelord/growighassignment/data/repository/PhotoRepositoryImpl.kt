package com.firelord.growighassignment.data.repository

import com.firelord.growighassignment.data.model.RemoteFetch
import com.firelord.growighassignment.data.repository.datasource.UnsplashRemoteDataSource
import com.firelord.growighassignment.data.util.Resource
import com.firelord.growighassignment.domain.repository.PhotoRepository
import retrofit2.Response

class PhotoRepositoryImpl(
    private val unsplashRemoteDataSource: UnsplashRemoteDataSource
):PhotoRepository {
    private fun responseToResource(response:Response<RemoteFetch>):Resource<RemoteFetch>{
        if (response.isSuccessful){
            response.body()?.let { result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }

    override suspend fun getPhoto(page:Int): Resource<RemoteFetch> {
        return responseToResource(unsplashRemoteDataSource.getPhotos(page))
    }
}