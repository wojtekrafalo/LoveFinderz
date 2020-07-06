package com.example.lovefinderz.ui.profile

interface ProfileView {

  fun showUsername(username: String)

  fun showEmail(email: String)

  fun showAge(dateOfBirth: String)

  fun showErrorMessage(message: String)
}