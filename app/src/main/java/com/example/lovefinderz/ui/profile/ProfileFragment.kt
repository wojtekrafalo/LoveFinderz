package com.example.lovefinderz.ui.profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lovefinderz.R
import com.example.lovefinderz.common.onClick
import com.example.lovefinderz.common.showInfoDialog
import com.example.lovefinderz.profilePresenter
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
        presenter.setView(this)
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.getProfile()

        profile_button_change_photo.onClick {
            //TODO: add using camera.
            Log.d("EDIT", "Edit photo of a user")
        }

        profile_button_change_login.onClick {
            //TODO: add dialog window to edit and update database, but I don't have to update authentication table.
            //TODO: Maybe add possibility to change password.
            Log.d("EDIT", "Edit login of user. Add a dialog!!")
        }

        profile_login_description.text = getString(R.string.login_description)
        profile_email_description.text = getString(R.string.email_description)
    }


    override fun showUsername(username: String) {
        profile_login_text.text = username
    }

    override fun showEmail(email: String) {
        profile_email_text.text = email
    }

    override fun showAge(dateOfBirth: String) {
        //TODO: add field to show age.
    }

    override fun showErrorMessage(message: String) {
        showInfoDialog(this.requireContext(), message)
    }
}