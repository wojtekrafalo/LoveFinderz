package com.example.lovefinderz.ui.jokes.favorite
import com.example.lovefinderz.model.Joke

interface FavoriteView {

  fun showFavoriteJokes(jokes: List<Joke>)

  fun showNoDataDescription()

  fun hideNoDataDescription()

  fun clearItems()
}