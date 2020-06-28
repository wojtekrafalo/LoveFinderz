package com.example.lovefinderz

import com.google.firebase.FirebaseApp
import android.app.Application
import com.example.lovefinderz.di.AppComponent
import com.example.lovefinderz.di.DaggerAppComponent

//import com.raywenderlich.android.whysoserious.di.DaggerAppComponent

class App : Application() {

  companion object {
    lateinit var instance: App
      private set

    val component: AppComponent by lazy { DaggerAppComponent.builder().build() }
  }

  override fun onCreate() {
    super.onCreate()
    instance = this
  }
}