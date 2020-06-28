package com.example.lovefinderz.presentation

import com.example.lovefinderz.ui.addJoke.AddJokeView

interface AddJokePresenter : BasePresenter<AddJokeView> {

  fun addJokeTapped()

  fun onJokeTextChanged(jokeText: String)
}