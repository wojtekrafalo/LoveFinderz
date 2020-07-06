package com.example.lovefinderz.common


fun parseListOfUsers(list: List<Any?>):List<String> {
    val resultList: MutableList<String> = MutableList(list.size) {
        list[it].toString()
    }
    return resultList.toList()
}
