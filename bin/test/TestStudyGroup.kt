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
import app.types.*

class TestStudyGroup : BehaviorSpec({

    beforeSpec {
        execSqlScript("init-table.h2.sql")
        execSqlScript("init-data.h2.sql")
    }

    Given("일반회원이 스터디 모임에 참여/미참여") {
        val user = User(1)
        val password = "1234!@#$"
        val studySeq = 1L
        When("회원1이 스터디를 작성") {
            then("생성") {
                val result = user.createStudy(
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
                        public = false,
                        password = password,
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

        When("회원2가 스터디 참여 시 비밀번호 틀렸을 때") {
            then("참가불가") {
                val user2 = User(2)
                val wrongPassword = "1234"
                val exception = shouldThrow<IllegalStateException> {
                    user2.upsertStudyGroup(
                        input = UpsertStudyGroup.Input(
                            studySeq = studySeq,
                            password = wrongPassword
                        )
                    )
                }

                exception.message shouldStartWith "스터디 그룹에 참여하실 수 없습니다. 비밀번호를 확인해주세요."
            }
        }

        When("회원2가 스터디 참여 시 비밀번호가 맞으면") {
            then("참가") {
                val user2 = User(2)
                val result = user2.upsertStudyGroup(
                    input = UpsertStudyGroup.Input(
                        studySeq = studySeq,
                        password = password
                    )
                )
                result shouldNotBe null
            }
        }

        When("회원3,4,5가 스터디 참여") {
            then("참가") {
                val user3 = User(3)
                user3.upsertStudyGroup(
                    input = UpsertStudyGroup.Input(
                        studySeq = studySeq,
                        password = password
                    )
                ) shouldNotBe null
                
                val user4 = User(4)
                user4.upsertStudyGroup(
                    input = UpsertStudyGroup.Input(
                        studySeq = studySeq,
                        password = password
                    )
                ) shouldNotBe null

                val user5 = User(5)
                user5.upsertStudyGroup(
                    input = UpsertStudyGroup.Input(
                        studySeq = studySeq,
                        password = password
                    )
                ) shouldNotBe null
            }
        }

        When("회원6이 참여 시 최대인원을 초과할 때") {
            then("참가불가") {
                val user6 = User(6)
                val exception = shouldThrow<IllegalStateException> {
                    user6.upsertStudyGroup(
                        input = UpsertStudyGroup.Input(
                            studySeq = studySeq,
                            password = password
                        )
                    )
                }
                exception.message shouldStartWith "스터디 모집 인원이 초과되었습니다."
            }
        }

        When("회원4이 불참") {
            then("참가인원은 회원1,2,3,5로 4명이 되야함") {
                val user4 = User(4)
                user4.deleteStudyGroup(
                    input = DeleteStudyGroup.Input(
                        studySeq = studySeq
                    )
                ) shouldNotBe null

                val result = main.studyGroups
                    .filter { (it.studySeq eq studySeq) and (it.deleted eq false) }
                    .count() 
                result shouldBe 4
            }
        }

        When("회원2가 스터디를 삭제된 참여 시") {
            then("참가불가") {
                val result = user.deleteStudy(
                    input = DeleteStudy.Input(
                        seq = studySeq
                    )
                )
                result shouldNotBe null
                result.seq shouldBe studySeq
                
                val _user = User(2)
                val exception = shouldThrow<IllegalStateException> {
                    _user.upsertStudyGroup(
                        input = UpsertStudyGroup.Input(
                            studySeq = studySeq,
                        )
                    )
                }

                exception.message shouldStartWith "해당 스터디를 찾을 수 없습니다."
            }
        }        
    }
})