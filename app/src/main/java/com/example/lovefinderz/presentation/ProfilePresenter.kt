package com.example.lovefinderz.presentation

import com.example.lovefinderz.ui.profile.ProfileView

interface ProfilePresenter : BasePresenter<ProfileView> {

  fun getProfile()

  fun logOut()
}