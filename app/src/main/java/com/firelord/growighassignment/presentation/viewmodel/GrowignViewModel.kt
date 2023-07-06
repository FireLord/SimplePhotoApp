package com.firelord.growighassignment.presentation.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.firelord.growighassignment.data.model.RemoteFetch
import com.firelord.growighassignment.data.model.RemoteFetchItem
import com.firelord.growighassignment.data.model.Urls
import com.firelord.growighassignment.data.util.Resource
import com.firelord.growighassignment.domain.usecase.GetPhotoUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GrowignViewModel(
    private val app:Application,
    private val getPhotoUseCase: GetPhotoUseCase
):AndroidViewModel(app) {
    val indicator : MutableLiveData<Int> = MutableLiveData()
    val photos: MutableLiveData<Resource<RemoteFetch>> = MutableLiveData()

    fun getPhotos(page:Int) = viewModelScope.launch(Dispatchers.IO) {
        photos.postValue(Resource.Loading())
        try {
            if (isInternetAvailable(app)){
                val apiResult = getPhotoUseCase.execute(page)
                photos.postValue(apiResult)
            }else{
                photos.postValue(Resource.Error("Internet is not available"))
            }
        } catch (e:java.lang.Exception){
            photos.postValue(Resource.Error(e.message.toString()))
        }
    }

    @Suppress("DEPRECATION")
    fun isInternetAvailable(context: Context): Boolean {
        var result = false
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cm?.run {
                cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                    result = when {
                        hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                        else -> false
                    }
                }
            }
        } else {
            cm?.run {
                cm.activeNetworkInfo?.run {
                    if (type == ConnectivityManager.TYPE_WIFI) {
                        result = true
                    } else if (type == ConnectivityManager.TYPE_MOBILE) {
                        result = true
                    }
                }
            }
        }
        return result
    }
}