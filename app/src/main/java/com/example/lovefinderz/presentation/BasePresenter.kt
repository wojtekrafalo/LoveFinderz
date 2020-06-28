package com.example.lovefinderz.presentation

interface BasePresenter<in T> {

  fun setView(view: T)
}