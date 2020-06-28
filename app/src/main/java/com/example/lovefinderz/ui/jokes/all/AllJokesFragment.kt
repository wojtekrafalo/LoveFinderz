package com.example.lovefinderz.ui.jokes.all

import android.os.Bundle
//import android.support.v4.app.Fragment
//import android.support.v7.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.lovefinderz.R
import com.example.lovefinderz.allJokesPresenter
import com.example.lovefinderz.model.Joke
import com.example.lovefinderz.ui.jokes.all.AllJokesView
import com.example.lovefinderz.ui.jokes.all.list.JokeAdapter

class AllJokesFragment : Fragment(), AllJokesView {

  private val presenter by lazy { allJokesPresenter() }
  private val adapter by lazy { JokeAdapter(presenter::onFavoriteButtonTapped) }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    //TODO: Delete this class and layout below.
    return inflater.inflate(R.layout.fragment_profile_browser, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initUi()
    presenter.setView(this)

    presenter.viewReady()
  }

  override fun addJoke(joke: Joke) {
    adapter.addJoke(joke)
//    noItems.visibility = if (adapter.itemCount!=0) View.INVISIBLE else View.VISIBLE
  }

  private fun initUi() {
//    jokes.layoutManager = LinearLayoutManager(activity)
//    jokes.setHasFixedSize(true)
//    jokes.adapter = adapter
  }

  override fun showNoDataDescription() {
//    noItems.visibility = View.VISIBLE
  }

  override fun hideNoDataDescription() {
//    noItems.visibility = View.GONE
  }

  override fun setFavoriteJokesIds(favoriteJokesIds: List<String>) = adapter.setFavoriteJokesIds(favoriteJokesIds)
}