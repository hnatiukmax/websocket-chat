package com.example.websocket.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class Message(val sender: String, val value: String) : Transferable {
    override val asJsonString get() = Json.encodeToString(this)
}

val String.asMessage: Message
    get() = Json.decodeFromString(this)