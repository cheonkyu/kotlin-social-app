package app.mapper

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import java.time.format.DateTimeFormatter
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import java.io.IOException

import java.text.SimpleDateFormat

val dateformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneOffset.of("+09:00"));

class JacksonLocalDateTimeSerializer : StdSerializer<LocalDateTime>(LocalDateTime::class.java) {
    companion object {
        private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern(
			"[yyyy-MM-dd HH:mm:ss]"
			// + DateTimeFormatter.ISO_INSTANT
		)
    }

    @Throws(IOException::class, JsonProcessingException::class)
    override fun serialize(value: LocalDateTime?, gen: JsonGenerator?, provider: SerializerProvider?) {
        if(value == null) {
            gen?.writeString("")
        } else {
            gen?.writeString(formatter.format(value))
        }
    }
}

class JacksonLocalDateTimeDeserializer : StdDeserializer<LocalDateTime>(LocalDateTime::class.java) {
    companion object {
        private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    }
    
    @Throws(IOException::class, JsonProcessingException::class)
    override fun deserialize(p: JsonParser?, ctxt: DeserializationContext?): LocalDateTime {
        val value = p?.text
        // return Instant.parse(value)

        val result: LocalDateTime = LocalDateTime.parse(value, formatter)
        return result
        // return Instant.from(formatter.parse(value))
    }
}
