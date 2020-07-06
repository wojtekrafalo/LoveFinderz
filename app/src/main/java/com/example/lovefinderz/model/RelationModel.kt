package com.example.lovefinderz.model


data class UserRelation(
    var thisUserId: String = "",
    var otherUserId: String = "",
    var isLiked: Boolean = false
)

data class UserRelationEntry(
    var users: List<String> = listOf()
)
