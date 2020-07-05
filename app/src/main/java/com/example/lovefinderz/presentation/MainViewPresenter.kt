package com.example.lovefinderz.presentation

import com.example.lovefinderz.ui.main.MainView

interface MainViewPresenter : BasePresenter<MainView> {

  fun onLogOutTapped()

  fun onHomeTapped()

  fun onBrowseTapped()

  fun onMatchesTapped()
}