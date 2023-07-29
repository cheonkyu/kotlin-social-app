package app.role

import io.ktor.server.auth.Principal
import io.ktor.server.application.ApplicationCall
import io.ktor.server.auth.authentication
import app.types.*

data class PublicUser(override var seq: Seq = -1): Member

val ApplicationCall.publicUser get() = PublicUser()