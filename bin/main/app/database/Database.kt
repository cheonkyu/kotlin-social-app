package app.database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.ktorm.database.Database
import org.ktorm.entity.sequenceOf
import org.ktorm.logging.ConsoleLogger
import org.ktorm.logging.LogLevel
import org.ktorm.support.mysql.MySqlDialect
import org.ktorm.expression.SqlFormatter
import app.config.ktorm

val main =
  Database.connect(
    dataSource =
      HikariDataSource(
        HikariConfig().apply {
          driverClassName = ktorm.main.driverClassName
          jdbcUrl = ktorm.main.url
          username = ktorm.main.username
          password = ktorm.main.password
          maximumPoolSize = 3
          isAutoCommit = true
          transactionIsolation = "TRANSACTION_REPEATABLE_READ"
          validate()
        },
      ),
    logger = ConsoleLogger(threshold = LogLevel.DEBUG),
    alwaysQuoteIdentifiers = false,
    generateSqlInUpperCase = false,
  )
