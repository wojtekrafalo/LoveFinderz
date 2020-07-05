package com.example.lovefinderz

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.lovefinderz.ui.main.MainView


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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return when (item.itemId) {
            R.id.action_browse -> {
                presenter.onBrowseTapped()
                Snackbar.make(window.decorView, "Pressed 'Browse'", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
                true
            }
            R.id.action_home -> {
                presenter.onHomeTapped()
//                Snackbar.make(window.decorView, "Pressed 'Home'", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
                true
            }
            R.id.action_show_matches -> {
                presenter.onMatchesTapped()
                Snackbar.make(window.decorView, "Pressed 'Show matches'", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
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

    override fun onLogOutSuccess() {
        val message = getString(R.string.log_out_success)
        Snackbar.make(window.decorView, message, Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.nav_host_fragment, WelcomeFragment())
            .commit()
    }

    override fun onHomeSuccess() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.nav_host_fragment, ProfileFragment())
            .commit()
    }

    override fun onBrowseSuccess() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.nav_host_fragment, ProfileBrowserFragment())
            .commit()
    }

    override fun onMatchesTapped() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.nav_host_fragment, MatchedProfileFragment())
            .commit()
    }
}
