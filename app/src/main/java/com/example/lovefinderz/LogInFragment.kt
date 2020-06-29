package com.example.lovefinderz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.lovefinderz.ui.login.LoginView
import kotlinx.android.synthetic.main.fragment_log_in.*
import kotlinx.android.synthetic.main.fragment_register.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class LogInFragment : Fragment(), LoginView {

    private val presenter by lazy { loginPresenter() }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        presenter.setView(this)
        return inflater.inflate(R.layout.fragment_log_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.log_in_button_cancel).setOnClickListener {
            findNavController().navigate(R.id.action_LogInFragment_to_WelcomeFragment)
        }

        //TODO: Add liveChange:
//    emailInput.onTextChanged { presenter.onEmailChanged(it) }
//    passwordInput.onTextChanged { presenter.onPasswordChanged(it) }
        //TODO: add logging of a user.
        view.findViewById<Button>(R.id.log_in_button_confirm).setOnClickListener {
//            findNavController().navigate(R.id.action_LogInFragment_to_ProfileFragment)
            presenter.onLoginTapped()
        }
    }

    override fun showPasswordError() {
        log_in_label_error.error = getString(R.string.show_password_error)
    }

    override fun showEmailError() {
        register_label_error.error = getString(R.string.show_email_error)
    }

    override fun onLoginSuccess() {
        findNavController().navigate(R.id.action_LogInFragment_to_ProfileFragment)
    }

    override fun showLoginError() {
        log_in_label_error.error = getString(R.string.log_in_error)
    }
}