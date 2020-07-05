package com.example.lovefinderz.model

import com.example.lovefinderz.common.*


data class RegisterRequest(
    var username: String = "",
    var email: String = "",
    var password: String = "",
    var repeatPassword: String = ""
) {

    fun isValid(): Boolean = isUsernameValid(username)
            && isEmailValid(email)
            && arePasswordsSame(password, repeatPassword)
}