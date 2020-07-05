package com.example.lovefinderz.model

import java.util.*

data class User(
    val id: String,
    val username: String,
    val email: String,
    val dateOfBirth: GregorianCalendar? = GregorianCalendar(1800, 1, 1),
    val photo: String? = "",
    internal val relatedUsers: List<String>? = listOf()
)
