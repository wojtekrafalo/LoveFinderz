package com.example.lovefinderz.presentation.implementation


import android.view.View
import com.example.lovefinderz.firebase.authentication.FirebaseAuthenticationInterface
import com.example.lovefinderz.firebase.database.FirebaseDatabaseInterface
import com.example.lovefinderz.model.Joke
import com.example.lovefinderz.presentation.AllJokesPresenter
import com.example.lovefinderz.ui.jokes.all.AllJokesView
import javax.inject.Inject

class AllJokesPresenterImpl @Inject constructor(
  private val authenticationInterface: FirebaseAuthenticationInterface,
  private val databaseInterface: FirebaseDatabaseInterface
) : AllJokesPresenter {

  private lateinit var view: AllJokesView

  override fun setView(view: AllJokesView) {
    this.view = view
  }

  override fun viewReady() {
    databaseInterface.getFavoriteJokes(authenticationInterface.getUserId()) { onFavoriteJokesResult(it) }
    getAllJokes()
  }

  private fun onFavoriteJokesResult(favoriteJokes: List<Joke>) = view.setFavoriteJokesIds(favoriteJokes.map { it.id })

  override fun getAllJokes() = databaseInterface.listenToJokes { view.addJoke(it) }

  override fun onFavoriteButtonTapped(joke: Joke) {
    databaseInterface.changeJokeFavoriteStatus(joke, authenticationInterface.getUserId())
  }
}

