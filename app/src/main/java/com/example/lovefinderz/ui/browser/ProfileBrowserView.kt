package com.example.lovefinderz.ui.browser

interface ProfileBrowserView {

  fun showUsername(username: String)

  fun showPhoto(email: String)

  fun showAge(dateOfBirth: String)

  fun showProfileLoadingError(errorMessage: String)

  fun showRelationError(errorMessage: String)
}