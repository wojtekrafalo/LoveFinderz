package com.example.lovefinderz.common

import android.util.Log
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.example.lovefinderz.model.ProtocolData
import com.example.lovefinderz.model.UserRelationEntry
import com.example.lovefinderz.protocol.ProtocolWorker
import com.example.lovefinderz.protocol.ProtocolWorker.Companion.createInputDataProtocolWorker
import com.example.lovefinderz.protocol.UserSympathy
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import java.security.InvalidParameterException
import java.security.SecureRandom


/**
 * Returns true if thisUserId is before otherUserId in order and false if otherUserId is before thisUserId
 */
fun orderOnUserIds(thisUserId:String, otherUserId: String): Boolean {
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

fun generateId(thisUserId: String, otherUserId: String): String {
    return if (orderOnUserIds(thisUserId, otherUserId)) thisUserId + otherUserId
    else otherUserId + thisUserId
}

fun firstPartOfProtocol(protocolDataRef: DocumentReference, likes: Boolean, thisUserId: String, otherUserId: String, onFailure: () -> Unit, onSuccess: () -> Unit): OneTimeWorkRequest {

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

    val p = getRandomPositiveVal()

    val x = g.toBigInteger().modPow(p.toBigInteger(), n.toBigInteger())

    val choiceKey = if (likes) a1 else a0

    val gc = mutableListOf(gc1, gc2, gc3, gc4)
    gc.shuffle(SecureRandom())
    val data = ProtocolData(thisUserId, listOf(thisUserId, otherUserId), gc, g, n, x.toInt(), choiceKey)

    protocolDataRef.set(data).addOnSuccessListener {
        onSuccess()
    }.addOnFailureListener{
        onFailure()
    }

    val workerData = createInputDataProtocolWorker(thisUserId, otherUserId, p, likes, choiceKey, b0, b1, x.toInt())
    return OneTimeWorkRequestBuilder<ProtocolWorker>().setInputData(workerData).build()
}


fun secondPartOfProtocol(protocolDataRef: DocumentReference, g: Int, n: Int, x: Int, likes: Boolean, thisUserId: String, otherUserId: String, onFailure: () -> Unit, onSuccess: () -> Unit): OneTimeWorkRequest {
    val p = getRandomPositiveVal()

    val y = if (likes){
        g.toBigInteger().modPow(p.toBigInteger(), n.toBigInteger())
    }else{
        g.toBigInteger().modPow(p.toBigInteger(), n.toBigInteger()).times(x.toBigInteger()).rem(n.toBigInteger())
    }
    val messagingKey = hash(x.toBigInteger().modPow(p.toBigInteger(), n.toBigInteger()).toString())


    protocolDataRef.update("y", y.toInt()).addOnSuccessListener {
        onSuccess()
    }.addOnFailureListener{
        onFailure()
    }
    val workerData = createInputDataProtocolWorker(thisUserId, otherUserId, p, likes, "", "", "", x.toInt())
    return OneTimeWorkRequestBuilder<ProtocolWorker>().setInputData(workerData).build()
}

fun thirdPartOfProtocol(protocolDataRef: DocumentReference, p:Int, n:Int, y:Int, x:Int, likes: Boolean, othersKey0:String, othersKey1: String, thisUserId: String, otherUserId: String, onFailure: () -> Unit, onSuccess: () -> Unit): OneTimeWorkRequest {
    val k0 = hash(y.toBigInteger().modPow(p.toBigInteger(), n.toBigInteger()).toInt().toString())
    val k1 = hash(y.toBigInteger().modPow(p.toBigInteger(), n.toBigInteger()).divide(x.toBigInteger()).rem(n.toBigInteger()).toInt().toString())
    val c0 = encrypt(k0, othersKey0)
    val c1 = encrypt(k1, othersKey1)
    protocolDataRef.update(mapOf("encryptedSecondUserChoiceKey0" to c0, "encryptedSecondUserChoiceKey1" to c1)).addOnSuccessListener { onSuccess() }.addOnFailureListener{onFailure()}
    val workerData = createInputDataProtocolWorker(thisUserId, otherUserId, p, likes, "", othersKey0, othersKey1, x)
    return OneTimeWorkRequestBuilder<ProtocolWorker>().setInputData(workerData).build()
}

fun fourthPartOfProtocol(myKey:String, othersKey:String, data: ProtocolData, thisUserId: String, otherUserId: String, onFailure: () -> Unit, onSuccess: () -> Unit){
    for(gc in data.gc){
        val out = decrypt(othersKey, decrypt(myKey, gc))
        if (out=="1" || out == "0"){
            val relation = UserRelationEntry("", listOf(thisUserId, otherUserId))
            FirebaseFirestore.getInstance().collection("relation").add(relation).addOnSuccessListener { onSuccess() }.addOnFailureListener{onFailure()}
        }
    }
    onFailure()
}