package com.example.lovefinderz.di

import com.example.lovefinderz.firebase.authentication.FirebaseAuthenticationInterface
import com.example.lovefinderz.firebase.authentication.FirebaseAuthenticationManager
import com.example.lovefinderz.firebase.database.FirebaseDatabaseInterface
import com.example.lovefinderz.firebase.database.FirebaseDatabaseManager
import dagger.Binds
import dagger.Module
import javax.inject.Singleton


@Module(includes = [FirebaseModule::class])
@Singleton
abstract class InteractionModule {

    @Binds
    abstract fun authentication(authentication: FirebaseAuthenticationManager): FirebaseAuthenticationInterface

    @Binds
    abstract fun database(database: FirebaseDatabaseManager): FirebaseDatabaseInterface
}