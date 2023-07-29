package app.role

import io.ktor.server.auth.Principal
import io.ktor.server.application.ApplicationCall
import io.ktor.server.auth.authentication
import app.types.*

data class User(override var seq: Seq): Member, Principal

val ApplicationCall.user get() = authentication.principal<User>()
val ApplicationCall.isUser get() = this.user != null
