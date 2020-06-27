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
class SignInFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.button_sign_in_cancel).setOnClickListener {
            findNavController().navigate(R.id.action_SignInFragment_to_WelcomeFragment)
        }

        //TODO: add logging of a user.
        view.findViewById<Button>(R.id.button_sign_in_confirm).setOnClickListener {
            findNavController().navigate(R.id.action_SignInFragment_to_ProfileFragment)
        }
    }
}