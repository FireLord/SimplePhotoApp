package com.firelord.growighassignment.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.firelord.growighassignment.domain.usecase.GetPhotoUseCase

class GrowignViewModelFactory(
    private val app: Application,
    private val getPhotoUseCase: GetPhotoUseCase
):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GrowignViewModel(app,getPhotoUseCase) as T
    }
}