package com.example.lovefinderz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ProfileBrowserFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //TODO: add liking and not-liking, encrypting, loading new profile to the Profile Browser.
        view.findViewById<Button>(R.id.profile_browser_not_like_button).setOnClickListener {

        }

        view.findViewById<Button>(R.id.profile_button_change_photo).setOnClickListener {

        }
    }
}