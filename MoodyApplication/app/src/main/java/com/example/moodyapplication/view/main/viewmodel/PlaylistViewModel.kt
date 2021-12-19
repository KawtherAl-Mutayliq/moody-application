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

private const val TAG = "PlaylistViewModel"
class PlaylistViewModel: ViewModel() {
    private val apiServiceRepository = ApiServiceRepository.get()

    val musicLiveData = MutableLiveData<List<MusicModel>>()
    val selectedLiveData = MutableLiveData<MusicModel>()
    val musicArrayList = ArrayList<MusicModel>()
    val musicErrorLiveData = MutableLiveData<String>()

    fun callMusic(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = apiServiceRepository.getMusic()
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
}