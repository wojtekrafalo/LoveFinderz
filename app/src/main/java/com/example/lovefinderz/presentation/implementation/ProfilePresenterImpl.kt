package com.example.lovefinderz.presentation.implementation

import com.example.lovefinderz.firebase.authentication.FirebaseAuthenticationInterface
import com.example.lovefinderz.firebase.database.FirebaseDatabaseInterface
import com.example.lovefinderz.presentation.ProfilePresenter
import com.example.lovefinderz.ui.profile.ProfileView
import javax.inject.Inject

class ProfilePresenterImpl @Inject constructor(
  private val authenticationInterface: FirebaseAuthenticationInterface,
  private val databaseInterface: FirebaseDatabaseInterface
) : ProfilePresenter {

  private lateinit var view: ProfileView

  override fun setView(view: ProfileView) {
    this.view = view
  }

  override fun getProfile() {
    databaseInterface.getProfile(authenticationInterface.getUserId()) {
      val userId = authenticationInterface.getUserId()

      view.showUsername(it.username)
      view.showEmail(it.email)
      it.favoriteUsers?.count { it.id == userId }?.let { it1 -> view.showNumberOfJokes(it1) }
    }
  }

  override fun logOut() = authenticationInterface.logOut { view.openWelcome() }
}