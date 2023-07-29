package app.plugins

import com.auth0.jwt.*
import com.auth0.jwt.algorithms.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.*
import io.ktor.server.application.*
import java.util.*
import app.role.Admin
import app.role.User
import app.api.core.failApi

fun Application.configureAuthentication() {
	val secret = environment.config.property("jwt.secret").getString()
    // val issuer = environment.config.property("jwt.issuer").getString()
    // val audience = environment.config.property("jwt.audience").getString()
    val myRealm = environment.config.property("jwt.realm").getString()
    install(Authentication) {
        jwt("auth-user") {
            realm = myRealm
            verifier(JWT
                .require(Algorithm.HMAC256(secret))
                // .withAudience(audience)
                // .withIssuer(issuer)
                .build())
            validate { credential ->
                if (credential.payload.getClaim("userSeq").asString() != "") {
                    // JWTPrincipal(credential.payload)
                    User(credential.payload.getClaim("userSeq").asLong())
                } else {
                    null
                }
            }
            challenge { _, _ ->
                call.failApi(
                    status = HttpStatusCode.Unauthorized,
                    message = "Token is not valid or has expired"
                )
                // call.respond(HttpStatusCode.Unauthorized, )
            }
        }
        jwt("auth-admin") {
            realm = myRealm
            verifier(JWT
                .require(Algorithm.HMAC256(secret))
                // .withAudience(audience)
                // .withIssuer(issuer)
                .build())
            validate { credential ->
                if (credential.payload.getClaim("seq").asString() != "" && credential.payload.getClaim("permission").asString() != "") {
                    // JWTPrincipal(credential.payload)
                    Admin(credential.payload.getClaim("seq").asLong())
                } else {
                    null
                }
            }
            challenge { _, _ ->
                call.failApi(
                    status = HttpStatusCode.Unauthorized,
                    message = "Token is not valid or has expired"
                )
                // call.respond(HttpStatusCode.Unauthorized, "Token is not valid or has expired")
            }
        }
    }
}