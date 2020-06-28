package com.example.lovefinderz.ui.jokes.all

import com.example.lovefinderz.model.Joke

interface AllJokesView {

  fun showNoDataDescription()

  fun hideNoDataDescription()

  fun setFavoriteJokesIds(favoriteJokesIds: List<String>)

  fun addJoke(joke: Joke)
}