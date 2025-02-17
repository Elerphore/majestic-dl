package ru.elerphore.plugins

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        post("/") {
            call.respondText("Hello World!")
        }
    }
}