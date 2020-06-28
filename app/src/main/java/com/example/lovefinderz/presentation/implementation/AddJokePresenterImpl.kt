package com.example.lovefinderz.presentation.implementation

import android.view.View
import com.example.lovefinderz.common.isValidJoke
import com.example.lovefinderz.firebase.authentication.FirebaseAuthenticationInterface
import com.example.lovefinderz.firebase.database.FirebaseDatabaseInterface
import com.example.lovefinderz.model.Joke
import com.example.lovefinderz.presentation.AddJokePresenter
import com.example.lovefinderz.ui.addJoke.AddJokeView
import javax.inject.Inject

class AddJokePresenterImpl @Inject constructor(
  private val authenticationInterface: FirebaseAuthenticationInterface,
  private val databaseInterface: FirebaseDatabaseInterface
) : AddJokePresenter {

  private lateinit var view: AddJokeView

  private var jokeText = ""

  override fun setView(view: AddJokeView) {
    this.view = view
  }

  override fun addJokeTapped() {
    if (isValidJoke(jokeText)) {
      val joke = Joke("", authenticationInterface.getUserName(), authenticationInterface.getUserId(), jokeText)

      databaseInterface.addNewJoke(joke) { onAddJokeResult(it) }
    }
  }

  override fun onJokeTextChanged(jokeText: String) {
    this.jokeText = jokeText

    if (!isValidJoke(jokeText)) {
      view.showJokeError()
    } else {
      view.removeJokeError()
    }
  }

  private fun onAddJokeResult(isSuccessful: Boolean) {
    if (isSuccessful) {
      view.onJokeAdded()
    } else {
      view.showAddJokeError()
    }
  }
}