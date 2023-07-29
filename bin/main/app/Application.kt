package app

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import app.plugins.*
import app.database.main

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    configureSerialization()
    configureHTTP()
    configureSwagger()
	configureAuthentication()
    configureRequestIneterceptor()
    configureRouting()
}
