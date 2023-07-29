package app.plugins

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.http.*
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import org.ktorm.jackson.KtormModule

import com.fasterxml.jackson.core.util.*
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import java.time.format.DateTimeFormatter
import java.time.ZoneId
import java.time.LocalDateTime
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.io.IOException

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.util.TimeZone
import app.mapper.JacksonLocalDateTimeSerializer
import app.mapper.JacksonLocalDateTimeDeserializer

val sdf: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
fun Application.configureSerialization() {
    install(ContentNegotiation) {
		jackson {
			configure(SerializationFeature.INDENT_OUTPUT, true)
			setDefaultPrettyPrinter(DefaultPrettyPrinter().apply {
                indentArraysWith(DefaultPrettyPrinter.FixedSpaceIndenter.instance)
                indentObjectsWith(DefaultIndenter("  ", "\n"))
            })
			configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, false);
			configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
			
            registerModule(JavaTimeModule().apply {
                
                // addSerializer(Instant::class.java, JacksonInstantSerializer())
                // addDeserializer(Instant::class.java, JacksonInstantDeserializer())

                addSerializer(LocalDateTime::class.java, JacksonLocalDateTimeSerializer())
                addDeserializer(LocalDateTime::class.java, JacksonLocalDateTimeDeserializer())
            })
            setDateFormat(sdf)
            setTimeZone(TimeZone.getTimeZone("Asia/Seoul"))

            registerModule(KtormModule())
			configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

            
        }
    }
}
