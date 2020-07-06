package com.example.lovefinderz.presentation

import com.example.lovefinderz.model.User
import com.example.lovefinderz.ui.browser.ProfileBrowserView

interface ProfileBrowserPresenter : BasePresenter<ProfileBrowserView> {

  fun onRelationTapped(isLiked:Boolean)

  fun loadFreshProfile()

  fun showProfileLoadingError(errorMessage: String)

  fun sendProfileDataToView(user: User)

  fun showRelationError(errorMessage: String)
}