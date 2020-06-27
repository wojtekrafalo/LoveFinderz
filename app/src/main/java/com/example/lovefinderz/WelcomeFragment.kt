package com.example.lovefinderz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class WelcomeFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_welcome, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.button_welcome_sign_in).setOnClickListener {
            findNavController().navigate(R.id.action_WelcomeFirstFragment_to_SignInFragment)
        }

        view.findViewById<Button>(R.id.button_welcome_sign_up).setOnClickListener {
            findNavController().navigate(R.id.action_WelcomeFirstFragment_to_SignUpFragment)
        }

        //TODO: check if user is logged in, and transit to ProfileFragment

    }
}