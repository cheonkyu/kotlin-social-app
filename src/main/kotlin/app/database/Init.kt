package app.database

import app.domain.entity.*
import org.ktorm.database.*
import org.ktorm.database.use
import org.ktorm.dsl.*
import app.database.main
import org.ktorm.schema.*

fun execSqlScript(filename: String) {
    main.useConnection { conn ->
            conn.createStatement().use { statement ->
                {}.javaClass.classLoader
                    .getResourceAsStream(filename)
                    .bufferedReader()
                    .use { reader ->
                        for (sql in reader.readText().split(';')) {
                            if (sql.any { it.isLetterOrDigit() }) {
                                statement.executeUpdate(sql)
                            }
                        }
            }
        }
    }
}