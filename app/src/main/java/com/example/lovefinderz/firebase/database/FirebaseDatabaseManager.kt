package com.example.lovefinderz.firebase.database

import android.util.Log
import com.example.lovefinderz.common.parseListOfUsers
import com.example.lovefinderz.model.User
import com.example.lovefinderz.model.UserRelation
import com.example.lovefinderz.model.UserRelationEntry
import com.example.lovefinderz.protocol.UserSympathy
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject
import kotlin.collections.ArrayList

private const val KEY_DB_USER = "user"
private const val KEY_DB_USERS = "users"
private const val KEY_DB_RELATION = "relation"
private const val KEY_EMAIL = "email"
private const val KEY_USERNAME = "username"
private const val KEY_PHOTO = "photo"
private const val KEY_BIRTH = "dateOfBirth"
private const val KEY_ID = "id"
private const val KEY_THIS_USER = "thisUserId"
private const val KEY_OTHER_USER = "otherUserId"
private const val KEY_MATCH = "match"
private const val KEY_RELATED_USERS = "relatedUsers\$app_debug" //relatedUsers\$app_debug

private var firestoreDatabase = FirebaseFirestore.getInstance()
private val HARDCODED_ID = "f74UBon50PVoKI4Z3aKanKDrAOs1"
private val HARDCODED_USERNAME = "bartke"
private val HARDCODED_EMAIL = "bartke@bartke.bartke"

class FirebaseDatabaseManager @Inject constructor(private val database: FirebaseDatabase) :
    FirebaseDatabaseInterface {

    override fun storeUser(
        id: String,
        user: User,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        firestoreDatabase.collection(KEY_DB_USER).document(id).set(user)
            .addOnSuccessListener {
                onSuccess()
                Log.d("SUCCESS", "User added!!!")
            }
            .addOnFailureListener {
                onFailure()
                Log.d("FAILURE", "User not added!!!")
            }
    }

    private fun updateRelatedUserList(
        relation: UserRelation,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        val errorMessage = "Server error while rating."
        firestoreDatabase.collection(KEY_DB_USER)
            .document(relation.thisUserId)
            .update(KEY_RELATED_USERS, FieldValue.arrayUnion(relation.otherUserId))
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onFailure(errorMessage)
            }
    }

    private fun loadRelatedUsers(
        thisUserId: String,
        onSuccess: (List<String>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        val errorMessage = "Server error while rating."
        firestoreDatabase.collection(KEY_DB_USER)
            .document(thisUserId)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    val map = (task.result?.data as HashMap<*, *>)
                    val listStr = (map[KEY_RELATED_USERS] as Iterable<*>).toList()

//                    val user = task.result?.toObject(User::class.java)!!
//                    val list = user.relatedUsers!!
                    val list = parseListOfUsers(listStr)
                    onSuccess(list)

                } else {
                    onFailure(errorMessage)
                    Log.w("ERROR", errorMessage, task.exception)
                }
            }
    }

    override fun loadProfile(
        userId: String,
        onSuccess: (User) -> Unit,
        onFailure: (String) -> Unit
    ) {
        val errorMessage = "Server error while getting profile."
        firestoreDatabase.collection(KEY_DB_USER).document(userId)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    val user = task.result?.toObject(User::class.java)!!
                    onSuccess(user)

                } else {
                    onFailure(errorMessage)
                    Log.w("ERROR", errorMessage, task.exception)
                }
            }
    }

    override fun loadAllProfiles(
        onSuccess: (List<User>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        val errorMessage = "Server error while getting all users."

        firestoreDatabase.collection(KEY_DB_USER)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val list: MutableList<User> = ArrayList()

                    for (document in task.result!!) {

                        val user = document.toObject(User::class.java)
                        list.add(user)
                    }
                    onSuccess(list)
                } else {
                    Log.w("ERROR", "Error getting users.", task.exception)
                    onFailure(errorMessage)
                }
            }
    }

    override fun loadFreshProfile(
        id: String,
        onSuccess: (User) -> Unit,
        onFailure: (String) -> Unit
    ) {
        val errorMessage = "Server error. No profile found."
//        val user = UserResponse(HARDCODED_ID, HARDCODED_USERNAME, HARDCODED_EMAIL)
//        onResult(user)

        this.loadRelatedUsers(id, { listRelated ->
            this.loadAllProfiles({ listAll ->

                var loaded = false
                for (user in listAll) {
                    if (id != user.id && !listRelated.contains(user.id)) {
                        loaded = true
                        onSuccess(user)
                        break
                    }
                }

                if (!loaded)
                    onFailure(errorMessage)
            }, {
                onFailure(it)
            })
        }, {
            onFailure(it)
        })
    }

    override fun storeRelation(
        relation: UserRelation,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        sendProtocol(relation, {
            this.updateRelatedUserList(relation, {
                onSuccess()
            }, {
                onFailure(it)
            })
        }, {
            onFailure(it)
        })

    }


    //TODO: Use onSuccess and onFailure.
    private fun sendProtocol(
        relation: UserRelation,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        val errorMessage = "Error while sending protocol."
        val protocol = UserSympathy(
            relation.thisUserId,
            relation.otherUserId,
            { onSuccess() },
            { onFailure(errorMessage) }
        )
        Log.d("Protocol", protocol.toString())

        //TODO: Comment line below, when protocol would be ready.
        onSuccess()

        //TODO: Uncomment line below, when protocol would be ready.
//        if (relation.isLiked)
//            protocol.like()
//        else
//            protocol.dislike()
    }

    override fun loadMatchingProfiles(
        id: String,
        onSuccess: (User) -> Unit,
        onFailure: (String) -> Unit
    ) {
        val errorMessage = "Server error while getting matched first profiles."
        firestoreDatabase.collection(KEY_DB_RELATION).whereArrayContains(KEY_DB_USERS, id).get()
            .addOnCompleteListener { task ->
                val profiles: MutableList<User> = ArrayList()
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        val relation = document.toObject(UserRelationEntry::class.java)
                        val newId: String
                        newId = if (relation.users.first() == id)
                            relation.users[1]
                        else
                            relation.users.first()

                        this.loadProfile(newId, {user ->
                            profiles.add(user)
                            onSuccess(user)
                        }, {
                            onFailure(it)
                            Log.w("ERROR", it)
                        })
                    }
//                    onSuccess(profiles)
                } else {
                    onFailure(errorMessage)
                    Log.w("ERROR", errorMessage, task.exception)
                }
            }

    }

}