package com.example.lovefinderz.presentation

import com.example.lovefinderz.ui.login.LogInView

interface LoginPresenter : BasePresenter<LogInView> {

  fun onLoginTapped()

  fun onEmailChanged(email: String)

  fun onPasswordChanged(password: String)
}