package com.example.lovefinderz.presentation.implementation

import com.example.lovefinderz.common.*
import com.example.lovefinderz.firebase.authentication.FirebaseAuthenticationInterface
import com.example.lovefinderz.firebase.database.FirebaseDatabaseInterface
import com.example.lovefinderz.model.RegisterRequest
import com.example.lovefinderz.presentation.RegisterPresenter
import com.example.lovefinderz.ui.register.RegisterView
import javax.inject.Inject

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
    userData.name = username

//    if (!isUsernameValid(username)) {
//      view.showUsernameError()
//    }
  }

  override fun onEmailChanged(email: String) {
    userData.email = email

//    if (!isEmailValid(email)) {
//      view.showEmailError()
//    }
  }

  override fun onPasswordChanged(password: String) {
    userData.password = password

//    if (!isPasswordValid(password)) {
//      view.showPasswordError()
//    }
  }

  override fun onRepeatPasswordChanged(repeatPassword: String) {
    userData.repeatPassword = repeatPassword

//    if (!arePasswordsSame(userData.password, repeatPassword)) {
//      view.showPasswordMatchingError()
//    }
  }

  override fun onRegisterTapped() {
    println("BreakPoint")

    val isUsernameValid = isUsernameValid(userData.name)
    val isEmailValid = isEmailValid(userData.email)
    val isPasswordValid = isPasswordValid(userData.password)
    val arePasswordsSame = arePasswordsSame(userData.password, userData.repeatPassword)

    if (userData.isValid()) {
      val (name, email, password) = userData

      authentication.register(email, password, name) { isSuccessful ->
        onRegisterResult(isSuccessful, name, email)
      }
    } else {
      if (isUsernameValid)
        view.showUsernameError()
      if (isEmailValid)
        view.showEmailError()
      if (isPasswordValid)
        view.showPasswordError()
      if (arePasswordsSame)
        view.showPasswordMatchingError()
    }
  }

  private fun onRegisterResult(isSuccessful: Boolean, name: String, email: String) {
    if (isSuccessful) {
      createUser(name, email)
      view.onRegisterSuccess()
    } else {
      view.showSignUpError()
    }
  }

  private fun createUser(name: String, email: String) {
    val id = authentication.getUserId()

    database.createUser(id, name, email)
  }
}

