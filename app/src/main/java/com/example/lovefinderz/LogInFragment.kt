package com.example.lovefinderz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.lovefinderz.common.onTextChanged
import com.example.lovefinderz.common.showGeneralError
import com.example.lovefinderz.ui.login.LoginView
import kotlinx.android.synthetic.main.fragment_log_in.*

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

        log_in_edit_text_email.onTextChanged {presenter.onEmailChanged(it) }
        log_in_edit_text_password.onTextChanged {presenter.onPasswordChanged(it) }
        view.findViewById<Button>(R.id.log_in_button_confirm).setOnClickListener {
//            findNavController().navigate(R.id.action_LogInFragment_to_ProfileFragment)
            presenter.onLoginTapped()
        }
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
        println("BreakPoint")
        findNavController().navigate(R.id.action_LogInFragment_to_ProfileFragment)
    }

    override fun showLoginError() {
        log_in_label_error.error = getString(R.string.log_in_error)
        log_in_label_error.text = getString(R.string.log_in_error)

        //TODO: Check if line below works.
//        showGeneralError(MainActivity())

    }
}