package com.example.websocket.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class User(val name: String) : Transferable {
    override val asJsonString get() = Json.encodeToString(this)
}

val String.asUser: String
    get() = Json.decodeFromString(this)