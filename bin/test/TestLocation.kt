package app.test

import io.kotest.core.spec.style.BehaviorSpec
// import io.kotest.core.spec.style.*
import io.kotest.matchers.*
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldStartWith
import io.kotest.assertions.throwables.shouldThrow

import org.ktorm.database.*
import org.ktorm.dsl.*
import org.ktorm.database.Database
import org.ktorm.database.use
import org.ktorm.schema.*

import app.database.main
import app.domain.core.entity.*
import app.role.Admin
import app.role.User
import app.service.admin.*
import app.service.member.*
import app.domain.location.*

class TestLocation : BehaviorSpec({

    beforeSpec {
        execSqlScript("init-table.h2.sql")
        execSqlScript("init-data.h2.sql")
    }

    Given("관리자가") {
        When("권역정보를 필수값이 없으면") {
            then("에러") {
                val admin1 = Admin(1)
                val exception = shouldThrow<IllegalArgumentException> {
                    admin1.upsertLocation(
                        input = UpsertLocation.Input(
                            name = "",
                            children = emptyList()
                        )
                    )
                }
                exception.message shouldStartWith "지역명은 필수 값입니다."
            }
        }
        When("권역정보를 맞는 값을 넣으면") {
            then("생성") {
                val admin1 = Admin(1)
                val result = admin1.upsertLocation(
                    input = UpsertLocation.Input(
                        name = "서울",
                        children = emptyList()
                    )
                )
                result shouldNotBe null
                result.seq shouldBe 1
            }
        }
        When("권역정보를 맞는 값을 넣으면") {
            then("수정") {
                val admin1 = Admin(1)
                val result = admin1.upsertLocation(
                    input = UpsertLocation.Input(
                        name = "서울",
                        children = emptyList()
                    )
                )
                result shouldNotBe null
                result.seq shouldBe 1
            }
        }
    }
})