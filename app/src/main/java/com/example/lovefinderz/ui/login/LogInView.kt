package com.example.lovefinderz.ui.login

interface LogInView {

  fun showPasswordError()

  fun showEmailError()

  fun showEmailAndPasswordError()

  fun onLoginSuccess()

  fun showLoginError()
}