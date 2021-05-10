package com.example.soutenance.Model


data class User(val nom: String,
                val speciality: String,
                val profilePicturePath: String?,
                val registrationTokens: MutableList<String>) {
    constructor(): this("", "", null, mutableListOf())
}