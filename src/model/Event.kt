package com.example.websocket.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class Event(val type: EventType, val value: String) : Transferable {

    override val asJsonString get() = Json.encodeToString(this)

    companion object {
        fun greetingEvent(user: User) = Event(EventType.GREETING, user.asJsonString)
        fun messageEvent(message: Message) = Event(EventType.MESSAGE, message.asJsonString)
        fun newUserEvent(user: User) = Event(EventType.NEW_USER, user.asJsonString)
        fun userDisconnected(user: User) = Event(EventType.USER_DISCONNECTED, user.asJsonString)
    }
}

enum class EventType {
    GREETING,
    MESSAGE,
    NEW_USER,
    USER_DISCONNECTED
}