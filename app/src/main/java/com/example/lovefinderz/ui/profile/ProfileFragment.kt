package com.example.lovefinderz.ui.profile

import android.os.Bundle
//import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.lovefinderz.R
import com.example.lovefinderz.profilePresenter

class ProfileFragment : Fragment(), ProfileView {

  private val presenter by lazy { profilePresenter() }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_profile, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    presenter.setView(this)
    initUi()
    presenter.getProfile()
  }

  private fun initUi() {
//    logoutButton.onClick { presenter.logOut() }
  }

  override fun showUsername(username: String) {
//    userName.text = getString(R.string.username_text, username)
  }

  override fun showEmail(email: String) {
//    userEmail.text = getString(R.string.email_text, email)
  }

  override fun showNumberOfJokes(jokesCount: Int) {
//    numberOfJokes.text = getString(R.string.number_of_jokes_text, jokesCount)
  }

  override fun openWelcome() {
    findNavController().navigate(R.id.action_ProfileFragment_to_WelcomeFragment)
//    startActivity(Intent(activity, WelcomeActivity::class.java))
//    activity?.finish()
  }
}