package com.example.lovefinderz.presentation.implementation

import com.example.lovefinderz.firebase.authentication.FirebaseAuthenticationInterface
import com.example.lovefinderz.presentation.WelcomePresenter
import com.example.lovefinderz.ui.welcome.WelcomeView
import javax.inject.Inject

class WelcomePresenterImpl @Inject constructor(
    private val authenticationInterface: FirebaseAuthenticationInterface
) : WelcomePresenter {

  private lateinit var view: WelcomeView

  override fun setView(view: WelcomeView) {
    this.view = view
  }

  override fun transitLoggedUser() {
    if (authenticationInterface.getUserId().isNotBlank()) {
      view.transitToProfile()
    }
  }
}