package com.example.lovefinderz.model

import com.example.lovefinderz.common.*


data class LoginRequest(var email: String = "",
                        var password: String = "") {

    fun isValid() = isEmailValid(email) && isPasswordValid(password)
}