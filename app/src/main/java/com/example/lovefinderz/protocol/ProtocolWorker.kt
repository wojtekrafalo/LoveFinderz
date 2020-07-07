package com.example.lovefinderz.protocol

import android.content.Context
import androidx.concurrent.futures.CallbackToFutureAdapter
import androidx.work.Data
import androidx.work.ListenableWorker
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.example.lovefinderz.common.*
import com.example.lovefinderz.model.ProtocolData
import com.google.common.util.concurrent.ListenableFuture
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException

class ProtocolWorker(private val context: Context, workerParams: WorkerParameters) :
    ListenableWorker(context, workerParams) {


    override fun startWork(): ListenableFuture<Result> {
        val thisUserId = inputData.getString(THIS_USER_ID)!!
        val otherUserId = inputData.getString(OTHER_USER_ID)!!
        val id = generateId(thisUserId, otherUserId)
        val docRef = FirebaseFirestore.getInstance().collection("protocol_data").document(id)
        return CallbackToFutureAdapter.getFuture {completer ->
            docRef.addSnapshotListener { documentSnapshot: DocumentSnapshot?, e: FirebaseFirestoreException? ->
                if (e != null) {
                    completer.set(Result.retry())
                    return@addSnapshotListener
                } else {
                    documentSnapshot?.let {
                        val data = documentSnapshot.toObject(ProtocolData::class.java)!!
                        if (data.initializerId!= thisUserId && data.firstUserChoiceKey != inputData.getString(MY_CHOICE_KEY) && inputData.getInt(MESSAGING_KEY_BASE, -1) != data.y){
                            secondPartOfProtocol(docRef, data.g, data.n, data.x!!, inputData.getBoolean(LIKES, false), thisUserId, otherUserId, {completer.set(Result.retry())}, {completer.set(Result.success())}, context)

                            return@addSnapshotListener
                        } else if (data.initializerId == thisUserId && data.y != null && data.encryptedSecondUserChoiceKey1=="" && data.encryptedSecondUserChoiceKey0=="" ) {
                            thirdPartOfProtocol(docRef, inputData.getInt(P, 1), data.n, data.y!!, data.x!!, inputData.getString(OTHERS_KEY_0)!!,inputData.getString(OTHERS_KEY_1)!!, {completer.set(Result.retry())}, {completer.set(Result.success())})

                            return@addSnapshotListener
                        } else if (data.initializerId != thisUserId && data.encryptedSecondUserChoiceKey0 != "" && data.encryptedSecondUserChoiceKey1 != ""){
                            val myKey = resolveMyKey(data)
                            val othersKey = resolveOthersKey(data)
                            fourthPartOfProtocol(myKey, othersKey, data, thisUserId, otherUserId, {completer.set(Result.retry())}, {completer.set(Result.success())})
                            return@addSnapshotListener
                        }

                    }
                }
            }

        }
    }

    private fun resolveOthersKey(data: ProtocolData): String {
        return data.firstUserChoiceKey
    }

    private fun resolveMyKey(data: ProtocolData): String {
        val p = inputData.getInt(P, 1)
        val key = hash(data.x!!.toBigInteger().modPow(p.toBigInteger(), data.n.toBigInteger()).toInt().toString())
        val myKey0 = decrypt(key, data.encryptedSecondUserChoiceKey0)
        val myKey1 = decrypt(key, data.encryptedSecondUserChoiceKey1)
        return if (myKey0.startsWith("0000000000")) myKey0.substring(10)
        else myKey1.substring(10)
    }

    companion object {
        private const val THIS_USER_ID = "user1id"
        private const val OTHER_USER_ID = "user2id"
        private const val P = "p"
        private const val LIKES = "likes"
        private const val MY_CHOICE_KEY = "myChoiceKey"
        private const val OTHERS_KEY_0 = "othersKey0"
        private const val OTHERS_KEY_1 = "othersKey1"
        private const val MESSAGING_KEY_BASE = "messagingKeyBase" //X or Y

        fun createInputDataProtocolWorker(
            user1id: String,
            user2id: String,
            p: Int?,
            likes: Boolean,
            myChoiceKey: String,
            othersKey0: String,
            othersKey1: String,
            messagingKeyBase: Int?
        ): Data {
            val builder = Data.Builder()
            builder.putString(THIS_USER_ID, user1id)
            builder.putString(OTHER_USER_ID, user2id)
            builder.putString(MY_CHOICE_KEY, myChoiceKey)
            builder.putString(OTHERS_KEY_0, othersKey0)
            builder.putString(OTHERS_KEY_1, othersKey1)
            builder.putBoolean(LIKES, likes)
            p?.let { builder.putInt(P, p) }
            messagingKeyBase?.let { builder.putInt(MESSAGING_KEY_BASE, messagingKeyBase) }
            return builder.build()
        }
    }


}