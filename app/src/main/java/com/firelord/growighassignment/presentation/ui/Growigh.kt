package com.firelord.growighassignment.presentation.ui

import android.app.Application
import android.util.Log
import android.widget.Toast
import com.firelord.growighassignment.data.util.Network.isInternetAvailable
import com.yausername.youtubedl_android.YoutubeDL
import com.yausername.youtubedl_android.YoutubeDLException
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Growigh: Application(){
    override fun onCreate() {
        super.onCreate()
        // YT instance
        try {
            YoutubeDL.getInstance().init(this)
        } catch (e: YoutubeDLException) {
            Log.e(TAG, "failed to initialize youtubedl-android", e)
        }
        if (isInternetAvailable(this)){
            Thread {
                try {
                    YoutubeDL.getInstance().updateYoutubeDL(this)
                }
                catch (e: Exception){
                    Log.d(TAG,"failed to update youtubeDL")
                }
            }.start()
        }
        else{
            Toast.makeText(this,"PLease turn on net",Toast.LENGTH_LONG).show()
        }
    }
    companion object {
        const val TAG = "BaseApplication"
    }
}