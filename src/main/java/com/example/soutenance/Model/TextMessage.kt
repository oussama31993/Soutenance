package com.example.soutenance.Model

import com.example.soutenance.Message
import com.example.soutenance.MessageType
import java.util.*


data class TextMessage(val text: String,
                       override val time: Date,
                       override val senderId: String,
                       override val recipientId: String,
                       override val senderName: String,
                       override val type: String = MessageType.TEXT)
    : Message {
    constructor() : this("", Date(0), "", "", "")
}