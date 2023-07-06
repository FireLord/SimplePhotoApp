package com.firelord.growighassignment.data.model


import com.google.gson.annotations.SerializedName

data class RemoteFetchItem(
    @SerializedName("id")
    var id: String?,
    @SerializedName("urls")
    val urls: Urls
)