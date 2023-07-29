package app.api.study

import io.ktor.http.HttpStatusCode
import io.github.smiley4.ktorswaggerui.dsl.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import org.ktorm.entity.*

fun Route.RoutingPing() {
    getPing()
}