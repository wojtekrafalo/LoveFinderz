package com.example.lovefinderz.presentation

import com.example.lovefinderz.presentation.BasePresenter
import com.example.lovefinderz.ui.welcome.WelcomeView

interface WelcomePresenter : BasePresenter<WelcomeView> {

  fun viewReady()
}
