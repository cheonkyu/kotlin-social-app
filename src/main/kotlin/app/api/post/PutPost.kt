package app.api.post

import io.ktor.http.HttpStatusCode
import io.github.smiley4.ktorswaggerui.dsl.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import org.ktorm.dsl.*
import org.ktorm.entity.*
import org.ktorm.schema.*
import java.time.LocalDateTime

import app.api.core.*
import app.api.core.response.*

import app.database.main
import app.api.core.ok
import app.domain.entity.*

fun Route.putPost() {
    put(
        "/api/post/{id}",
        {
            tags = listOf("post")
            description = "post"
        }
    ) {
        val data = call.receive<PostData>()
        val result = Post {
            id = call.parameters["id"]?.toLong() ?: 0L
            title = data.title
            content = data.content
            updatedBy = 1
            updatedAt = LocalDateTime.now()
            main.posts.update(this)
        }
        call.ok(message = result)
    }
}
