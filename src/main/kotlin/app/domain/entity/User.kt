package app.domain.entity

import app.domain.core.BaseEntity
import app.domain.core.BaseTable
import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Table
import org.ktorm.schema.boolean
import org.ktorm.schema.datetime
import org.ktorm.schema.varchar
import java.time.LocalDateTime
import app.types.*

interface User : BaseEntity<User> {
    companion object : Entity.Factory<User>()

    var userId: UserId
    var password: Password
}

object Users : BaseTable<User>("User") {
    val userId = varchar("userId").bindTo { it.userId }
    val password = varchar("password").bindTo { it.password }
}

val Database.users get() = this.sequenceOf(Users)
