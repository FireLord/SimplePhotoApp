package com.firelord.growighassignment.domain.repository

import com.firelord.growighassignment.data.model.RemoteFetch
import com.firelord.growighassignment.data.util.Resource

interface PhotoRepository {
    suspend fun getPhoto(page:Int):Resource<RemoteFetch>
}