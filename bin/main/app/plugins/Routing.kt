package app.plugins

import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.plugins.doublereceive.*
import io.ktor.server.request.*
import io.ktor.server.application.*
import app.api.study.*
import app.api.location.*
import app.api.tagGroup.*
import app.api.file.*
import app.api.study.*
import app.api.main.*
import app.api.bookmark.*
import app.api.user.*
import app.api.history.*

import app.api.moim.main.*

fun Application.configureRouting() {
    install(DoubleReceive)
    routing {
        // 관리자
        RoutingTagGroup()
        RoutingLocation()
        RoutingStudy()
        RoutingMain()
        RoutingUser()
        RoutingHistory()

		// MoimStudyRouting()
		// HashTagsRouting()
        
        // 일반회원
        MoimMainRouting()
        RoutingFile()
        RoutingBookmark()

        // 시스템
        RoutingPing()
    }
}
