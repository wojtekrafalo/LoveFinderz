package com.example.lovefinderz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.lovefinderz.common.onClick
import com.example.lovefinderz.ui.welcome.WelcomeView
import kotlinx.android.synthetic.main.fragment_welcome.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class WelcomeFragment : Fragment(), WelcomeView {

    private val presenter by lazy { welcomePresenter() }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        presenter.setView(this)

        return inflater.inflate(R.layout.fragment_welcome, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        welcome_button_log_in.onClick { findNavController().navigate(R.id.action_WelcomeFragment_to_LogInFragment) }
        welcome_button_register.onClick { findNavController().navigate(R.id.action_WelcomeFragment_to_RegisterFragment) }

        presenter.transitLoggedUser()
    }

    override fun transitToProfile() {
        //TODO: Uncomment line below:
//        findNavController().navigate(R.id.action_WelcomeFragment_to_ProfileFragment)
    }
}