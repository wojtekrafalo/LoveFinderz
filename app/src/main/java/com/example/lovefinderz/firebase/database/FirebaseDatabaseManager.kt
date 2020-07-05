package com.example.lovefinderz.firebase.database

import android.util.Log
import com.example.lovefinderz.model.User
import com.example.lovefinderz.model.UserRelation
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

private const val KEY_DB_USER = "user"
private const val KEY_DB_RELATION = "relation"
private const val KEY_EMAIL = "email"
private const val KEY_USERNAME = "username"
private const val KEY_ID = "id"
private const val KEY_ID1 = "id1"
private const val KEY_ID2 = "id2"
private const val KEY_MATCH = "match"

private val HARDCODED_ID = "f74UBon50PVoKI4Z3aKanKDrAOs1"
private val HARDCODED_USERNAME = "bartke"
private val HARDCODED_EMAIL = "bartke@bartke.bartke"

class FirebaseDatabaseManager @Inject constructor(private val database: FirebaseDatabase) :
    FirebaseDatabaseInterface {

    override fun createUser(id: String, name: String, email: String) {
        val user = User(id, name, email)

        //TODO: delete old database.reference:
//        database.reference.child(KEY_USER).child(id).setValue(user)


        val db = FirebaseFirestore.getInstance()
        db.collection(KEY_DB_USER).add(user)
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
        db.collection(KEY_DB_USER)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        //TODO: Delete ass-prints.
//                        Log.d("READ", "Map: " + document.data[KEY_ID].toString() + " => " + document.data)
//                        Log.d("ID", id)
//                        Log.d("READ", "Experiment: " + document.data[id].toString())


                        //TODO: Not the document.id. Sth else:
                        val map = (document.data as HashMap<*, *>)
//                        if (document.data[id] != null && ((document.data[id] as HashMap<*, *>)[KEY_ID] == id)) {
                        if (map[KEY_ID] == id) {
//                            Log.d("READ", "Condition is fine. Got to onResult()")
                            val us = map[KEY_USERNAME].toString()
                            val em = map[KEY_EMAIL].toString()
                            val user = User(id, us, em)
                            onResult(user)
                        }
                    }
                } else {
                    Log.w("ERROR", "Error getting documents.", task.exception)
                }
            }

        //TODO: Delete below old database using after assuring, all database stuff works:
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

    override fun getProfiles(onResult: (MutableList<User>) -> Unit) {
        val db = FirebaseFirestore.getInstance()
        db.collection(KEY_DB_USER)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val list: MutableList<User> = ArrayList()

                    for (document in task.result!!) {
                        val map = (document.data as HashMap<*, *>)
                        val userId = map[KEY_ID].toString()
                        val userUsername = map[KEY_USERNAME].toString()
                        val userEmail = map[KEY_EMAIL].toString()
                        val user = User(userId, userUsername, userEmail)

                        list.add(user)
                    }
                    onResult(list)
                } else {
                    //TODO: Add some exception handling to show in layout.
                    Log.w("ERROR", "Error getting documents.", task.exception)
                }
            }
    }

    //TODO: change structure of relation table to make operations more efficient.
    override fun getFreshProfile(
        id: String,
        onSuccess: (User) -> Unit,
        onFailure: () -> Unit
    ) {
//        val user = UserResponse(HARDCODED_ID, HARDCODED_USERNAME, HARDCODED_EMAIL)
//        onResult(user)

        getRelatedProfiles(id, KEY_ID1, KEY_ID2, false) { list1 ->
            getRelatedProfiles(id, KEY_ID2, KEY_ID1, false) { list2 ->
                list1.addAll(list2)
                getProfiles { listAll ->
                    for (user in listAll) {
                        if (user.id != id && !list1.contains(user)) {
                            onSuccess(user)
                        }
                    }
                    onFailure()
                }
            }
        }
    }

    //TODO: Test it.
    override fun addRatedProfile(
        idLiking: String,
        idLiked: String,
        isLiked: Boolean,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        val relation = UserRelation(idLiking, idLiked, isLiked)
        val db = FirebaseFirestore.getInstance()
        db.collection(KEY_DB_RELATION).add(relation)
            .addOnSuccessListener {
                Log.d("SUCCESS", "Profile rated!!!")
                onSuccess()
            }
            .addOnFailureListener {
                Log.d("FAILURE", "Error while rating!!!")
                onFailure()
            }
    }

    //TODO: Test it.
    override fun getMatchedProfiles(id: String, onResult: (MutableList<User>) -> Unit) {

        this.getRelatedProfiles(id, KEY_ID1, KEY_ID2, true) {
            onResult(it)
        }

        this.getRelatedProfiles(id, KEY_ID2, KEY_ID1, true) {
            onResult(it)
        }
    }


    override fun getRelatedProfiles(
        id: String,
        keyId1: String,
        keyId2: String,
        checkMatching: Boolean,
        onResult: (MutableList<User>) -> Unit
    ) {

        val db = FirebaseFirestore.getInstance()
        val query = db.collection(KEY_DB_RELATION).whereEqualTo(keyId1, id)
        if (checkMatching) {
            query.whereEqualTo(KEY_MATCH, true)
        }

        query.get().addOnCompleteListener { task ->
            val profiles: MutableList<User> = ArrayList()
            if (task.isSuccessful) {
                //TODO: Adjust selects to table in Firestore.
                for (document in task.result!!) {

                    val map = (document.data as HashMap<*, *>)
                    val newId = map[keyId2].toString()

                    this.getProfile(newId) {
                        profiles.add(it)
                    }
                }
                onResult(profiles)
            } else {
                //TODO: Add some handling of exception.
                Log.w("ERROR", "Error getting matched first profiles.", task.exception)
            }
        }
    }
}