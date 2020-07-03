package com.example.lovefinderz.firebase.database

import android.util.Log
import com.example.lovefinderz.model.User
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject


private const val KEY_USER = "user"
private const val KEY_EMAIL = "email"
private const val KEY_USERNAME = "username"
private const val KEY_ID = "id"

class FirebaseDatabaseManager @Inject constructor(private val database: FirebaseDatabase) : FirebaseDatabaseInterface {

    override fun createUser(id: String, name: String, email: String) {
        val user = User(id, name, email)

        //TODO: delete old database.reference:
//        database.reference.child(KEY_USER).child(id).setValue(user)


        val db = FirebaseFirestore.getInstance()
        db.collection(KEY_USER).add(user)
            .addOnSuccessListener {
                Log.d("SUCCESS", "User added!!!")
            }
            .addOnFailureListener {
                Log.d("FAILURE", "User not added!!!")
            }

    }

    //TODO: Update following method. It makes the ProfileFragment to not working and loading data.
    override fun getProfile(id: String, onResult: (User) -> Unit) {

        val db = FirebaseFirestore.getInstance()
        db.collection(KEY_USER)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
//                        Log.d("READ", "Map: " + document.data[KEY_ID].toString() + " => " + document.data)
//                        Log.d("ID", id)
//                        Log.d("READ", "Experiment: " + document.data[id].toString())


                        //TODO: Not the document.id. Sth else:
                        if ((document.data[id] as HashMap<*, *>)[KEY_ID] == id) {
//                            Log.d("READ", "Condition is fine. Got to onResult()")
                            val us = (document.data[id] as HashMap<*, *>)[KEY_USERNAME].toString()
                            val em = (document.data[id] as HashMap<*, *>)[KEY_EMAIL].toString()
                            val user = User( id, us, em)
                            onResult(user)
                        }
                    }
                } else {
                    Log.w("ERROR", "Error getting documents.", task.exception)
                }
            }

//        database.reference
//            .child(KEY_USER)
//            .child(id)
//            .addValueEventListener(object : ValueEventListener {
//                override fun onCancelled(error: DatabaseError) = Unit
//
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    val user = snapshot.getValue(UserResponse::class.java)
////                    val favoriteJokes = snapshot.child(KEY_FAVORITE).children
////                        .map { it?.getValue(JokeResponse::class.java) }
////                        .mapNotNull { it?.mapToJoke() }
////                        ?: listOf()
////
////
////                    user?.run { onResult(User(id, username, email, favoriteJokes)) }
//                }
//            })
    }
}