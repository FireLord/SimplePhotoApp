package com.firelord.growighassignment.data.api

import com.firelord.growighassignment.data.model.RemoteFetch
import com.firelord.growighassignment.data.util.Constants.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UnsplashAPIService {
    @GET("photos")
    suspend fun getPhotos(
        @Query("page")
        page:Int,
        @Query("client_id")
        apiKey:String = API_KEY,
        @Query("per_page")
        per_page:Int = 10 // default value is 10
    ): Response<RemoteFetch>
}