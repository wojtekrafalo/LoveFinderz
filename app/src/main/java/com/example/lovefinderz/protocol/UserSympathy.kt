package com.example.lovefinderz.protocol

import com.example.lovefinderz.common.*
import java.security.SecureRandom
import java.util.*
import kotlin.math.pow


class UserSympathy(private val thisUserId: String, private val otherUserId: String) {

    fun like() {
        confess(true)
    }

    fun dislike() {
        confess(false)
    }

    private fun confess(likes:Boolean){
        // Grabled circuit
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

        val x = g.toBigDecimal().pow(p).rem(n.toBigDecimal())

        val choiceKey = if (likes) a1 else a0

        sendDataToServer(gc1, gc2, gc3, gc4, g, n, x.toInt(), choiceKey)
        runListener()

    }

    private fun sendDataToServer(
        gc1: String,
        gc2: String,
        gc3: String,
        gc4: String,
        g: Int,
        n: Int,
        x: Int,
        choiceKey: String
    ) {
        TODO("Not yet implemented")
    }

    private fun runListener() {
        TODO("Not yet implemented")
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