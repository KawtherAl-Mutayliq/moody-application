package com.example.moodyapplication.model


import com.google.gson.annotations.SerializedName

data class MusicModel(
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("music")
    val music: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("photo")
    val photo: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("userId")
    val userId: String
)