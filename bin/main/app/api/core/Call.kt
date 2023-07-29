package app.api.core

import io.ktor.server.application.*
import io.ktor.server.auth.authenticate
import io.ktor.server.response.respond
import io.ktor.http.HttpStatusCode
import app.api.core.response.Response
import app.api.core.response.ResponsePagination
import app.api.core.response.ResponseStatus

inline suspend fun <T: Any>ApplicationCall.ok(message : T, status: HttpStatusCode = HttpStatusCode.OK) {
    this.respond(
        message = Response(
            data = message,
            status = ResponseStatus(code = "0000", message = null),
        ),
        status = status
    )
}

inline fun <reified T : Any> Any.getReflection(propertyName: String): T? {
    val getterName = "get" + propertyName.capitalize()
    return try {
        javaClass.getMethod(getterName).invoke(this) as? T
    } catch (e: NoSuchMethodException) {
        null
    }
}

inline suspend fun <T: Any>ApplicationCall.okPagination(message : T, status: HttpStatusCode = HttpStatusCode.OK) {
    val page = message.getReflection("page") ?: 0
    val pageSize = message.getReflection("pageSize") ?: 0
    val total = message.getReflection("total") ?: 0
    val data : Any = message.getReflection("data") ?: emptyList<Any>()

    this.respond(
        message = ResponsePagination(
            page = page,
            pageSize = pageSize,
            total = total,
            data = data,
            status = ResponseStatus(code = "0000", message = null),
        ),
        status = status
    )
}

inline suspend fun ApplicationCall.failApi(message : String, status: HttpStatusCode = HttpStatusCode.OK) {
    this.respond(
        status = status,
        message = Response(
            data = null,
            status = ResponseStatus(code = "1000", message = message),
        )
    )
}

inline suspend fun ApplicationCall.failDomain(message : String, status: HttpStatusCode = HttpStatusCode.OK) {
    this.respond(
        status = status,
        message = Response(
            data = null,
            status = ResponseStatus(code = "2000", message = message),
        )
    )
}

inline suspend fun ApplicationCall.failServer(message : String) {
    this.respond(
        message = Response(
            data = null,
            status = ResponseStatus(code = "9999", message = message),
        )
    )
}