package com.example.lovefinderz.ui.login

interface LoginView {

  fun showPasswordError()

  fun showEmailError()

  fun onLoginSuccess()

  fun showLoginError()
}