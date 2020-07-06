package com.example.lovefinderz.ui.register

interface RegisterView {

  fun onSignUpSuccess()

  fun showSignUpError()

  fun showUsernameError()

  fun showEmailError()

  fun showPasswordError()

  fun showPasswordMatchingError()
}