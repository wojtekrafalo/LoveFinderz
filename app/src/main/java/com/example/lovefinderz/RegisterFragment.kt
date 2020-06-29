package com.example.lovefinderz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.lovefinderz.common.onClick
import com.example.lovefinderz.common.onTextChanged
import com.example.lovefinderz.ui.register.RegisterView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
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
        // Inflate the layout for this fragment
        presenter.setView(this)

//        initUi()
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        register_label_error.error = ""

        register_edit_text_login.onTextChanged {presenter.onUsernameChanged(it) }
        register_edit_text_email.onTextChanged { presenter.onEmailChanged(it) }
        register_edit_text_password.onTextChanged { presenter.onPasswordChanged(it) }
        register_edit_text_password_repeat.onTextChanged { presenter.onRepeatPasswordChanged(it) }

//        view.findViewById<TextInputLayout>(R.id.register_input_login)
//            .setOnKeyListener { v, actionId, event ->
//                presenter.onUsernameChanged(event.toString())
//
//                Snackbar.make(view, "Edited", Snackbar.LENGTH_SHORT)
//                    .setAction("Action", null).show()
//                true
//            }

        register_button_cancel.onClick { findNavController().navigate(R.id.action_RegisterFragment_to_WelcomeFragment) }
        register_button_confirm.onClick { presenter.onRegisterTapped() }
//        view.findViewById<Button>(R.id.register_button_cancel).setOnClickListener {
//            findNavController().navigate(R.id.action_RegisterFragment_to_WelcomeFragment)
//        }
//        view.findViewById<Button>(R.id.register_button_confirm)
//            .setOnClickListener { presenter.onRegisterTapped() }
    }

    override fun onRegisterSuccess() {
        println("BreakPoint")
        findNavController().navigate(R.id.action_RegisterFragment_to_ProfileFragment)
    }

    override fun showSignUpError() {
        register_label_error.error = getString(R.string.register_error)
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
}