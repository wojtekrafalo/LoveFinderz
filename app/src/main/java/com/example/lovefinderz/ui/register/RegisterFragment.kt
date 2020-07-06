package com.example.lovefinderz.ui.register

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.lovefinderz.R
import com.example.lovefinderz.common.onClick
import com.example.lovefinderz.common.onTextChanged
import com.example.lovefinderz.common.showInfoDialog
import com.example.lovefinderz.registerPresenter
import kotlinx.android.synthetic.main.fragment_register.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class RegisterFragment : Fragment(), RegisterView {

    private val presenter by lazy { registerPresenter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        presenter.setView(this)
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        register_edit_text_login.onTextChanged {presenter.onUsernameChanged(it) }
        register_edit_text_email.onTextChanged { presenter.onEmailChanged(it) }
        register_edit_text_password.onTextChanged { presenter.onPasswordChanged(it) }
        register_edit_text_password_repeat.onTextChanged { presenter.onRepeatPasswordChanged(it) }

        register_button_cancel.onClick { findNavController().navigate(R.id.action_RegisterFragment_to_WelcomeFragment) }
        register_button_confirm.onClick {
            Log.d("SUCCESS", "Register confirmed")
            presenter.onRegisterTapped()
        }
    }

    override fun onSignUpSuccess() {
        findNavController().navigate(R.id.action_RegisterFragment_to_WelcomeFragment)
        val message = getString(R.string.register_success)
        this.showMessageDialog(message)
    }

    override fun showSignUpError() {
        val message = getString(R.string.register_error)
        this.showMessageDialog(message)
    }

    override fun showUsernameError() {
        register_label_error.error = getString(R.string.show_login_error)
        register_label_error.text = getString(R.string.show_login_error)
    }

    override fun showEmailError() {
        register_label_error.error = getString(R.string.show_email_error)
        register_label_error.text = getString(R.string.show_email_error)
    }

    override fun showPasswordError() {
        register_label_error.error = getString(R.string.show_password_error)
        register_label_error.text = getString(R.string.show_password_error)
    }

    override fun showPasswordMatchingError() {
        register_label_error.error = getString(R.string.repeat_password_error)
        register_label_error.text = getString(R.string.repeat_password_error)
    }

    private fun showMessageDialog(message:String) {
        showInfoDialog(this.requireContext(), message)
    }
}