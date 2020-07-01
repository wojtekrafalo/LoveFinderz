package com.example.lovefinderz

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.lovefinderz.common.onClick
import com.example.lovefinderz.common.showGeneralError
import com.example.lovefinderz.ui.profile.ProfileView
import kotlinx.android.synthetic.main.fragment_profile.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ProfileFragment : Fragment(), ProfileView {

    private val presenter by lazy { profilePresenter() }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.getProfile()
        //TODO: add dialog window to edit login
        profile_edit_button.onClick {
            Log.d("EDIT", "Edit login of user. Add a dialog!!")
        }

//        view.findViewById<Button>(R.id.profile_edit_button).setOnClickListener {
//
//        }


        //TODO: add using camera
        profile_browser_like_button.onClick {
            Log.d("EDIT", "Edit photo of a user")
        }
//        view.findViewById<Button>(R.id.profile_browser_like_button).setOnClickListener {
//
//        }
    }

    override fun showUsername(username: String) {
        profile_login_text.text = username
    }

    override fun showEmail(email: String) {
        profile_email_text.text = email
    }

    override fun showNumberOfJokes(jokesCount: Int) {
        //TODO("Not yet implemented")
    }

    override fun openWelcome() {
        //TODO: Add some dialog: "Hello to LoveFinderz".
//        trash:
//        val str: String = R.string.welcome_fragment_label.toString()
//        context?.let { showGeneralError(it, str) }

        findNavController().navigate(R.id.action_ProfileFragment_to_WelcomeFragment)
    }
}