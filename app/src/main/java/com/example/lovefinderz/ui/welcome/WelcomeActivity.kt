package com.example.lovefinderz.ui.welcome

import android.content.Intent
import android.os.Bundle
//import android.support.v7.app.AppCompatActivity
import androidx.appcompat.app.AppCompatActivity
import com.example.lovefinderz.R
import com.example.lovefinderz.ui.main.MainActivity
import com.example.lovefinderz.welcomePresenter

//import kotlinx.android.synthetic.main.activity_welcome.*

class WelcomeActivity : AppCompatActivity(), WelcomeView {

  private val presenter by lazy { welcomePresenter() }


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
//    setContentView(R.layout.activity_welcome)
    setContentView(R.layout.fragment_welcome)
    presenter.setView(this)

    presenter.viewReady()

//    registerButton.onClick { startActivity(Intent(this, RegisterActivity::class.java)) }
//    loginButton.onClick { startActivity(Intent(this, LoginActivity::class.java)) }
  }

  override fun startMainScreen() = startActivity(MainActivity.getLaunchIntent(this))
}
