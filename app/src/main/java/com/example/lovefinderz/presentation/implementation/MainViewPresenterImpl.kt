package com.example.lovefinderz.presentation.implementation

import com.example.lovefinderz.firebase.authentication.FirebaseAuthenticationInterface
import com.example.lovefinderz.firebase.database.FirebaseDatabaseInterface
import com.example.lovefinderz.presentation.MainViewPresenter
import com.example.lovefinderz.ui.main.MainView
import javax.inject.Inject

class MainViewPresenterImpl @Inject constructor(
  private val database: FirebaseDatabaseInterface,
  private val authentication: FirebaseAuthenticationInterface
) : MainViewPresenter {

  private lateinit var view: MainView

  override fun setView(view: MainView) {
    this.view = view
  }

  override fun onLogOutTapped() {
    authentication.logOut {
      view.onLogOutSuccess()
    }
  }

  override fun onHomeTapped() {
    authentication.transitIfLogged{
      view.onHomeSuccess()
    }
  }

  override fun onBrowseTapped() {
    //TODO: load BrowseProfileFragment
    authentication.transitIfLogged{
      view.onBrowseSuccess()
    }
  }

  override fun onMatchesTapped() {
    //TODO: Add new window to show matches of specific user.
    authentication.transitIfLogged{
      view.onMatchesTapped()
    }
  }
}

