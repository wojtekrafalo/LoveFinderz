package com.example.lovefinderz.ui.browser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.example.lovefinderz.R
import com.example.lovefinderz.common.onClick
import com.example.lovefinderz.common.showInfoDialog
import com.example.lovefinderz.profileBrowserPresenter
import kotlinx.android.synthetic.main.fragment_profile_browser.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ProfileBrowserFragment : Fragment(), ProfileBrowserView {

    private val presenter by lazy { profileBrowserPresenter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        presenter.setView(this)
        return inflater.inflate(R.layout.fragment_profile_browser, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.loadFreshProfile()
        profile_browser_like_button.onClick {
            presenter.onRelationTapped(this.requireContext(), true)
        }

        profile_browser_dislike_button.onClick {
            presenter.onRelationTapped(this.requireContext(), false)
        }
    }

    override fun showUsername(username: String) {
        profile_browser_login.text = username
    }

    override fun showPhoto(email: String) {
        //TODO("Not yet implemented"), and probably would be not implemented.
    }

    override fun showAge(dateOfBirth: String) {
        //TODO: add field to show age.
    }

    override fun showProfileLoadingError(errorMessage: String) {
        showInfoDialog(this.requireContext(), errorMessage)
        profile_browser_image.isVisible = false
        profile_browser_login_layout.isVisible = false
        profile_browser_buttons_layout.isVisible = false
    }

    override fun showRelationError(errorMessage: String) {
        showInfoDialog(this.requireContext(), errorMessage)
    }
}