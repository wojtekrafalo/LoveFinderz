package com.example.lovefinderz.model

import com.example.lovefinderz.common.*


data class RegisterRequest(
    var name: String = "",
    var email: String = "",
    var password: String = "",
    var repeatPassword: String = ""
) {

    fun isValid(): Boolean = isUsernameValid(name)
            && isEmailValid(email)
            && arePasswordsSame(password, repeatPassword)
}