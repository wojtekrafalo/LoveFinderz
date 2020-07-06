package com.example.lovefinderz.model

import java.util.*

data class User(
    val id: String ="",
    val username: String = "",
    val email: String = "",
    val dateOfBirth: UserDateOfBirth? = UserDateOfBirth(1800, 1, 1),
    val photo: String? = "",
    internal val relatedUsers: List<String>? = listOf()
)

data class UserDateOfBirth(
    val year: Int = -1,
    val month: Int = -1,
    val day: Int = -1
)