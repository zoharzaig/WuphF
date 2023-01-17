package com.example.wuphf.data.remote.model

import com.google.gson.annotations.SerializedName

data class AllDogs(
    @SerializedName("message")
    val message : List<String>,
    @SerializedName("status")
    val status: String
)
