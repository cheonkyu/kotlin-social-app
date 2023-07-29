package app.api.post

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

import app.database.main
import app.api.core.ok
import app.domain.entity.*
import org.ktorm.dsl.*
import org.ktorm.entity.*
import org.ktorm.schema.*

fun Route.deletePost() {
    delete(
        "/api/post/{id}",
        {
            tags = listOf("post")
            description = "post"
        }
    ) {
        call.ok(message = "main")
    }
}
