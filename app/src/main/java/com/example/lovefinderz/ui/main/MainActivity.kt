package com.example.lovefinderz.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
//import android.support.v7.app.AppCompatActivity
import androidx.appcompat.app.AppCompatActivity
import com.example.lovefinderz.R
import com.example.lovefinderz.ui.jokes.all.AllJokesFragment
import com.example.lovefinderz.ui.jokes.favorite.FavoriteJokesFragment
import com.example.lovefinderz.ui.main.pager.MainPagerAdapter
import com.example.lovefinderz.ui.profile.ProfileFragment

class MainActivity : AppCompatActivity() {

  companion object {
    fun getLaunchIntent(from: Context) = Intent(from, MainActivity::class.java).apply {
      addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    initUi()
  }

  private fun initUi() {
    val adapter = MainPagerAdapter(supportFragmentManager)
    adapter.setPages(listOf(AllJokesFragment(), FavoriteJokesFragment(), ProfileFragment()))

//    mainPager.adapter = adapter
//    mainPager.offscreenPageLimit = 3
//    bottomNavigation.setOnNavigationItemSelectedListener {
//      switchNavigationTab(it.order)
//      true
//    }

//    mainPager.onPageChange { position ->
//      val item = bottomNavigation.menu.getItem(position)
//
//      bottomNavigation.selectedItemId = item.itemId
//    }

//    addJoke.onClick { startActivity(Intent(this, AddJokeActivity::class.java)) }
  }

//  private fun switchNavigationTab(position: Int) = mainPager.setCurrentItem(position, true)
}