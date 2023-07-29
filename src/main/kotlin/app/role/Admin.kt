package app.role

import io.ktor.server.auth.Principal
import io.ktor.server.application.ApplicationCall
import io.ktor.server.auth.authentication
import app.types.*

data class Admin(override var id: Id): Member, Principal

val ApplicationCall.admin get() = authentication.principal<Admin>()