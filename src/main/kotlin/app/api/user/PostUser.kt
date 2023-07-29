package app.api.user

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
import java.time.LocalDateTime
import com.auth0.jwt.*
import com.auth0.jwt.algorithms.*
import io.ktor.server.auth.jwt.*

fun Route.postUserLogin() {
    post(
        "/api/user/login",
        {
            tags = listOf("user")
            description = "user"
        }
    ) {
        val data = call.receive<UserData>()
        val result = main.users
            .filter { it.userId eq data.userId }
            .filter { it.password eq data.password }
            .firstOrNull()
        checkNotNull(result) { "로그인 정보를 확인해주세요." }

        val token = JWT.create()
            // .withAudience(audience)
            // .withIssuer(issuer)
            .withClaim("userId", result.userId)
            // .withExpiresAt(Date(System.currentTimeMillis() + 60000))
            .sign(Algorithm.HMAC256("TOKEN1234!@#$"))
        
        call.ok(message = mapOf("token" to token))
    }
}

fun Route.postUser() {
    post(
        "/api/user",
        {
            tags = listOf("user")
            description = "user"
        }
    ) {
        val data = call.receive<UserData>()
        val result = User {
            userId = data.userId
            password = data.password
            createdBy = 1
            updatedBy = 1
            createdAt = LocalDateTime.now()
            updatedAt = LocalDateTime.now()
            main.users.add(this)
        }
        call.ok(message = result)
    }
}
