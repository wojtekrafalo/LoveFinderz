package com.example.lovefinderz.protocol

import android.content.Context
import android.preference.PreferenceManager.getDefaultSharedPreferences
import android.util.Log
import com.example.lovefinderz.common.*

import com.example.lovefinderz.model.ProtocolData
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import java.security.InvalidParameterException
import java.security.SecureRandom


class UserSympathy(private val thisUserId: String, private val otherUserId: String, private val onSuccess: () -> Unit, private val onFailure: () -> Unit) {
    private val recordId = generateId()
    private var p: Int = 0
    private val db = FirebaseFirestore.getInstance()
    private var myChoiceKey:String? = getKeyOrNull(recordId, Companion.MY_CHOICE_KEY)

    private fun getKeyOrNull(id: String, key: String): String? {
        //TODO
        return null
    }

    private lateinit var othersChoiceKey:String
    private lateinit var messagingKey:String
    private var likes = false
    private lateinit var garbledCircuit:List<String>

    fun like() {
        confess(true)
    }

    fun dislike() {
        confess(false)
    }


    private fun confess(likes: Boolean) {
        this.likes = likes
        initializeProtocol()
        //TODO run listener
        //runListener()
    }

    private fun runListener() {
        TODO("Not yet implemented")
    }


    private fun initializeProtocol() {
        val protocolDataRef = db.collection("protocol_data").document(recordId)

        protocolDataRef.get().addOnSuccessListener {
            if (it.exists()) {
                Log.d(Companion.TAG, "initializeProtocol: ProtocolData Record found")
                val oldData = it.toObject(ProtocolData::class.java)!!
                if(myChoiceKey != null  || oldData.firstUserChoiceKey != myChoiceKey){
                    secondPartOfProtocol(protocolDataRef, oldData.g!!, oldData.n!!, oldData.x!!)
                }
                else{
                    onFailure()
                }

            } else {
                Log.d(Companion.TAG, "initializeProtocol: ProtocolData Record not found")
                firstPartOfProtocol(protocolDataRef)
            }
        }.addOnFailureListener {
            Log.d(Companion.TAG, "initializeProtocol: Error while updating protocol data: " + it.message)
            onFailure()
        }
    }

    private fun firstPartOfProtocol(protocolDataRef: DocumentReference) {

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

        p = getRandomPositiveVal()
        saveKey(MY_P, p.toString())

        val x = g.toBigInteger().modPow(p.toBigInteger(), n.toBigInteger())

        val choiceKey = if (likes) a1 else a0
        myChoiceKey = choiceKey
        saveKey(MY_CHOICE_KEY, choiceKey)

        val gc = mutableListOf(gc1, gc2, gc3, gc4)
        gc.shuffle(SecureRandom())
        val data = ProtocolData(listOf(thisUserId, otherUserId), gc, g, n, x.toInt(), choiceKey)

        protocolDataRef.set(data).addOnSuccessListener {
            Log.d(Companion.TAG, "firstPartOfProtocol: Data added")
            onSuccess()
        }.addOnFailureListener{
            Log.d(Companion.TAG, "firstPartOfProtocol: Data not added")
            onFailure()
        }
    }

    private fun saveKey(key: String, value: String) {
        //TODO use existing id
        //TODO implement that
        return
    }

    private fun secondPartOfProtocol(
        protocolDataRef: DocumentReference,
        g: Int,
        n: Int,
        x: Int
    ) {
        p = getRandomPositiveVal()
        saveKey(MY_P, p.toString())
        val y = if (likes){
            g.toBigInteger().modPow(p.toBigInteger(), n.toBigInteger())
        }else{
            g.toBigInteger().modPow(p.toBigInteger(), n.toBigInteger()).times(x.toBigInteger()).rem(n.toBigInteger())
        }
        messagingKey = hash(x.toBigInteger().modPow(p.toBigInteger(), n.toBigInteger()).toString())
        saveKey(MESSAGING_KEY, messagingKey)

        protocolDataRef.update("y", y.toInt()).addOnSuccessListener {
            Log.d(Companion.TAG, "secondPartOfProtocol: Data added")
            onSuccess()
        }.addOnFailureListener{
            Log.d(Companion.TAG, "secondPartOfProtocol: Data added")
            onFailure()
        }
    }

    /**
     * Returns true if thisUserId is before otherUserId in order and false if otherUserId is before thisUserId
     */
    private fun orderOnUserIds(): Boolean {
        var i = 0
        while (true) {
            if (thisUserId.length <= i && otherUserId.length <= i) throw InvalidParameterException("Ids can not be equal!")
            if (thisUserId.length <= i) return true
            if (otherUserId.length <= i) return false
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
        private const val MY_CHOICE_KEY = "myChoiceKey"
        private const val MY_P = "p"
        private const val MESSAGING_KEY = "messagingKey"

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