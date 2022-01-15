package com.example.moodyapplication.view.main.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moodyapplication.model.MusicModel
import com.example.moodyapplication.repository.ApiServiceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

private const val TAG = "HappyMusicViewModel"

class HappyMusicViewModel : ViewModel() {
    private val apiServiceRepository = ApiServiceRepository.get()
    val musicArrayList = MutableLiveData<List<MusicModel>>()
    val happyMusicLiveData = MutableLiveData<List<MusicModel>>()
    val happyMusicErrorLiveData = MutableLiveData<String>()

    fun callMusic(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = apiServiceRepository.getHappyMusic()
                if (response.isSuccessful){
                    response.body()?.run {
                        Log.d(TAG, "success response ${response.message()}")
                        happyMusicLiveData.postValue(this)
                    }
                }else{
                    Log.d(TAG, " failed response ${response.message()}")
                    happyMusicErrorLiveData.postValue(response.message())
                }

            }catch (e: Exception){
                Log.d(TAG, " exception ${e.message.toString()}")
                happyMusicErrorLiveData.postValue(e.message.toString())
            }
        }
    }
}