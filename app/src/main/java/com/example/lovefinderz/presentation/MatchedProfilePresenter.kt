package com.example.lovefinderz.presentation

import com.example.lovefinderz.ui.matched.MatchedProfileView

interface MatchedProfilePresenter : BasePresenter<MatchedProfileView> {

  fun getMatchedProfiles()
}