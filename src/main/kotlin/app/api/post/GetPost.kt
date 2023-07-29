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

fun Route.getPost() {
    get(
        "/api/post",
        {
            tags = listOf("post")
            description = "post"
        }
    ) {
        val result = main.posts.toList()
        call.ok(message = result)
    }
}

fun Route.getPostId() {
    get(
        "/api/post/{id}",
        {
            tags = listOf("post")
            description = "post"
        }
    ) {
        val result = main.posts
            .filter { it.id eq (call.parameters["id"]?.toLong() ?: 0L) }
            .firstOrNull()
        checkNotNull(result) { "조회할 수 없습니다."}
        call.ok(message = result)
    }
}
