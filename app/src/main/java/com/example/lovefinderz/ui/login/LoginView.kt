package com.example.lovefinderz.ui.login

interface LoginView {

  fun showPasswordError()

  fun showEmailError()

  fun showEmailAndPasswordError()

  fun onLoginSuccess()

  fun showLoginError()
}