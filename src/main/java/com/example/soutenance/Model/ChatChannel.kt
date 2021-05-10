package com.example.soutenance.Model


data class ChatChannel(val userIds: MutableList<String>) {
    constructor() : this(mutableListOf())
}