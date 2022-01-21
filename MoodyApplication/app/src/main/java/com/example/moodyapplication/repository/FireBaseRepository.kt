package com.example.moodyapplication.repository

import android.annotation.SuppressLint
import android.content.Context
import com.example.moodyapplication.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FireBaseRepository(val context: Context) {

    private val userCollection = Firebase.firestore.collection("user")

    fun addUser(user: UserModel) = userCollection.document(FirebaseAuth.getInstance().uid.toString()).set(user)
    fun getUser() = userCollection.document(FirebaseAuth.getInstance().uid.toString()).get()


    companion object {
        @SuppressLint("StaticFieldLeak")
        private var instance: FireBaseRepository? = null

        fun init(context: Context) {
            if (instance == null)
                instance = FireBaseRepository(context)
        }

        fun get(): FireBaseRepository {
            return instance ?: throw Exception("UserProfileRepositoryService must be initialized")
        }
    }
}