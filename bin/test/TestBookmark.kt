package app.test

import io.kotest.core.spec.style.BehaviorSpec
// import io.kotest.core.spec.style.*
import io.kotest.matchers.*
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldStartWith
import io.kotest.assertions.throwables.shouldThrow

import org.ktorm.database.*
import org.ktorm.dsl.*
import org.ktorm.database.Database
import org.ktorm.database.use
import org.ktorm.schema.*
import java.time.LocalDateTime

import app.database.main
import app.domain.core.entity.*
import app.role.PublicUser
import app.role.User
import app.service.admin.*
import app.service.member.*
import app.domain.study.*
import app.domain.bookmark.*

class TestBookmark : BehaviorSpec({

    beforeSpec {
        execSqlScript("init-table.h2.sql")
        execSqlScript("init-data.h2.sql")
    }

    Given("회원1이 스터디를 작성한다.") {
        When("스터디를 작성") {
            then("생성") {
                val user1 = User(1)
                val result = user1.createStudy(
                    input = CreateStudy.Input(
                        title = "[가보자구]한강에서 주식 스터디",
                        content = """
                            날도 좋은데 한강에서 주식에 대한 스터디를 하려고 합니다.
    
                            한강을 보며 의지를 다지는 스터디 어때요
                        """,
                        startDate = LocalDateTime.of(2023, 7, 15, 12, 32, 22),
                        endDate = LocalDateTime.of(2023, 7, 18, 11, 32, 22),
                        isOffline = false, 
                        onlineLink = "", 
                        placeName = "스타벅스 선릉역점",
                        addressName = "서울 강남구 역삼동 XXX-X",
                        roadAddressName = "서울 강남구 테헤란로 XXX",
                        latitude = 37.51766013568054, // 위도
                        longitude = 126.95803386590158, // 경도
                        detailAddressName = "강남 스타벅스 선릉 ",
                        public = true,
                        password = null,
                        openChatRoomLink = "http://chat.link",
                        joinedContent = null,
                        priceContent = null,
                        minMember = 1,
                        maxMember = 5,
                        hashtags = listOf(
                            CreateStudy.Input.Hashtag(name = "스터디"),
                            CreateStudy.Input.Hashtag(name = "같이"),
                            CreateStudy.Input.Hashtag(name = "재미있게")
                        )
                    )
                )

                result shouldNotBe null
                result.seq shouldBe 1
            }
        }

        When("회원1이 북마크 체크 시") {
            then("적용") {
                val user1 = User(1)
                val result = user1.upsertBookmark(
                    input = UpsertBookmark.Input(
                        seq = 1
                    )
                )
                result shouldNotBe null
            }
        }

        When("북마크 적용 후, 회원1이 스터디 정보 조회 시") {
            then("북마크가 있음") {
                val user1 = User(1)
                val result = user1.findOneStudy(
                    input = FindOneStudy.Input(
                        studySeq = 1
                    )
                )
                result shouldNotBe null
                result.bookmark shouldBe true
            }
        }

        When("로그인을 안하고 스터디 정보 조회 시") {
            then("북마크가 없음") {
                val publicUser = PublicUser()
                val result = publicUser.findOneStudy(
                    input = FindOneStudy.Input(
                        studySeq = 1
                    )
                )
                result shouldNotBe null
                result.bookmark shouldBe false
            }
        }

        When("회원2이 북마크 체크 시") {
            then("적용") {
                val user2 = User(2)
                val result = user2.upsertBookmark(
                    input = UpsertBookmark.Input(
                        seq = 1
                    )
                )
                result shouldNotBe null
            }
            then("북마크 확인") {
                val user2 = User(2)
                val result = user2.findOneStudy(
                    input = FindOneStudy.Input(
                        studySeq = 1
                    )
                )
                result shouldNotBe null
                result.bookmark shouldBe true
            }
        }

        When("삭제된 스터디에 북마크 체크 시") {
            then("불가") {
                val user1 = User(1)
                user1.deleteStudy(
                    input = DeleteStudy.Input(
                        seq = 1
                    )
                ) shouldNotBe null

                val exception = shouldThrow<IllegalStateException> {
                    user1.upsertBookmark(
                        input = UpsertBookmark.Input(
                            seq = 1
                        )
                    )
                }
                exception.message shouldStartWith "스터디 정보를 찾을 수 없습니다."
            }
        }
    }
})