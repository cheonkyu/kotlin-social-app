package app.plugins

import io.ktor.serialization.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.response.respond
import io.ktor.util.*
// import org.ktorm.database.useTransaction
import app.api.core.response.Response
import app.database.main
import app.api.core.*
import kotlinx.serialization.MissingFieldException
import io.ktor.server.plugins.BadRequestException
import java.sql.SQLException

import com.fasterxml.jackson.databind.JsonMappingException
@OptIn(kotlinx.serialization.ExperimentalSerializationApi::class)
class RequestIneterceptor {
    companion object Plugin : BaseApplicationPlugin<ApplicationCallPipeline, Configuration, RequestIneterceptor> {
        override val key = AttributeKey<RequestIneterceptor>("RequestIneterceptor")
        override fun install(pipeline: ApplicationCallPipeline, configure: Configuration.() -> Unit): RequestIneterceptor {
            val plugin = RequestIneterceptor()
            pipeline.intercept(ApplicationCallPipeline.Monitoring) {
                try {
                    main.useTransaction { // 트랜젝션, 에러나면 롤백
                        proceed()
                    }
                } catch (err: Throwable) {
                    if(err is IllegalStateException) {
                        call.failDomain(message = err.message!!)
                    }
                    else if(err is IllegalArgumentException) {
                        call.failDomain(message = err.message!!)
                    }
                    else if(err is BadRequestException) {
                        var e0 = err.cause
                        var message = ""
                        for(i in 0..5) {
                            if(e0 != null) {
                                if(e0 is IllegalArgumentException) {
                                    println(">>>> IllegalArgumentException : ${e0.message}")
                                    message = e0.message!!
                                    break
                                }
                                else if(e0 is IllegalStateException) {
                                    println(">>>> IllegalStateException : ${e0.message}")
                                    message = e0.message!!
                                    break
                                }
                                else if(e0 is MissingFieldException || e0 is JsonMappingException) {
                                    message = e0.message!!
                                    e0 = e0.cause
                                } else {
                                    e0 = e0.cause
                                }
                            }
                        }
                        println(">>>> message : $message")
                        if(message.isNotEmpty()) {
                            call.failApi(message = message)
                        }
                        throw err
                    }
                    else if(err is SQLException) {
                        println(">>>> SQLException>>>> $err")
                        call.failServer(message = "이용에 불편을 드려서 죄송합니다. 스터디모아로 문의주시길 바랍니다.")
                    } else {
                        println(">>>> Exception >>>> $err")
                        call.failServer(message = "이용에 불편을 드려서 죄송합니다. 스터디모아로 문의주시길 바랍니다.")
                        throw err
                    }
                }
            }
            return plugin
        }
    }
}

fun Application.configureRequestIneterceptor() {
    install(RequestIneterceptor)
}