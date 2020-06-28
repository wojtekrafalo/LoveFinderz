package com.example.lovefinderz.ui.addJoke

import android.os.Bundle
//import android.support.v7.app.AppCompatActivity
import androidx.appcompat.app.AppCompatActivity
import com.example.lovefinderz.R
import com.example.lovefinderz.addJokePresenter
import com.example.lovefinderz.common.showGeneralError
import com.example.lovefinderz.ui.addJoke.AddJokeView
//import kotlinx.android.synthetic.main.activity_add_joke.*

class AddJokeActivity : AppCompatActivity(), AddJokeView {

  private val presenter by lazy { addJokePresenter() }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
//    setContentView(R.layout.activity_add_joke)
    presenter.setView(this)

    initUi()
  }

  private fun initUi() {
//    jokeDescription.onTextChanged { presenter.onJokeTextChanged(it) }
//    addJoke.onClick { presenter.addJokeTapped() }
  }

  override fun showJokeError() {
//    jokeDescription.error = getString(R.string.joke_error)
  }

  override fun removeJokeError() {
//    jokeDescription.error = null
  }

  override fun onJokeAdded() = finish()

  override fun showAddJokeError() = showGeneralError(this)
}