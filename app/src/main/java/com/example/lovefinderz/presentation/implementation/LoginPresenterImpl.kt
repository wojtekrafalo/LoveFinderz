package com.example.lovefinderz.presentation.implementation

import com.example.lovefinderz.common.isEmailValid
import com.example.lovefinderz.common.isPasswordValid
import com.example.lovefinderz.firebase.authentication.FirebaseAuthenticationInterface
import com.example.lovefinderz.model.LoginRequest
import com.example.lovefinderz.presentation.LoginPresenter
import com.example.lovefinderz.ui.login.LogInView
import javax.inject.Inject

class LoginPresenterImpl @Inject constructor(
    private val authentication: FirebaseAuthenticationInterface
) : LoginPresenter {

  private lateinit var view: LogInView

  private val loginRequest = LoginRequest()

  override fun setView(view: LogInView) {
    this.view = view
  }

  override fun onLoginTapped() {
    val isEmailValid = isEmailValid(loginRequest.email)
    val isPasswordValid = isPasswordValid(loginRequest.password)

    if (loginRequest.isValid()) {
      authentication.login(loginRequest.email, loginRequest.password) { isSuccess ->
        if (isSuccess) {
          view.onLoginSuccess()
        } else {
          view.showLoginError()
        }
      }
    } else {
      if (!isEmailValid)
        view.showEmailError()
      if (!isPasswordValid)
        view.showPasswordError()
      if (!isEmailValid && !isPasswordValid)
        view.showEmailAndPasswordError()
    }
  }

  override fun onEmailChanged(email: String) {
    loginRequest.email = email
  }

  override fun onPasswordChanged(password: String) {
    loginRequest.password = password
  }
}

