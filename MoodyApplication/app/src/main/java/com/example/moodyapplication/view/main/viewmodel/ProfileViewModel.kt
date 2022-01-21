package com.example.moodyapplication.view.main.viewmodel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moodyapplication.model.UserModel
import com.example.moodyapplication.repository.FireBaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

private const val TAG = "ProfileViewModel"
class ProfileViewModel : ViewModel() {

    val userLiveData = MutableLiveData<UserModel>()
    val getUserLiveData = MutableLiveData<UserModel>()
    val userErrorLiveData = MutableLiveData<String>()

   private val fireBaseRepository = FireBaseRepository.get()

    fun updateUserProfile(userModel: UserModel){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                fireBaseRepository.addUser(userModel).addOnSuccessListener {
                    Log.d(TAG, "document success" )
                    userLiveData.postValue(userModel)
                }.addOnFailureListener{

                    Log.d(TAG, "failure message ${it.message.toString()}" )
                    userErrorLiveData.postValue(it.message.toString())
                }
            }catch (e : Exception){
                Log.d(ContentValues.TAG, e.message.toString())
                userErrorLiveData.postValue(e.message.toString())
            }
        }
    }


    fun addUser(){
        try {
            fireBaseRepository.getUser().addOnSuccessListener {

                val user = it.toObject<UserModel>(UserModel::class.java)
                getUserLiveData.postValue(user!!)
                Log.d(TAG, "document success" )

            }.addOnFailureListener {

                userErrorLiveData.postValue(it.message)
                Log.d(TAG, "failure message ${it.message.toString()}" )
            }
        }catch (e : Exception){

            Log.d(ContentValues.TAG, e.message.toString())
            userErrorLiveData.postValue(e.message.toString())
        }
    }
}