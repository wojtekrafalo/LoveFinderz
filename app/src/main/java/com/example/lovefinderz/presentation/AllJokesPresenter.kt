package com.example.lovefinderz.presentation

import com.example.lovefinderz.model.Joke
import com.example.lovefinderz.ui.jokes.all.AllJokesView


interface AllJokesPresenter : BasePresenter<AllJokesView> {

  fun viewReady()

  fun getAllJokes()

  fun onFavoriteButtonTapped(joke: Joke)
}