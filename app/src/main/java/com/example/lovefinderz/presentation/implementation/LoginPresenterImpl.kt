package com.example.lovefinderz.presentation.implementation

import android.view.View
import com.example.lovefinderz.common.isEmailValid
import com.example.lovefinderz.common.isPasswordValid
import com.example.lovefinderz.firebase.authentication.FirebaseAuthenticationInterface
import com.example.lovefinderz.model.LoginRequest
import com.example.lovefinderz.presentation.LoginPresenter
import com.example.lovefinderz.ui.login.LoginView
import javax.inject.Inject

class LoginPresenterImpl @Inject constructor(
    private val authentication: FirebaseAuthenticationInterface
) : LoginPresenter {

  private lateinit var view: LoginView

  private val loginRequest = LoginRequest()

  override fun setView(view: LoginView) {
    this.view = view
  }

  override fun onLoginTapped() {
    if (loginRequest.isValid()) {
      authentication.login(loginRequest.email, loginRequest.password) { isSuccess ->
        if (isSuccess) {
          view.onLoginSuccess()
        } else {
          view.showLoginError()
        }
      }
    }
  }

  override fun onEmailChanged(email: String) {
    loginRequest.email = email

    if (!isEmailValid(email)) {
      view.showEmailError()
    }
  }

  override fun onPasswordChanged(password: String) {
    loginRequest.password = password

    if (!isPasswordValid(password)) {
      view.showPasswordError()
    }
  }
}

