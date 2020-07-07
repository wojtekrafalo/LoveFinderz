package com.example.lovefinderz.presentation.implementation

import android.content.Context
import com.example.lovefinderz.firebase.authentication.FirebaseAuthenticationInterface
import com.example.lovefinderz.firebase.database.FirebaseDatabaseInterface
import com.example.lovefinderz.model.User
import com.example.lovefinderz.model.UserRelation
import com.example.lovefinderz.presentation.ProfileBrowserPresenter
import com.example.lovefinderz.ui.browser.ProfileBrowserView
import javax.inject.Inject

class ProfileBrowserPresenterImpl @Inject constructor(
    private val authenticationInterface: FirebaseAuthenticationInterface,
    private val databaseInterface: FirebaseDatabaseInterface
) : ProfileBrowserPresenter {

    private lateinit var view: ProfileBrowserView
    private lateinit var loadedUser: User

    override fun setView(view: ProfileBrowserView) {
        this.view = view
    }

    override fun onRelationTapped(context: Context, isLiked: Boolean) {

        val relation = UserRelation(authenticationInterface.getUserId(), loadedUser.id, isLiked)

        databaseInterface.storeRelation(
            context,
            relation,
            { this.loadFreshProfile() },
            { this.showRelationError(it) }
        )
    }

    override fun loadFreshProfile() {
        databaseInterface.loadFreshProfile(
            authenticationInterface.getUserId(),
            { profile -> this.sendProfileDataToView(profile) },
            { message -> this.showProfileLoadingError(message) })
    }

    override fun sendProfileDataToView(user: User) {
        this.loadedUser = user
        view.showUsername(user.username)
        //TODO: Set photo.
        view.showPhoto(user.email)
        //TODO: Set date of birth.
        view.showAge(user.id)
    }

    override fun showRelationError(errorMessage: String) {
        view.showRelationError(errorMessage)
    }

    override fun showProfileLoadingError(errorMessage: String) {
        view.showProfileLoadingError(errorMessage)
    }
}