package com.app

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.app.plugins.*

// fun main() {
//     embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
//         .start(wait = true)
// }
fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    configureHTTP()
    // configureSecurity()
    configureSerialization()
    configureRouting()
}
