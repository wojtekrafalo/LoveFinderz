package com.example.lovefinderz.presentation

import com.example.lovefinderz.model.Joke
import com.example.lovefinderz.presentation.BasePresenter
import com.example.lovefinderz.ui.jokes.favorite.FavoriteView

interface FavoriteJokesPresenter : BasePresenter<FavoriteView> {

  fun getFavoriteJokes()

  fun onFavoriteButtonTapped(joke: Joke)
}