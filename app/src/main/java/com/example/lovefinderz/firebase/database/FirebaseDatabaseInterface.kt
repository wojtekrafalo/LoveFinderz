package com.example.lovefinderz.firebase.database

import com.example.lovefinderz.model.User
import com.example.lovefinderz.model.UserRelation
import java.util.*


interface FirebaseDatabaseInterface {

    fun storeUser(
        id: String,
        user: User,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    )

//    fun updateRelatedUserList(
//        thisUserId: String,
//        otherUserId: String,
//        onSuccess: () -> Unit,
//        onFailure: (String) -> Unit
//    )

    fun loadProfile(
        userId: String,
        onSuccess: (User) -> Unit,
        onFailure: (String) -> Unit
    )

    fun loadAllProfiles(
        onSuccess: (MutableList<User>) -> Unit,
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
        onSuccess: (MutableList<User>) -> Unit,
        onFailure: (String) -> Unit
    )

//    fun sendProtocol(relation: UserRelation)
}