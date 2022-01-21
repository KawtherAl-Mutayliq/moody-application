package com.example.moodyapplication.api

import com.example.moodyapplication.model.FavoriteMusic
import com.example.moodyapplication.model.MusicModel
import retrofit2.Response
import retrofit2.http.*

interface MusicApi {

    // get music info
    @GET("/moody")
    suspend fun getMusic(): Response<List<MusicModel>>

    //  get music which its type is sad
    @GET("/moody?type=sad")
    suspend fun getSadMoodMusic() : Response<List<MusicModel>>

    //  get music which its type is happy
    @GET("/moody?type=happy")
    suspend fun getHappyMoodMusic() : Response<List<MusicModel>>

    //  get music which its type is romance
    @GET("/moody?type=romance")
    suspend fun getRomanceMoodMusic() : Response<List<MusicModel>>

    //  get music which its type is workout
    @GET("/moody?type=workout")
    suspend fun getWorkoutMoodMusic() : Response<List<MusicModel>>

    // get favorite music
    @GET("/favoriteMusic")
    suspend fun getMusicFavorite(): Response<List<FavoriteMusic>>

    // post music from main api and put them in favorite api
    @POST("/favoriteMusic/")
    suspend fun addFavorite(
      @Body favoriteBody: FavoriteMusic
    ): Response<FavoriteMusic>


    // delete music item from favorite
    @DELETE("/favoriteMusic/{id}")
    suspend fun deleteFavorite(
        @Path("id") id : String
    ): Response<FavoriteMusic>


    // update title and description of music item
    @PUT("/moody/{id}")
    suspend fun updateMusicName(
        @Path("id") id: String,
        @Body musicModel: MusicModel
    ): Response<MusicModel>


}