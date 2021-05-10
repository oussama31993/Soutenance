package com.example.soutenance.Model

import com.example.soutenance.Message
import com.example.soutenance.MessageType
import java.util.*


data class ImageMessage(val imagePath: String,
                        override val time: Date,
                        override val senderId: String,
                        override val recipientId: String,
                        override val senderName: String,
                        override val type: String = MessageType.IMAGE)
    : Message {
    constructor() : this("", Date(0), "", "", "")
}