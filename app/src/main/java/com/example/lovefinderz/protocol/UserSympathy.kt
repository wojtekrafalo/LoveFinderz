package com.example.lovefinderz.protocol

import android.content.Context
import android.util.Log
import androidx.work.Data

import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.example.lovefinderz.common.*

import com.example.lovefinderz.model.ProtocolData
import com.google.firebase.firestore.DocumentReference

import com.google.firebase.firestore.FirebaseFirestore

import java.security.SecureRandom


class UserSympathy(private val thisUserId: String, private val otherUserId: String, private val onSuccess: () -> Unit = {}, private val onFailure: () -> Unit = {}, private val context:Context) {
    private val recordId = generateId(thisUserId, otherUserId)
    private val db = FirebaseFirestore.getInstance()

    private var likes = false

    fun like() {
        confess(true)
    }

    fun dislike() {
        confess(false)
    }


    private fun confess(likes: Boolean) {
        this.likes = likes
        initializeProtocol()
    }

    private fun initializeProtocol() {
        val protocolDataRef = db.collection("protocol_data").document(recordId)

        protocolDataRef.get().addOnSuccessListener {
            if (it.exists()) {
                Log.d(Companion.TAG, "initializeProtocol: ProtocolData Record found")
                val oldData = it.toObject(ProtocolData::class.java)!!
                if(oldData.initializatorId != thisUserId){
                    secondPartOfProtocol(protocolDataRef, oldData.g!!, oldData.n!!, oldData.x!!, likes, thisUserId, otherUserId, onFailure, onSuccess)
                }
                else{
                    onFailure()
                }

            } else {
                Log.d(Companion.TAG, "initializeProtocol: ProtocolData Record not found")
                firstPartOfProtocol(protocolDataRef, likes, thisUserId, otherUserId, onFailure, onSuccess)
            }
        }.addOnFailureListener {
            Log.d(Companion.TAG, "initializeProtocol: Error while updating protocol data: " + it.message)
            onFailure()
        }
    }



    private fun saveKey(key: String, value: String) {
        //TODO?? use existing id
        //TODO?? implement that
        return
    }

    private fun getKeyOrNull(key: String): String? {
        //TODO?? user recordId
        //TODO?? implement that
        return null
    }

    companion object {
        private const val TAG = "UserSympathy"

    }
}
















/*    private fun createInputDataForUri(): Data {
        val builder = Data.Builder()
        imageUri?.let {
            builder.putString(KEY_IMAGE_URI, imageUri.toString())
        }
        return builder.build()

                val protocolBackgroundWork: WorkRequest = OneTimeWorkRequestBuilder<ProtocolWorker>().setInputData().build()
        WorkManager.getInstance(context).enqueue(protocolBackgroundWork)
    }*/



/*
    Sprawdź, czy firebase jest już info dla waszej relacji, jeżeli nie ma:

    START TRANSACTION
    1. Alice wyświetla się bob - mamy jego id
    2. W bazie lokalnej generujemy i dodajemy 4 klucze, szyfrujemy i wysylamy do firebase 4 wartosci zaszyfrowane + klucz
    3. Pobieramy z serwera g
    4. Zapisujemy p oraz wysylamy do firebase g^p
    STOP TRANSACTION

    W przeciwnym wypadku:

    1. Wyślij Y z wyborem


    W tle:

    Jeżeli to ty jestes Alice:
    1. Oczekuj na notyfikację o przyjściu Y i gdy to nastąpi wyślij c0 oraz c1 zaszyfrowane na serwer

    Jeżeli to ty jesteś Bob:
    1. Oczekuj na odpowiedź z c0 oraz c1, gdy przyjdą, pobierz je i odszyfruj
    2. Pobierz klucz Alice oraz 4 zaszyfrowane wartości
    3. Odszyfruj odpowiedź
    4. Wyślij na serwer
 */