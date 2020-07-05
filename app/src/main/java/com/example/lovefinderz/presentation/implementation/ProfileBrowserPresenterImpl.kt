package com.example.lovefinderz.presentation.implementation

import com.example.lovefinderz.firebase.authentication.FirebaseAuthenticationInterface
import com.example.lovefinderz.firebase.database.FirebaseDatabaseInterface
import com.example.lovefinderz.model.User
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

    //TODO: add liking and not-liking, encrypting, loading new profile to the Profile Browser.
    override fun onRateTapped(isLiked: Boolean) {
        databaseInterface.addRatedProfile(authenticationInterface.getUserId(),
            loadedUser.id,
            isLiked,
            { this.loadFreshProfile() },
            { this.showProfileRatingError() })
    }


    //  override fun loadFreshProfile(onResult: (UserResponse) -> Unit) {
    //TODO: Add onFailure event. The same as in onRateTapped()
    override fun loadFreshProfile() {
        databaseInterface.getFreshProfile(
            authenticationInterface.getUserId(),
            { profile -> this.sendProfileDataToView(profile) },
            { this.showProfileLoadingError() })
    }

    override fun sendProfileDataToView(user: User) {
        this.loadedUser = user
        view.showUsername(user.username)
        //TODO: Set photo.
        view.showPhoto(user.email)
        //TODO: Set date of birth.
        view.showAge(user.id)
    }

    override fun showProfileRatingError() {
        view.showProfileRatingError()
    }

    override fun showProfileLoadingError() {
        view.showProfileLoadingError()
    }
}