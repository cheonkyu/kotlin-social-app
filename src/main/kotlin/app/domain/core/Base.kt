package app.domain.core

import org.ktorm.entity.Entity
import org.ktorm.schema.Table
import org.ktorm.entity.*
import org.ktorm.dsl.*
import org.ktorm.expression.*
import org.ktorm.schema.*

import java.time.LocalDateTime
import app.types.*

interface BaseEntity<E : Entity<E>> : Entity<E> {
	var id: Id
	var createdBy: Long
	var updatedBy: Long
    var createdAt: CreatedAt?
    var updatedAt: UpdatedAt?
    var deleted: Deleted
}

abstract class BaseTable<E : BaseEntity<E>>(tableName: String, alias: String? = tableName) : Table<E>(tableName, alias) {
	val id = long("id").primaryKey().bindTo { it.id }
	val createdBy = long("createdBy").bindTo { it.createdBy }
    val updatedBy = long("updatedBy").bindTo { it.updatedBy }
    val createdAt = datetime("createdAt").bindTo { it.createdAt }
    val updatedAt = datetime("updatedAt").bindTo { it.updatedAt }
    val deleted = boolean("deleted").bindTo { it.deleted }
}
