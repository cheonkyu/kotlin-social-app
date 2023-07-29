package app.mapper

import io.ktor.http.HttpStatusCode
import io.github.smiley4.ktorswaggerui.dsl.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*

// import studymoa.domain.study.*
// import studymoa.role.*
// import studymoa.database.main
// import studymoa.api.core.*
// import studymoa.api.core.response.*
// import studymoa.mapper.*
// import io.ktor.server.auth.authenticate

// import studymoa.types.*
// import studymoa.api.core.*

// import studymoa.domain.core.BaseEntity

// import kotlinx.serialization.json.*
// import studymoa.domain.core.entity.*

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import org.ktorm.jackson.KtormModule
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.databind.module.SimpleModule

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.readValue
import java.text.SimpleDateFormat
import java.util.TimeZone
import app.api.core.*

val sdf: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
// !!.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"))
val mapper = ObjectMapper()
    .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
    .configure(SerializationFeature.INDENT_OUTPUT, true)
    .configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, false)
    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    .registerModule(
        KotlinModule.Builder()
        .enable(KotlinFeature.NullIsSameAsDefault)
        .build()
    )
    .registerModule(KtormModule())
    .registerModule(SimpleModule())
    .registerModule(JavaTimeModule().apply {
        addSerializer(LocalDateTime::class.java, JacksonLocalDateTimeSerializer())
        addDeserializer(LocalDateTime::class.java, JacksonLocalDateTimeDeserializer())
    })
    .setDateFormat(sdf)
    .setTimeZone(TimeZone.getTimeZone("Asia/Seoul"))

inline fun <reified K, reified V> mapping(input :K): V {
	val json = mapper.writeValueAsString(input)
	return mapper.readValue<V>(json)
}