package com.example.lovefinderz.common

import android.util.Log
import java.math.BigInteger
import java.security.MessageDigest
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import kotlin.math.ceil

val random = SecureRandom()

fun generateCryptographicKey(): String {
    return hash(random.nextLong().toString())
}

fun hash(source:String): String {
    return MessageDigest
        .getInstance("SHA-256")
        .digest(source.toByteArray())
        .fold("", { str, it -> str + "%02x".format(it) })

}

//TODO - it can be upgraded with knowledge contained here: https://stackoverflow.com/questions/49340005/encrypt-decrypt-string-kotlin
//https://www.raywenderlich.com/778533-encryption-tutorial-for-android-getting-started
fun encrypt(key:String, source: String): String {
    println(key)

    val keyAes = SecretKeySpec(key.hexStringToByteArray(), "AES")
    val sourceBytes = source.toByteArray()


    val cipher = Cipher.getInstance("AES/GCM/NoPadding")
    cipher.init(Cipher.ENCRYPT_MODE, keyAes)
    val encrypted = cipher.doFinal(sourceBytes)


    return encrypted.toString()
}

fun decrypt(key:String, source: String): String {
    println(key)
    val keyAes = SecretKeySpec(key.hexStringToByteArray(), "AES")
    val sourceBytes = source.toByteArray()

    val cipher = Cipher.getInstance("AES/GCM/NoPadding")
    cipher.init(Cipher.DECRYPT_MODE, keyAes)
    val decrypted = cipher.doFinal(sourceBytes)


    return decrypted.toString()
}

fun getG(): Int {
    return 522853
}

fun getN(): Int {
    return 49979693
}

fun String.hexStringToByteArray() = ByteArray(this.length / 2) { this.substring(it * 2, it * 2 + 2).toInt(16).toByte() }

