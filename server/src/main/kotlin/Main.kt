package ru.elerphore

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.Netty
import ru.elerphore.plugins.configureRouting

fun main() {
    val config =
        serverConfig {
            module { module() }
        }

    embeddedServer(Netty, config) {
        connector {
            host = "0.0.0.0"
            port = 8080
        }
    }.start(wait = true)
}

fun Application.module() {
    configureRouting()
}