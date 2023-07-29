package app.plugins

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.application.*

import io.github.smiley4.ktorswaggerui.SwaggerUI
import io.github.smiley4.ktorswaggerui.dsl.*
// import kotlinx.serialization.jackson
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import io.ktor.serialization.kotlinx.json.*

import app.mapper.*

fun Application.configureSwagger() {
    // val json = Json {
    //     prettyPrint = true
    //     isLenient = true
    //     explicitNulls = false
    //     ignoreUnknownKeys = true
    //     coerceInputValues = false
    //     encodeDefaults = true
    // }
    install(SwaggerUI) {
        swagger {
            swaggerUrl = "swagger-ui"
            forwardRoot = true
        }
        info {
            title = "Example API"
            version = "latest"
            description = "Example API for testing and demonstration purposes."
        }
        server {
            url = "http://stg.app.me:8080"
            description = "Example server"
        }
        server {
            url = "http://localhost:8080"
            description = "Development Server"
        }
        securityScheme("auth-user") {
            type = AuthType.HTTP
            scheme = AuthScheme.BEARER
            bearerFormat = "jwt"
        }
        securityScheme("auth-admin") {
            type = AuthType.HTTP
            scheme = AuthScheme.BEARER
            bearerFormat = "jwt"
        }
        tag("moim/main") {
            description = "회원 - 모임 app"
        }
        tag("moim/study") {
            description = "회원 - 모임"
        }
        tag("moim/study/group") {
            description = "회원 - 모임 스터디 그룹 참여/미참여"
        }
        tag("main") {
            description = "관리자 - 사용자 app 화면 관리"
        }
        tag("study") {
            description = "관리자 - 스터디"
        }
        tag("tagGroup") {
            description = "관리자 - 태그그룹"
        }
        tag("hashtag") {
            description = "관리자 - 해시테그"
        }
        tag("location") {
            description = "관리자 - 위치"
        }
        tag("studyGroup") {
            description = "관리자 - 스터디 그룹 참여/미참여"
        }
        tag("bookmark") {
            description = "TODO 북마크"
        }
		tag("file") {
            description = "파일 첨부"
        }
        tag("history") {
            description = "히스토리"
        }
        generateTags { url -> listOf(url.firstOrNull()) }
        encoding {
            exampleEncoder { _, value ->
                // json.encodeToString(serializer(type!!), value)
                mapper.writeValueAsString(value)
            }
        }
    }
}
