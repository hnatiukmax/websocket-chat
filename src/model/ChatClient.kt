package com.example.websocket.model

import io.ktor.http.cio.websocket.*
import java.util.concurrent.atomic.AtomicInteger
import java.util.logging.Logger

class ChatClient(val session: DefaultWebSocketSession) {

    private val id = lastId.getAndIncrement()
    val user = User(name = "User.$id")

    companion object {
        var lastId = AtomicInteger(0)
    }
}

suspend fun Collection<ChatClient>.sendToAll(frame: Frame) {
    for (other in toList()) {
        other.session.outgoing.send(frame.copy())
    }
}