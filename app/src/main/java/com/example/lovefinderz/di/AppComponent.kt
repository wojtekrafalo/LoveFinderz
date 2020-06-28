package com.example.lovefinderz.di

import com.example.lovefinderz.presentation.*

import dagger.Component
import javax.inject.Singleton

@Component(modules = [PresentationModule::class])
@Singleton
interface AppComponent {

  fun registerPresenter(): RegisterPresenter

  fun loginPresenter(): LoginPresenter

  fun allJokesPresenter(): AllJokesPresenter

  fun favoriteJokesPresenter(): FavoriteJokesPresenter

  fun profilePresenter(): ProfilePresenter

  fun addJokePresenter(): AddJokePresenter

  fun welcomePresenter(): WelcomePresenter
}