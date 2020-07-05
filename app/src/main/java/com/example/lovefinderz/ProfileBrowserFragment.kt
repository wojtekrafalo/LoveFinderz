package com.example.lovefinderz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lovefinderz.common.onClick
import com.example.lovefinderz.ui.browser.ProfileBrowserView
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
            presenter.onRateTapped(true)
        }

        profile_browser_hate_button.onClick {
            presenter.onRateTapped(false)
        }
    }

    override fun showUsername(username: String) {
        profile_browser_login.text = username
    }

    override fun showPhoto(email: String) {
        //TODO("Not yet implemented"), and probably would be not implemented.
    }

    override fun showAge(dateOfBirth: String) {
        //TODO("Not yet implemented")
    }

    override fun showProfileLoadingError() {
        //TODO: Display error, that there occurred an error while rating.
    }

    override fun showProfileRatingError() {
        //TODO: Display error, that there is no more users to display.
    }
}