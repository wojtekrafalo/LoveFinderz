package com.example.lovefinderz.di

import com.example.lovefinderz.presentation.*

import dagger.Component
import javax.inject.Singleton

@Component(modules = [PresentationModule::class])
@Singleton
interface AppComponent {

  fun mainViewPresenter(): MainViewPresenter

  fun registerPresenter(): RegisterPresenter

  fun loginPresenter(): LoginPresenter

  fun profilePresenter(): ProfilePresenter

  fun welcomePresenter(): WelcomePresenter
}