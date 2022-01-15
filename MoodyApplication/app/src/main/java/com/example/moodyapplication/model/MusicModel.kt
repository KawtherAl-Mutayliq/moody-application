package com.example.moodyapplication.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MusicModel(
    @SerializedName("description")
    var description: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("music")
    var music: String,
    @SerializedName("name")
    var name: String,
    @SerializedName("photo")
    val photo: String,
    @SerializedName("type")
    val type: String
) : Parcelable