package com.example.lovefinderz.common

import java.security.InvalidParameterException


/**
 * Returns true if thisUserId is before otherUserId in order and false if otherUserId is before thisUserId
 */
fun orderOnUserIds(thisUserId:String, otherUserId: String): Boolean {
    var i = 0
    while (true) {
        if (thisUserId.length <= i && otherUserId.length <= i) throw InvalidParameterException("Ids can not be equal!")
        if (thisUserId.length <= i) return true
        if (otherUserId.length <= i) return false
        if (thisUserId[i].toInt() < otherUserId[i].toInt()) return true
        if (thisUserId[i].toInt() > otherUserId[i].toInt()) return false
        i++
    }
}

fun generateId(thisUserId: String, otherUserId: String): String {
    return if (orderOnUserIds(thisUserId, otherUserId)) thisUserId + otherUserId
    else otherUserId + thisUserId
}
