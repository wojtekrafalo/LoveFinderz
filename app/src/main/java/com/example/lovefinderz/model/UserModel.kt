package com.example.lovefinderz.model


data class UserResponse(
    val id: String = "",
    val username: String = "",
    val email: String = ""
)

data class User(
    val id: String,
    val username: String,
    val email: String,
    val favoriteJokes: List<Joke>? = listOf()
//    ,
//    val photo: String?,
//    val likedProfiles: List<User>? = listOf()
)
//{
//    constructor(id: String, username: String, email: String) : this(id, username, email, null, null, null)
//    constructor(id: String, username: String, email: String, favoriteJokes: List<Joke>) : this(id, username, email, favoriteJokes, null, null)
//}
