package com.example.lovefinderz.common

import com.google.crypto.tink.KeysetHandle
import com.google.crypto.tink.aead.AeadConfig
import com.google.crypto.tink.aead.AeadFactory
import com.google.crypto.tink.aead.AeadKeyTemplates
import com.google.crypto.tink.integration.android.AndroidKeystoreAesGcm
import com.google.crypto.tink.subtle.AesGcmJce
import java.security.MessageDigest
import java.security.SecureRandom


fun init(){
    AeadConfig.AES_GCM_TYPE_URL
}

fun getRandomPositiveVal():Int{
    return SecureRandom().nextInt(Int.MAX_VALUE)
}

fun generateCryptographicKey(): String {
    return hash(getRandomPositiveVal().toString())
}

fun hash(source:String): String {
    return MessageDigest
        .getInstance("SHA-256")
        .digest(source.toByteArray()).toHexString()
}

fun encrypt(key:String, source: String): String {

    val encryptor = AesGcmJce(key.hexStringToByteArray())
    val ciphertext = encryptor.encrypt(source.toByteArray(Charsets.UTF_8), null)
    return ciphertext.toHexString()
}

fun decrypt(key:String, source: String): String {

    val encryptor = AesGcmJce(key.hexStringToByteArray())
    val decrypted = encryptor.decrypt(source.hexStringToByteArray(), null)
    return decrypted.toString(Charsets.UTF_8)
}

fun getG(): Int {
    return 522853
}

fun getN(): Int {
    return 49979693
}

fun String.hexStringToByteArray() = ByteArray(this.length / 2) { this.substring(it * 2, it * 2 + 2).toInt(16).toByte() }

fun ByteArray.toHexString() = this.fold("", { str, it -> str + "%02x".format(it) })


