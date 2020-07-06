package com.example.lovefinderz.presentation.implementation

import com.example.lovefinderz.common.*
import com.example.lovefinderz.firebase.authentication.FirebaseAuthenticationInterface
import com.example.lovefinderz.firebase.database.FirebaseDatabaseInterface
import com.example.lovefinderz.model.RegisterRequest
import com.example.lovefinderz.model.User
import com.example.lovefinderz.model.UserDateOfBirth
import com.example.lovefinderz.presentation.RegisterPresenter
import com.example.lovefinderz.ui.register.RegisterView
import java.util.*
import javax.inject.Inject

private val DATE_OF_BIRTH = UserDateOfBirth(1800, 1, 1)

class RegisterPresenterImpl @Inject constructor(
    private val database: FirebaseDatabaseInterface,
    private val authentication: FirebaseAuthenticationInterface
) : RegisterPresenter {

    private lateinit var view: RegisterView

    private val userData = RegisterRequest()

    override fun setView(view: RegisterView) {
        this.view = view
    }

    override fun onUsernameChanged(username: String) {
        userData.username = username
    }

    override fun onEmailChanged(email: String) {
        userData.email = email
    }

    override fun onPasswordChanged(password: String) {
        userData.password = password
    }

    override fun onRepeatPasswordChanged(repeatPassword: String) {
        userData.repeatPassword = repeatPassword
    }

    override fun onRegisterTapped() {

        val isUsernameValid = isUsernameValid(userData.username)
        val isEmailValid = isEmailValid(userData.email)
        val isPasswordValid = isPasswordValid(userData.password)
        val arePasswordsSame = arePasswordsSame(userData.password, userData.repeatPassword)

        if (userData.isValid()) {
            val (username, email, password) = userData

            authentication.register(email, password, username) { isSuccessful ->
                onRegisterResult(isSuccessful, username, email)
            }
        } else {
            if (!isUsernameValid)
                view.showUsernameError()
            if (!isEmailValid)
                view.showEmailError()
            if (!isPasswordValid)
                view.showPasswordError()
            if (!arePasswordsSame)
                view.showPasswordMatchingError()
        }
    }

    //TODO: Add dateOfBirth
    private fun onRegisterResult(isSuccessful: Boolean, username: String, email: String) {
        if (isSuccessful) {
            createUser(
                username,
                email,
                DATE_OF_BIRTH,
                { view.onSignUpSuccess() },
                { view.showSignUpError() }
            )
        } else {
            view.showSignUpError()
        }
    }

    private fun createUser(
        username: String,
        email: String,
        dateOfBirth: UserDateOfBirth,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        val id = authentication.getUserId()
        val user = User(id, username, email, dateOfBirth)
        database.storeUser(id, user, onSuccess, onFailure)
    }
}

