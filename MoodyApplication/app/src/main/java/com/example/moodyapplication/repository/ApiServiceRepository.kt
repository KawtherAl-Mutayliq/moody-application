package com.example.moodyapplication.repository

import android.content.Context
import com.example.moodyapplication.api.MusicApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

private const val TAG = "ApiServiceRepository"
private const val BASE_URL = "https://61af59a23e2aba0017c491fe.mockapi.io/"

class ApiServiceRepository(context: Context) {
    private val retrofitService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val retrofitApi = retrofitService.create(MusicApi::class.java)
    suspend fun getMusic() = retrofitApi.getMusic()
    suspend fun getSadMusic() = retrofitApi.getSadMoodMusic()
    suspend fun getHappyMusic() = retrofitApi.getHappyMoodMusic()
    suspend fun getRomanceMusic() = retrofitApi.getRomanceMoodMusic()
    suspend fun getWorkoutMusic() = retrofitApi.getWorkoutMoodMusic()

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
