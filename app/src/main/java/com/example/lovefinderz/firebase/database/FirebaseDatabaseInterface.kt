package com.example.lovefinderz.firebase.database

import com.example.lovefinderz.model.User
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Query


interface FirebaseDatabaseInterface {

    fun createUser(id: String, name: String, email: String)

    fun getProfile(id: String, onResult: (User) -> Unit)

    fun getProfiles(onResult: (MutableList<User>) -> Unit)

    fun getFreshProfile(id: String, onSuccess: (User) -> Unit, onFailure: () -> Unit)

    fun addRatedProfile(
        idLiking: String,
        idLiked: String,
        isLiked: Boolean,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    )

    fun getMatchedProfiles(id: String, onResult: (MutableList<User>) -> Unit)

    fun getRelatedProfiles(
        id: String,
        keyId1: String,
        keyId2: String,
        checkMatching: Boolean,
        onResult: (MutableList<User>) -> Unit
    )
}