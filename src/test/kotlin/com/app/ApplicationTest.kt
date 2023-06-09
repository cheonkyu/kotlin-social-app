package com.app

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.server.testing.*
import kotlin.test.*
import io.ktor.http.*
import com.app.plugins.*

class ApplicationTest {
    @Test
    fun testRoot() = testApplication {
        application {
            configureRouting()
        }
        client.get("/admin/category").apply {
            assertEquals(HttpStatusCode.OK, status)
            // assertEquals("Hello World!", bodyAsText())
        }
    }
}
