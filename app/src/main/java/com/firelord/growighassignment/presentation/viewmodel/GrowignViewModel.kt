package com.firelord.growighassignment.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class GrowignViewModel(app: Application,):AndroidViewModel(app) {
    val indicator : MutableLiveData<Int> = MutableLiveData()
}