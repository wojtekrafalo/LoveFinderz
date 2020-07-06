package com.example.lovefinderz.model

data class ProtocolData(
    var initializatorId: String = "",
    var users: List<String> = listOf("", ""),
    var gc: List<String> = listOf("", "", "", ""),
    var g: Int? = null,
    var n: Int? = null,
    var x: Int? = null,
    var firstUserChoiceKey: String = "",
    var y: Int? = null,
    var secondUserChoiceKey1: String = "",
    var secondUserChoiceKey2: String = ""
)