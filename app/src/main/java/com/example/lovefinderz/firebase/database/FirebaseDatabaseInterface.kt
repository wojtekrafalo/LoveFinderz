package com.example.lovefinderz.firebase.database

import com.example.lovefinderz.model.User
import com.example.lovefinderz.model.UserRelation


interface FirebaseDatabaseInterface {

    fun storeUser(
        id: String,
        user: User,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    )

    fun loadProfile(
        userId: String,
        onSuccess: (User) -> Unit,
        onFailure: (String) -> Unit
    )

    fun loadAllProfiles(
        onSuccess: (List<User>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun loadFreshProfile(
        id: String,
        onSuccess: (User) -> Unit,
        onFailure: (String) -> Unit
    )

    fun storeRelation(
        relation: UserRelation,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    )

    fun loadMatchingProfiles(
        id: String,
        onSuccess: (User) -> Unit,
        onFailure: (String) -> Unit
    )

//    fun sendProtocol(relation: UserRelation)
}