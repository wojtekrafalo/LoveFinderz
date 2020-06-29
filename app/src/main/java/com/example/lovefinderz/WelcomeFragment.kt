package com.example.lovefinderz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.lovefinderz.ui.welcome.WelcomeView

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class WelcomeFragment : Fragment(), WelcomeView {

    private val presenter by lazy { welcomePresenter() }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        presenter.setView(this)

        return inflater.inflate(R.layout.fragment_welcome, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.welcome_button_log_in).setOnClickListener {
            findNavController().navigate(R.id.action_WelcomeFragment_to_LogInFragment)
        }

        view.findViewById<Button>(R.id.welcome_button_register).setOnClickListener {
            findNavController().navigate(R.id.action_WelcomeFragment_to_RegisterFragment)
        }

        presenter.viewReady()

        //TODO: check if user is logged in, and transit to ProfileFragment

    }

    override fun startMainScreen() {
        //TODO("Not yet implemented")
    }
}