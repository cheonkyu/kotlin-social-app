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

interface Post : BaseEntity<Post> {
    companion object : Entity.Factory<Post>()

    var title: Title
    var content: Content
}

object Posts : BaseTable<Post>("Post") {
    val title = varchar("title").bindTo { it.title }
    val content = varchar("content").bindTo { it.content }
}

val Database.posts get() = this.sequenceOf(Posts)
