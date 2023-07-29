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
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.*
import java.time.LocalDateTime


import app.domain.tagGroup.*
import app.database.main
import app.domain.core.entity.*
import app.role.User
import app.service.admin.*
import app.service.member.*
import app.domain.study.*

class TestStudy : BehaviorSpec({

    beforeSpec {
        execSqlScript("init-table.h2.sql")
        execSqlScript("init-data.h2.sql")
    }

    Given("일반회원이 스터디를 작성한다.") {
        val user = User(1)
        When("제목 글자수를 초과하면") {
            then("에러") {
                val exception = shouldThrow<IllegalArgumentException> {
                    user.createStudy(
                        input = CreateStudy.Input(
                            title = "모모모모모모모모모모".repeat(100) + "모", // 1001자
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
                }
                exception.message shouldStartWith("제목은 1000자 이내로 입력이 가능합니다.")
            }
        }
        When("내용 글자수를 초과하면") {
            then("에러") {
                val exception = shouldThrow<IllegalArgumentException> {
                    user.createStudy(
                        input = CreateStudy.Input(
                            title = "[가보자구]한강에서 주식 스터디",
                            content = "모모모모모모모모모모".repeat(200) + "모", // 2001자,
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
                }
                exception.message shouldStartWith("내용은 2000자 이내로 입력이 가능합니다.")
            }
        }
        When("채팅링크가 이상한 형식이면") {
            then("에러") {
                val exception = shouldThrow<IllegalArgumentException> {
                    user.createStudy(
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
                            openChatRoomLink = "111111ㅎㅎㅎㅎ123",
                            // openChatRoomLink = "http://chat.link",
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
                }
                exception.message shouldStartWith("링크 형식를 확인해주세요.")
            }
        }

        When("채팅링크가 이상한 형식이면") {
            then("에러") {
                val exception = shouldThrow<IllegalArgumentException> {
                    user.createStudy(
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
                            openChatRoomLink = "111111ㅎㅎㅎㅎ123",
                            // openChatRoomLink = "http://chat.link",
                            joinedContent = null,
                            priceContent = null,
                            minMember = 1,
                            maxMember = 5,
                            hashtags = listOf(
                                CreateStudy.Input.Hashtag(name = "스터디 태그는 공백이 포함되면 안됨"),
                                CreateStudy.Input.Hashtag(name = "   "),
                                CreateStudy.Input.Hashtag(name = " ")
                            )
                        )
                    )
                }
                exception.message shouldStartWith("해시태그는 공백을 입력할 수 없습니다.")
            }
        }
        
        When("스터디를 작성") {
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
            then("studySeq == 1로 스터디 항목 조회  isOffline == false == 온라인") {
                val result = user.findOneStudy(
                    input = FindOneStudy.Input(
                        studySeq = 1
                    )
                )

                result shouldNotBe null
                result.seq shouldBe 1
                result.locationSeq shouldBe 9
                result.placeName shouldBe null
                result.placeName shouldBe null
                // result.dDay shouldBe 3

                result.hashtags shouldNotBe null
                result.hashtags.size shouldBe 3
            }
        }

        When("스터디를 수정할 때") {
            then("이미 삭제되었거나 조회가 안되면 에러") {
                val exception = shouldThrow<IllegalArgumentException> {
                    val NOT_EXIST_STUDY_SEQ = 99999999L
                    user.updateStudy(
                        input = UpdateStudy.Input(
                            seq = NOT_EXIST_STUDY_SEQ,
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
                                UpdateStudy.Input.Hashtag(name = "스터디"),
                                UpdateStudy.Input.Hashtag(name = "같이"),
                                UpdateStudy.Input.Hashtag(name = "재미있게")
                            )
                        )
                    )
                }
                exception.message shouldStartWith("스터디 정보를 수정할 수 없습니다.")
            }
        }

        When("스터디를 수정할 때") {
            then("해시태그를 3개에서 2개로 줄이고 생성") {
                val studySeq = 1L
                val result = user.updateStudy(
                    input = UpdateStudy.Input(
                        seq = studySeq,
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
                            UpdateStudy.Input.Hashtag(name = "스터디"),
                            UpdateStudy.Input.Hashtag(name = "같이"),
                        )
                    )
                )

                result shouldNotBe null

                val result1 = user.findOneStudy(
                    input = FindOneStudy.Input(
                        studySeq = studySeq
                    )
                )
                result1 shouldNotBe null
                result1.hashtags shouldNotBe null
                result1.hashtags.size shouldBe 2
            }
        }

        When("스터디를 삭제할 때") {
            then("삭제 처리") {
                val studySeq = 1L
                val result = user.deleteStudy(
                    input = DeleteStudy.Input(
                        seq = studySeq
                    )
                )

                result shouldNotBe null
                result.seq shouldBe studySeq
            }
        }
    }
})