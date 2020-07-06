package com.example.lovefinderz.model

data class ProtocolData(
    var initializerId: String = "",
    var users: List<String> = listOf("", ""),
    var gc: List<String> = listOf("", "", "", ""),
    var g: Int? = null,
    var n: Int? = null,
    var x: Int? = null,
    var firstUserChoiceKey: String = "",
    var y: Int? = null,
    var encryptedSecondUserChoiceKey0: String = "",
    var encryptedSecondUserChoiceKey1: String = ""
)