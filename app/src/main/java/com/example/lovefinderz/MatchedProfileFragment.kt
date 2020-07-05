package com.example.lovefinderz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.lovefinderz.common.onClick
import com.example.lovefinderz.model.User
import com.example.lovefinderz.ui.matched.MatchedProfileView
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
        //TODO: Now it doesn't work. Maybe add it later.
//        matched_profile_cancel_button.onClick {
//            findNavController().navigate(R.id.action_MatchedProfileFragment_to_ProfileFragment)
//        }
    }

    override fun loadMatchedProfiles(profiles: MutableList<User>) {
        //TODO: Add elements to recyclerView: 'matched_profile_list'
    }

}