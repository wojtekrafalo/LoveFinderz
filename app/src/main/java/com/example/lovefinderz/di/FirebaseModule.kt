package com.example.lovefinderz.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
@Singleton
class FirebaseModule {

    @Provides
    fun firebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun firebaseDatabase(): FirebaseDatabase = FirebaseDatabase.getInstance()
}
