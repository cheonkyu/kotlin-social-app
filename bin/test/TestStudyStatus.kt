package app.test

import io.kotest.core.spec.style.BehaviorSpec
// import io.kotest.core.spec.style.*
import io.kotest.matchers.*
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldStartWith
import io.kotest.assertions.throwables.shouldThrow

import org.ktorm.database.*
import org.ktorm.entity.*
import org.ktorm.dsl.*
import org.ktorm.database.Database
import org.ktorm.schema.*
import java.time.LocalDateTime

import app.domain.core.entity.*
import app.database.main
import app.role.User
import app.service.admin.*
import app.service.member.*
import app.domain.study.*
import app.domain.study.group.*
import app.domain.study.status.*
import app.types.*

class TestStudyStatus : BehaviorSpec({

    beforeSpec {
        execSqlScript("init-table.h2.sql")
        execSqlScript("init-data.h2.sql")
    }

    Given("모임 장이 스터디 모집상태를 변경") {
        When("회원1이 스터디 작성 / 회원2가 스터디 참여할때") {
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

                val user2 = User(2)
                val result1 = user2.upsertStudyGroup(
                    input = UpsertStudyGroup.Input(
                        studySeq = result.seq,
                        userRole = STUDY_GROUP_USER_ROLE.MEMBER
                    )
                )
                result1 shouldNotBe null
                result1.studySeq shouldBe result.seq
                result1.userSeq shouldBe 2

                val result2 = user2.findOneStudy(
                    input = FindOneStudy.Input(
                        studySeq = result.seq
                    )
                )
                result2 shouldNotBe null
                result2.seq shouldBe result.seq
                result2.users shouldNotBe null
                result2.users.size shouldBe 2
            }
        }
        When("모임장이 아닌 회원2이 스터디 모집 중단하려 하면") {
            then("에러") {
                val user2 = User(2)
                val exception = shouldThrow<IllegalStateException> {
                    user2.updateStudyStatus(
                        input = UpdateStudyStatus.Input(
                            studySeq = 1,
                            status = STUDY_STATUS.RECRUIT_COMPLETE
                        )
                    )
                }
                exception.message shouldStartWith "스터디 모임 리더만 상태를 변경할 수 있습니다."
            }
        }
        When("모임장인 회원1이 스터디 모집 중단하려 하면") {
            then("성공") {
                val user1 = User(1)
                val result = user1.updateStudyStatus(
                    input = UpdateStudyStatus.Input(
                        studySeq = 1,
                        status = STUDY_STATUS.RECRUIT_COMPLETE
                    )
                )
                result shouldNotBe null
                result.seq shouldBe 1
            }
        }
    }
})