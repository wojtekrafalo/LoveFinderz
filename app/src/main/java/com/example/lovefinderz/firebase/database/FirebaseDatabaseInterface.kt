package com.example.lovefinderz.firebase.database

import com.example.lovefinderz.model.User


interface FirebaseDatabaseInterface {

    fun createUser(id: String, name: String, email: String)

    fun getProfile(id: String, onResult: (User) -> Unit)
}