package com.example.lovefinderz.protocol

import android.content.Context
import androidx.concurrent.futures.CallbackToFutureAdapter
import androidx.work.ListenableWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.common.util.concurrent.ListenableFuture
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException

class ProtocolWorker(context: Context, workerParams: WorkerParameters) : ListenableWorker(context, workerParams) {



    override fun startWork(): ListenableFuture<Result> {
        return CallbackToFutureAdapter.getFuture {
            FirebaseFirestore.getInstance().collection("").document("").addSnapshotListener{ documentSnapshot: DocumentSnapshot?, firebaseFirestoreException: FirebaseFirestoreException? ->
                //Byc moze zignoruj pierwszy raz
                /*
                sprawdz czy rekord ten ma firstUserChoiceKey == myChoiceKey && y!=null a wtedy policz klucze prywatne zaszyfruj nimi others key 0 oraz others key 1 po czym wrzuc w baze
                w przeciwnym wypadku wykonaj secondPartOfProtocol

                w przeciwnym wypadku jezeli secondUserChoiceKey1 != "" && secondUserChoiceKey2 != "" wykonaj czwartyEtap
                 */
                it.set(Result.success())
                it.set(Result.retry())
                it.set(Result.failure())

            }

        }
    }

    companion object{
        const val USER_1_ID = "user1id"
        const val USER_2_ID = "user2id"
        const val P = "p"
        const val LIKES = "likes"
        const val MY_CHOICE_KEY = "myChoiceKey"
        const val OTHERS_KEY_0 = "othersKey0"
        const val OTHERS_KEY_1 = "othersKey1"
        const val MESSAGING_KEY_BASE = "messagingKeyBase" //X or Y
    }
}