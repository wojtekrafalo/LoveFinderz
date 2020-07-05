package com.example.lovefinderz.model

import com.example.lovefinderz.common.*


data class UserRelation(
    var idLiking: String = "",
    var idLiked: String = "",
    var isLiked: Boolean = false
)