package app.api.study

import io.ktor.http.HttpStatusCode
import io.github.smiley4.ktorswaggerui.dsl.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*

import app.api.core.*
import app.api.core.response.*

import app.api.core.ok

fun Route.getPing() {
    get(
        "/api/ping",
        {
            tags = listOf("ping")
            description = "ping"
        }
    ) {
        call.ok(message = "main")
    }
}
