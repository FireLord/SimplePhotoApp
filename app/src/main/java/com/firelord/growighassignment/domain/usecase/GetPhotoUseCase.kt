package com.firelord.growighassignment.domain.usecase

import com.firelord.growighassignment.data.model.RemoteFetch
import com.firelord.growighassignment.data.util.Resource
import com.firelord.growighassignment.domain.repository.PhotoRepository

class GetPhotoUseCase(private val photoRepository: PhotoRepository) {
    suspend fun execute(page:Int): Resource<RemoteFetch>{
        return photoRepository.getPhoto(page)
    }
}