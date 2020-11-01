package com.example.websocket.kit

import io.ktor.http.cio.websocket.*

val String.asTextFrame get() = Frame.Text(this)