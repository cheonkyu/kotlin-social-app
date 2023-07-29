package app.plugins

import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.plugins.doublereceive.*
import io.ktor.server.request.*
import io.ktor.server.application.*
import app.api.ping.*
import app.api.post.*
import app.api.user.*

fun Application.configureRouting() {
    install(DoubleReceive)
    routing {
        RoutingPing()
        RoutingPost()
        RoutingUser()
    }
}
