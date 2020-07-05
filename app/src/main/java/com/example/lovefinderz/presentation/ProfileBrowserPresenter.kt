package com.example.lovefinderz.presentation

import com.example.lovefinderz.model.User
import com.example.lovefinderz.ui.browser.ProfileBrowserView

interface ProfileBrowserPresenter : BasePresenter<ProfileBrowserView> {

  fun onRateTapped(isLiked:Boolean)

  fun loadFreshProfile()

  fun showProfileLoadingError()
  fun sendProfileDataToView(user: User)
  fun showProfileRatingError()
}