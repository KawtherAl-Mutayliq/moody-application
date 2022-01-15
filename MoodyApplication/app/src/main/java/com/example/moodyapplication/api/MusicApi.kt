package com.example.moodyapplication.api

import com.example.moodyapplication.model.FavoriteMusic
import com.example.moodyapplication.model.MusicModel
import retrofit2.Response
import retrofit2.http.*

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

    @GET("/favoriteMusic")
    suspend fun getMusicFavorite(): Response<List<FavoriteMusic>>


    @POST("/favoriteMusic/")
    suspend fun addFavorite(
      @Body favoriteBody: FavoriteMusic
    ): Response<FavoriteMusic>


    @DELETE("/favoriteMusic/{id}")
    suspend fun deleteFavorite(
        @Path("id") id : String
    ): Response<FavoriteMusic>

    @PUT("/moody/{id}")
    suspend fun updateMusicName(
        @Path("id") id: String,
        @Body musicModel: MusicModel
    ): Response<MusicModel>
}