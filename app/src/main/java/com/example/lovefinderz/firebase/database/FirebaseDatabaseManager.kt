package com.example.lovefinderz.firebase.database

import com.example.lovefinderz.model.*
import com.google.firebase.database.*
import javax.inject.Inject

private const val KEY_USER = "user"
private const val KEY_JOKE = "joke"
private const val KEY_FAVORITE = "favorite"

class FirebaseDatabaseManager @Inject constructor(private val database: FirebaseDatabase) : FirebaseDatabaseInterface {

    override fun createUser(id: String, name: String, email: String) {
        val user = User(id, name, email)

        database.reference.child(KEY_USER).child(id).setValue(user)
    }

    override fun getProfile(id: String, onResult: (User) -> Unit) {
        database.reference
            .child(KEY_USER)
            .child(id)
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) = Unit

                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.getValue(UserResponse::class.java)
//                    val favoriteJokes = snapshot.child(KEY_FAVORITE).children
//                        .map { it?.getValue(JokeResponse::class.java) }
//                        .mapNotNull { it?.mapToJoke() }
//                        ?: listOf()
//
//
//                    user?.run { onResult(User(id, username, email, favoriteJokes)) }
                }
            })
    }
}