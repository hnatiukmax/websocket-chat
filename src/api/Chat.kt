package com.example.websocket.api

import com.example.websocket.kit.asTextFrame
import com.example.websocket.model.ChatClient
import com.example.websocket.model.Event.Companion.greetingEvent
import com.example.websocket.model.Event.Companion.messageEvent
import com.example.websocket.model.Event.Companion.newUserEvent
import com.example.websocket.model.Event.Companion.userDisconnected
import com.example.websocket.model.Message
import com.example.websocket.model.sendToAll
import io.ktor.http.cio.websocket.*
import io.ktor.routing.*
import io.ktor.websocket.*
import java.util.*
import java.util.logging.Logger
import kotlin.collections.LinkedHashSet

fun Route.chat() {
    val clients = Collections.synchronizedSet(LinkedHashSet<ChatClient>())

    webSocket("/chat") {
        val client = ChatClient(this).also {
            it.session.outgoing.send(greetingEvent(it.user).asJsonString.asTextFrame)
            clients.sendToAll(newUserEvent(it.user).asJsonString.asTextFrame)
        }
        clients += client
        try {
            while (true) {
                when (val frame = incoming.receive()) {
                    is Frame.Close -> {
                        Logger.getGlobal().info("${client.user.name} disconnected")
                    }
                    is Frame.Text -> {
                        val message = Message(client.user.name, frame.readText())
                        clients.sendToAll(messageEvent(message).asJsonString.asTextFrame)
                    }
                }
            }
        } finally {
            clients -= client
            val disconnectedUser = client.user
            clients.sendToAll(userDisconnected(disconnectedUser).asJsonString.asTextFrame)
        }
    }
}