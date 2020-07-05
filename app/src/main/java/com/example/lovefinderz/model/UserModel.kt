package com.example.lovefinderz.model

data class User(
    val id: String,
    val username: String,
    val email: String,
    internal val favoriteUsers: List<User>? = listOf(),
    val photo: String? = ""
)
