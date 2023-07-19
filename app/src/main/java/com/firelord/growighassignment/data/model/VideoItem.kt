package com.firelord.growighassignment.data.model

import java.io.Serializable

data class VideoItem(
    val id: Int,
    val name: String,
    val url : String,
    val description : String
) : Serializable