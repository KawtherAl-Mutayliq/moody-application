package com.example.moodyapplication.api

import com.example.moodyapplication.model.MusicModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MusicApi {
    @GET("/moody")
    suspend fun getMusic(): Response<List<MusicModel>>

    @GET("/moody?type=sad")
    suspend fun getSadMoodMusic() : Response<List<MusicModel>>

    @GET("/moody?type=happy")
    suspend fun getHappyMoodMusic() : Response<List<MusicModel>>

    @GET("/moody?type=romance")
    suspend fun getRomanceMoodMusic() : Response<List<MusicModel>>

    @GET("/moody?type=workout")
    suspend fun getWorkoutMoodMusic() : Response<List<MusicModel>>
}