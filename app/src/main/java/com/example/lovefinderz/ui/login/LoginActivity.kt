package com.example.lovefinderz.ui.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lovefinderz.R
import com.example.lovefinderz.common.showGeneralError
import com.example.lovefinderz.loginPresenter
//import android.support.v7.app.AppCompatActivity
import com.example.lovefinderz.ui.login.LoginView
import com.example.lovefinderz.ui.main.MainActivity
//import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), LoginView {

  private val presenter by lazy { loginPresenter() }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
//    setContentView(R.layout.activity_login)
    presenter.setView(this)
    initUi()
  }

  private fun initUi() {
//    emailInput.onTextChanged { presenter.onEmailChanged(it) }
//    passwordInput.onTextChanged { presenter.onPasswordChanged(it) }
//    loginButton.onClick { presenter.onLoginTapped() }
  }

  override fun showPasswordError() {
//    passwordInput.error = getString(R.string.password_error)
  }

  override fun showEmailError() {
//    emailInput.error = getString(R.string.email_error)
  }

  override fun onLoginSuccess() = startActivity(MainActivity.getLaunchIntent(this))

  override fun showLoginError() = showGeneralError(this)
}