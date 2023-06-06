package com.app.api.admin.category

import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*

fun Route.Routing() {
    findAllCategory()
    createCategory()
    updateCategory()
    deleteCategory()
}

fun Route.findAllCategory() {
    get("/admin/category") {
        TODO("TODO")
    }
}

fun Route.createCategory() {
    post("/admin/category") {
        TODO("TODO")
    }
}

fun Route.updateCategory() {
    put("/admin/category") {
        TODO("TODO")
    }
}

fun Route.deleteCategory() {
    delete("/admin/category") {
        TODO("TODO")
    }
}
