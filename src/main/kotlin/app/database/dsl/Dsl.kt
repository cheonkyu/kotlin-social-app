package app.database.dsl

import org.ktorm.entity.Entity
import org.ktorm.schema.Table
import org.ktorm.schema.BaseTable as BaseTable1
import org.ktorm.entity.*
import org.ktorm.entity.EntitySequence
import org.ktorm.dsl.eq
import org.ktorm.dsl.less
import org.ktorm.dsl.lessEq
import org.ktorm.dsl.greater
import org.ktorm.dsl.greaterEq
import org.ktorm.dsl.where
import org.ktorm.dsl.Query
import org.ktorm.dsl.QueryRowSet
import org.ktorm.dsl.limit
import org.ktorm.dsl.map
import org.ktorm.schema.boolean
import org.ktorm.schema.datetime
import org.ktorm.schema.datetime
import org.ktorm.schema.long
import org.ktorm.schema.Column
import org.ktorm.schema.ColumnDeclaring
import org.ktorm.expression.OrderByExpression
import org.ktorm.expression.*
import org.ktorm.schema.*
import java.sql.Types
import java.sql.PreparedStatement

import java.time.LocalDateTime
import java.sql.ResultSet

import app.domain.core.BaseEntity
import app.domain.core.BaseTable
import app.types.Page
import app.types.PageSize

fun <E: BaseEntity<E>, T: BaseTable<E>> EntitySequence<E, T>.upsert(
	entity: E,
	predicate: (T) -> ColumnDeclaring<Boolean>,
) : Pair<Boolean, Boolean> {
	val row = this.find(predicate)
	if(row == null) {
		entity.createdAt = LocalDateTime.now()
		entity.updatedAt = LocalDateTime.now()
		this.add(entity)
		return Pair(true, false)
	} else {
		entity.id = row.id
		entity.createdBy = row.createdBy
		entity.createdAt = row.createdAt
		entity.updatedAt = LocalDateTime.now()
		this.update(entity)
		return Pair(false, true)
	}
}

fun <E: BaseEntity<E>, T: BaseTable<E>> EntitySequence<E, T>.deleteIf(
		predicate: (T) -> ColumnDeclaring<Boolean>,
	): Boolean {
	val row = this.find(predicate)
	if(row != null) {
		row.deleted = true
		row.updatedAt = LocalDateTime.now()
		return row.flushChanges() > 0
	}
	return false
}

fun <E : BaseEntity<E>, T : BaseTable<E>> EntitySequence<E, T>.filterIf(
    condition: Boolean,
    predicate: (T) -> ColumnDeclaring<Boolean>
): EntitySequence<E, T> {
    if (condition) {
        return this.filter(predicate)
    } else {
        return this
    }
}

fun <E : BaseEntity<E>, T : BaseTable<E>> EntitySequence<E, T>.pagination(
    page: Page,
	pageSize: PageSize
): EntitySequence<E, T> {
    return this
			.drop((page - 1) * pageSize)
			.take(page * pageSize)
}
