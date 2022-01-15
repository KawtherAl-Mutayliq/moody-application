package com.example.moodyapplication.repository

import android.content.Context
import com.example.moodyapplication.api.MusicApi
import com.example.moodyapplication.model.FavoriteMusic
import com.example.moodyapplication.model.MusicModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

private const val TAG = "ApiServiceRepository"
private const val BASE_URL = "https://61af59a23e2aba0017c491fe.mockapi.io/"
private const val FAVORITE_BASE_URL = "https://61af59a23e2aba0017c491fe.mockapi.io"

class ApiServiceRepository(context: Context) {
    private val retrofitService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val favoriteRetrofit = Retrofit.Builder()
        .baseUrl(FAVORITE_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val retrofitFavorite = favoriteRetrofit.create(MusicApi::class.java)

    private val retrofitApi = retrofitService.create(MusicApi::class.java)
    suspend fun getMusic() = retrofitApi.getMusic()
    suspend fun getSadMusic() = retrofitApi.getSadMoodMusic()
    suspend fun getHappyMusic() = retrofitApi.getHappyMoodMusic()
    suspend fun getRomanceMusic() = retrofitApi.getRomanceMoodMusic()
    suspend fun getWorkoutMusic() = retrofitApi.getWorkoutMoodMusic()
    suspend fun getFavorite() =retrofitFavorite.getMusicFavorite()
    suspend fun addFavorite(favoriteBody: FavoriteMusic) = retrofitApi.addFavorite(favoriteBody)
    suspend fun deleteFavorite(musicId: String) = retrofitApi.deleteFavorite(musicId)
    suspend fun updateMusicName(id:String, musicModel: MusicModel) = retrofitApi.updateMusicName(id, musicModel)

    companion object{
        private var instance: ApiServiceRepository? = null

        fun init(context: Context){
            if (instance == null)
                instance = ApiServiceRepository(context)
        }

        fun get(): ApiServiceRepository {
            return instance ?: throw Exception("ApiServiceRepository must be initialized")
        }
    }
}
