package com.firelord.growighassignment.data.repository.datasource

import com.firelord.growighassignment.data.model.RemoteFetch
import retrofit2.Response

interface UnsplashRemoteDataSource {
    suspend fun getPhotos(page:Int):Response<RemoteFetch>
}