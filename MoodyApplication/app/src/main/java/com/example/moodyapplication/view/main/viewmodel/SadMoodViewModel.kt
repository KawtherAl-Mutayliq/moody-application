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

private const val TAG = "SadMoodViewModel"
class SadMoodViewModel : ViewModel() {

    private val apiServiceRepository = ApiServiceRepository.get()
    val musicArrayList = MutableLiveData<List<MusicModel>>()
    val musicLiveData = MutableLiveData<List<MusicModel>>()
    val musicErrorLiveData = MutableLiveData<String>()

    // call data of sad music type from api
    fun callMusic(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = apiServiceRepository.getSadMusic()
                if (response.isSuccessful){
                    response.body()?.run {
                        Log.d(TAG, "success response ${response.message()}")
                        musicLiveData.postValue(this)
                    }
                }else{
                    Log.d(TAG, " failed response ${response.message()}")
                    musicErrorLiveData.postValue(response.message())
                }

            }catch (e: Exception){
                Log.d(TAG, " exception ${e.message.toString()}")
                musicErrorLiveData.postValue(e.message.toString())
            }
        }
    }

    // add music into favorite model
    fun addFavorite(musicModel: MusicModel) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = apiServiceRepository.addFavorite(
                    FavoriteMusic(
                    musicModel.description,
                    musicModel.id,
                    musicModel.music,
                    musicModel.name,
                    musicModel.photo,
                    musicModel.type,
                    FirebaseAuth.getInstance().currentUser!!.uid
                )
                )
                if (response.isSuccessful) {
                    Log.d(TAG , "success response ${response.message()}")
                }

            } catch (e: Exception) {
                Log.d(TAG , " exception ${e.message.toString()}")
                musicErrorLiveData.postValue(e.message.toString())
            }
        }
    }
}