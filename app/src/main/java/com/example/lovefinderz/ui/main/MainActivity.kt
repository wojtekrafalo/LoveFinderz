package com.example.lovefinderz.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.lovefinderz.*
import com.example.lovefinderz.ui.matched.MatchedProfileFragment
import com.example.lovefinderz.ui.browser.ProfileBrowserFragment
import com.example.lovefinderz.ui.profile.ProfileFragment
import com.example.lovefinderz.ui.welcome.WelcomeFragment


class MainActivity : AppCompatActivity(), MainView {

    private val presenter by lazy { mainViewPresenter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        presenter.setView(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.action_browse -> {
                presenter.onBrowseTapped()
                Toast.makeText(applicationContext,"Pressed 'Browse'",Toast.LENGTH_LONG)
                true
            }
            R.id.action_home -> {
                presenter.onHomeTapped()
                Toast.makeText(applicationContext,"Pressed 'Home'",Toast.LENGTH_LONG)
                true
            }
            R.id.action_show_matches -> {
                presenter.onMatchesTapped()
                Toast.makeText(applicationContext,"Pressed 'Show matches'",Toast.LENGTH_LONG)
                true
            }
            //TODO: Repair it. Crashes the app.
            R.id.action_log_out -> {
                presenter.onLogOutTapped()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    @SuppressLint("ShowToast")
    override fun onLogOutSuccess() {
        val message = getString(R.string.log_out_success)
        Toast.makeText(applicationContext,message,Toast.LENGTH_LONG)

        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.nav_host_fragment,
                WelcomeFragment()
            )
            .commit()
    }

    override fun onHomeSuccess() {
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.nav_host_fragment,
                ProfileFragment()
            )
            .commit()
    }

    override fun onBrowseSuccess() {
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.nav_host_fragment,
                ProfileBrowserFragment()
            )
            .commit()
    }

    override fun onMatchesTapped() {
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.nav_host_fragment,
                MatchedProfileFragment()
            )
            .commit()
    }
}
