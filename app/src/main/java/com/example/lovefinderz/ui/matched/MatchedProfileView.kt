package com.example.lovefinderz.ui.matched

import com.example.lovefinderz.model.User

interface MatchedProfileView {

  fun loadMatchedProfiles (profiles: MutableList<User>)

  fun showErrorMessage(message: String)
}