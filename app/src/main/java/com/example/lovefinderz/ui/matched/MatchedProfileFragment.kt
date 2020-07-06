package com.example.lovefinderz.ui.matched

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lovefinderz.R
import com.example.lovefinderz.common.showInfoDialog
import com.example.lovefinderz.matchedProfilePresenter
import com.example.lovefinderz.model.User
import kotlinx.android.synthetic.main.fragment_matched_profile.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class MatchedProfileFragment : Fragment(), MatchedProfileView {

    private val presenter by lazy { matchedProfilePresenter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        presenter.setView(this)
        return inflater.inflate(R.layout.fragment_matched_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.getMatchedProfiles()
    }

    override fun loadMatchedProfile(profile: User) {
        //TODO: Add element to recyclerView: 'matched_profile_list' instead of concatenating.
        var str:String = matched_profile_title.text.toString()
        str += "\n\n" + profile.username
        matched_profile_title.text = str
    }

    override fun showErrorMessage(message: String) {
        showInfoDialog(this.requireContext(), message)
    }
}