package com.example.moodyapplication.database

import android.annotation.SuppressLint
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

object DataBase {

    @SuppressLint("StaticFieldLeak")
    val dataBase = FirebaseFirestore.getInstance()
    val userCollection = dataBase.collection("USER")
}