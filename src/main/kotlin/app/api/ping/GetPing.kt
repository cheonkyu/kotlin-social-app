package app.api.ping

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
import app.database.execSqlScript

fun Route.getPing() {
    get(
        "/api/ping",
        {
            tags = listOf("ping")
            description = "ping"
        }
    ) {
        // execSqlScript("init-table.h2.sql")

        call.ok(message = "main")
    }
}
