package com.firelord.growighassignment.data.model


import com.google.gson.annotations.SerializedName

data class RemoteFetchItem(
    @SerializedName("urls")
    val urls: Urls,
)