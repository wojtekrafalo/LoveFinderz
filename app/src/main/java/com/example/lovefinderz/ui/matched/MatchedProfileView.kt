package com.example.lovefinderz.ui.matched

import com.example.lovefinderz.model.User

interface MatchedProfileView {

  fun loadMatchedProfile (profile: User)

  fun showErrorMessage(message: String)
}