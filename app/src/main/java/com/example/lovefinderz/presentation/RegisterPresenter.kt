package com.example.lovefinderz.presentation

import com.example.lovefinderz.ui.register.RegisterView

interface RegisterPresenter : BasePresenter<RegisterView> {

  fun onUsernameChanged(username: String)

  fun onEmailChanged(email: String)

  fun onPasswordChanged(password: String)

  fun onRepeatPasswordChanged(repeatPassword: String)

  fun onRegisterTapped()

}