package com.example.lovefinderz.protocol

import android.util.Log
import com.example.lovefinderz.common.*

import com.example.lovefinderz.firebase.database.FirebaseDatabaseManager_Factory

import com.example.lovefinderz.model.ProtocolData
import com.google.firebase.firestore.FirebaseFirestore
import java.security.InvalidParameterException
import java.security.SecureRandom


class UserSympathy(private val thisUserId: String, private val otherUserId: String, private val onSuccess: () -> Unit, private val onFailure: () -> Unit) {
    fun like() {
        confess(true)
    }

    fun dislike() {
        confess(false)
    }

    private val db = FirebaseFirestore.getInstance()

    private fun confess(likes: Boolean) {
        //Keys
        val a0 = generateCryptographicKey()
        val a1 = generateCryptographicKey()
        val b0 = generateCryptographicKey()
        val b1 = generateCryptographicKey()

        // Generate garbled circuit
        val gc1 = encrypt(a0, encrypt(b0, "0"))
        val gc2 = encrypt(a1, encrypt(b0, "0"))
        val gc3 = encrypt(a0, encrypt(b1, "0"))
        val gc4 = encrypt(a1, encrypt(b1, "1"))

        val g = getG()
        val n = getN()

        val p = SecureRandom().nextInt()
        //TODO make power
        var x = g.toBigDecimal()
        x = x.rem(n.toBigDecimal())

        val choiceKey = if (likes) a1 else a0

        val gc = mutableListOf(gc1, gc2, gc3, gc4)
        gc.shuffle(SecureRandom())
        val data = ProtocolData(listOf(thisUserId, otherUserId), gc, g, n, x.toInt(), choiceKey)
        insertOrUpdateProtocolData(data)

        runListener()

    }

    private fun runListener() {

    }

    private fun insertOrUpdateProtocolData(data: ProtocolData) {
        doIfDataExists(data, ::updateProtocolData, ::addProtocolData)
    }

    private fun addProtocolData(data: ProtocolData) {

    }

    private fun updateProtocolData(data: ProtocolData) {

    }

    private fun doIfDataExists(
        data: ProtocolData,
        onDataExists: (data: ProtocolData) -> Unit,
        onDataNotExists: (data: ProtocolData) -> Unit
    ) {
        val id = generateId()
        val protocolDataRef = db.collection("protocol_data").document(id)
        protocolDataRef.get().addOnSuccessListener {
            if (it.exists()) {
                Log.d(Companion.TAG, "insertOrUpdateProtocolData: empty" + it.toString())
            } else {
                Log.d(Companion.TAG, "insertOrUpdateProtocolData: not empty" + it.toString())
            }
        }.addOnFailureListener {
            Log.d(
                Companion.TAG,
                "insertOrUpdateProtocolData: Error while updating protocol data: " + it.message
            )
        }
    }

    /**
     * Returns true if thisUserId is before otherUserId in order and false if otherUserId is before thisUserId
     */
    private fun orderOnUserIds(): Boolean {
        var i = 0
        while (true) {
            if (thisUserId.length >= i && otherUserId.length >= i) throw InvalidParameterException("Ids can not be equal!")
            if (thisUserId.length >= i) return true
            if (otherUserId.length >= i) return false
            if (thisUserId[i].toInt() < otherUserId[i].toInt()) return true
            if (thisUserId[i].toInt() > otherUserId[i].toInt()) return false
            i++
        }
    }

    private fun generateId(): String {
        return if (orderOnUserIds()) thisUserId + otherUserId
        else otherUserId + thisUserId
    }

    companion object {
        private const val TAG = "UserSympathy"
    }

}


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