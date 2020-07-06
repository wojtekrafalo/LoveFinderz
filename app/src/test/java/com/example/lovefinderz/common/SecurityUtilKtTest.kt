package com.example.lovefinderz.common

import org.junit.Test
import java.security.SecureRandom


import java.util.*

class SecurityUtilKtTest {

    @Test
    fun generateCryptographicKeyTest() {
        val keys = ArrayList<String>()
        for (i in 1..100) {
            keys.add(generateCryptographicKey())
        }

        //check if there is no repetitions
        for (i in 0 until keys.size) {
            for (j in 0 until keys.size) {
                assert(!(i != j && keys[i] == keys[j]))
            }
        }
    }

    @Test
    fun hashTest() {
        val s1 = "Test1"
        val s2 = "Test2"
        val s1Again = "Test1"
        assert(hash(s1) == hash(s1Again))
        assert(hash(s1) != hash(s2))
    }

    @Test
    fun encryptAndDecryptTest() {
        val key = generateCryptographicKey()
        val toEncrypt = "Testing source message"
        val encrypted = encrypt(key, toEncrypt)
        assert(encrypted != toEncrypt)
        assert(toEncrypt == decrypt(key, encrypted))
    }

    @Test
    fun advancedEncryptAndDecryptTest(){
        for (i in 1..10000){
            val key = generateCryptographicKey()
            val toEncrypt = hash(SecureRandom().nextLong().toString())
            val encrypted = encrypt(key, toEncrypt)
            assert(encrypted != toEncrypt)
            assert(toEncrypt == decrypt(key, encrypted))
        }
    }

}