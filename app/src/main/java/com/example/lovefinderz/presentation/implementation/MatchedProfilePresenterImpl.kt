package com.example.lovefinderz.presentation.implementation

import com.example.lovefinderz.firebase.authentication.FirebaseAuthenticationInterface
import com.example.lovefinderz.firebase.database.FirebaseDatabaseInterface
import com.example.lovefinderz.presentation.MatchedProfilePresenter
import com.example.lovefinderz.ui.matched.MatchedProfileView
import javax.inject.Inject

class MatchedProfilePresenterImpl @Inject constructor(
  private val authenticationInterface: FirebaseAuthenticationInterface,
  private val databaseInterface: FirebaseDatabaseInterface
) : MatchedProfilePresenter {

  private lateinit var view: MatchedProfileView

  override fun setView(view: MatchedProfileView) {
    this.view = view
  }

  override fun getMatchedProfiles() {
    databaseInterface.getMatchedProfiles(authenticationInterface.getUserId()) { profiles ->
      view.loadMatchedProfiles(profiles)
    }
  }
}