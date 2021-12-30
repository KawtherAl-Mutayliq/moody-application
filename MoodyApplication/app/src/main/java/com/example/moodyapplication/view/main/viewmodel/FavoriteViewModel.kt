package com.example.moodyapplication.view.main.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moodyapplication.model.FavoriteMusic
import com.example.moodyapplication.model.MusicModel
import com.example.moodyapplication.repository.ApiServiceRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

private const val TAG = "FavoriteViewModel"

class FavoriteViewModel : ViewModel() {
    private val apiServiceRepository = ApiServiceRepository.get()

    val favoriteLiveData = MutableLiveData<List<FavoriteMusic>>()
    val musicArrayList = MutableLiveData<List<FavoriteMusic>>()
    val favoriteErrorLiveData = MutableLiveData<String>()

    fun callFavorite(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = apiServiceRepository.getFavorite()
                if (response.isSuccessful){
                    response.body()?.run {
                        Log.d(TAG, "success response ${response.message()}")
                       favoriteLiveData.postValue(this)
                    }
                }else{
                    Log.d(TAG, " failed response ${response.message()}")
                    favoriteErrorLiveData.postValue(response.message())
                }

            }catch (e: Exception){
                Log.d(TAG, " exception ${e.message.toString()}")
                favoriteErrorLiveData.postValue(e.message.toString())
            }
        }
    }

     fun deleteFavorite(music: FavoriteMusic) {
         viewModelScope.launch(Dispatchers.IO) {
             try {
                 val response = apiServiceRepository.deleteFavorite(music.id)
                 if (!response.isSuccessful) {

                     Log.d(TAG , "success response ${response.message()}")
                     favoriteErrorLiveData.postValue(response.toString())
                 }

             } catch (e: Exception) {
                 Log.d(TAG , " exception ${e.message.toString()}")
                 favoriteErrorLiveData.postValue(e.message.toString())
             }
         }
     }
}