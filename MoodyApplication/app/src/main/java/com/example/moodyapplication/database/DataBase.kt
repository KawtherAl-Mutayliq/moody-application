package com.example.moodyapplication.database

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

object DataBase {

    val dataBase: FirebaseDatabase = FirebaseDatabase.getInstance()
    val myRef : DatabaseReference = dataBase.getReference("User")
}