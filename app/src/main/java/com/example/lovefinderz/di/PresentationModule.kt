package com.example.lovefinderz.di

import com.example.lovefinderz.presentation.*
import com.example.lovefinderz.presentation.implementation.*
import dagger.Binds
import dagger.Module

@Module(includes = [InteractionModule::class])
abstract class PresentationModule {

  @Binds
  abstract fun loginPresenter(loginPresenter: LoginPresenterImpl): LoginPresenter

  @Binds
  abstract fun registerPresenter(registerPresenter: RegisterPresenterImpl): RegisterPresenter

  @Binds
  abstract fun mainViewPresenter(mainViewPresenter: MainViewPresenterImpl): MainViewPresenter

  @Binds
  abstract fun profilePresenter(profilePresenterImpl: ProfilePresenterImpl): ProfilePresenter

  @Binds
  abstract fun profileBrowserPresenter(profileBrowserPresenterImpl: ProfileBrowserPresenterImpl): ProfileBrowserPresenter

  @Binds
  abstract fun matchedProfilePresenter(matchedProfilePresenterImpl: MatchedProfilePresenterImpl): MatchedProfilePresenter

  @Binds
  abstract fun welcomePresenter(welcomePresenterImpl: WelcomePresenterImpl): WelcomePresenter
}