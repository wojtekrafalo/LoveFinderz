package com.example.lovefinderz.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.lovefinderz.R
import com.example.lovefinderz.common.onClick
import com.example.lovefinderz.common.onTextChanged
import com.example.lovefinderz.common.showInfoDialog
import com.example.lovefinderz.loginPresenter
import kotlinx.android.synthetic.main.fragment_log_in.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class LogInFragment : Fragment(), LogInView {

    private val presenter by lazy { loginPresenter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        presenter.setView(this)
        return inflater.inflate(R.layout.fragment_log_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        log_in_edit_text_email.onTextChanged { presenter.onEmailChanged(it) }
        log_in_edit_text_password.onTextChanged { presenter.onPasswordChanged(it) }

        log_in_button_confirm.onClick { presenter.onLoginTapped() }
        log_in_button_cancel.onClick { findNavController().navigate(R.id.action_LogInFragment_to_WelcomeFragment) }
    }

    override fun showPasswordError() {
        log_in_label_error.error = getString(R.string.show_password_error)
        log_in_label_error.text = getString(R.string.show_password_error)
    }

    override fun showEmailError() {
        log_in_label_error.error = getString(R.string.show_email_error)
        log_in_label_error.text = getString(R.string.show_email_error)
    }

    override fun showEmailAndPasswordError() {
        log_in_label_error.error = getString(R.string.show_email_and_password_error)
        log_in_label_error.text = getString(R.string.show_email_and_password_error)
    }

    override fun onLoginSuccess() {
        findNavController().navigate(R.id.action_LogInFragment_to_ProfileFragment)
    }

    override fun showLoginError() {
        showInfoDialog(this.requireContext(), getString(R.string.show_email_and_password_error))
    }
}