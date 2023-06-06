package com.app.plugins

import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*

import com.app.api.admin.category.Routing as adminCategoryRouting

fun Application.configureRouting() {
    // routing {
    //     get("/") {
    //         call.respondText("Hello World!")
    //     }
    // }
    routing {
        adminCategoryRouting()
    }
}