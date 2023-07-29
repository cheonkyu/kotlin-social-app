package app.test

import io.kotest.core.spec.style.BehaviorSpec
// import io.kotest.core.spec.style.*
import io.kotest.matchers.*
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldStartWith
import io.kotest.assertions.throwables.shouldThrow

import app.domain.tagGroup.*

import app.domain.core.entity.*
import org.ktorm.database.*
import org.ktorm.schema.*
import org.ktorm.dsl.*
import org.ktorm.entity.*

import org.ktorm.database.Database
import org.ktorm.database.use
import app.config.ktorm
import app.database.main
import app.role.Admin
import app.service.admin.*

class TestTagGroup : BehaviorSpec({

    beforeSpec {
        execSqlScript("init-table.h2.sql")
        execSqlScript("init-data.h2.sql")
    }   
    // beforeTest {
    //     println("Before every s pec/test case")
    // }

    // beforeSpec {
    //     println("Before every test suite")
    // }

    // afterTest {
    //     println("After every spec/test case")
    // }

    // afterSpec {
    //     println("After every test suite")
    // }


    Given("관리자만 태그그룹을 관리") {
        val admin = Admin(1)
        When("태그그룹을") {
            then("조회하면 우선 6건") {
                val result = admin.paginationTagGroup(
                    input = PaginationTagGroup.Input(
                        page = 1,
                        pageSize = 10
                    )
                )

                result shouldNotBe null
                result.data.size shouldBe 6
            }
        }
        When("태그그룹 등록 시") {
            then("제목이 '' 빈 문자열이면 불가") {
                val exception = shouldThrow<IllegalArgumentException> {
                    CreateTagGroup.Input(
                        title = "",
                        content = "H2태그그룹",
                        imageUrl = "https://studymoa-moim.s3.ap-northeast-2.amazonaws.com/a7b7b537-38c6-432e-b5ab-64a229fca366",
                        shortcut = false,
                        recommend = false,
                    )
                }
                exception.message shouldStartWith "제목을 입력해주세요."
            }
            then("제목이 '   ' 공백이여도 불가") {
                val exception = shouldThrow<IllegalArgumentException> {
                    CreateTagGroup.Input(
                        title = " ",
                        content = "H2태그그룹",
                        imageUrl = "https://studymoa-moim.s3.ap-northeast-2.amazonaws.com/a7b7b537-38c6-432e-b5ab-64a229fca366",
                        shortcut = false,
                        recommend = false,
                    )
                }
                exception.message shouldStartWith "제목을 입력해주세요."
            }
        }
        When("태그그룹 등록할 때") {
            then("맞는 값일 시 등록") {
                val result = admin.createTagGroup(
                    input = CreateTagGroup.Input(
                        title = "H2태그그룹",
                        content = "H2태그그룹",
                        imageUrl = "https://studymoa-moim.s3.ap-northeast-2.amazonaws.com/a7b7b537-38c6-432e-b5ab-64a229fca366",
                        shortcut = false,
                        recommend = false,
                    )
                )
                result shouldNotBe null
                result.seq shouldNotBe 0
                result.seq shouldNotBe null
            }
            then("신규 태그그룹 등록 확인 (6건 -> 7건)") {
                val result = admin.paginationTagGroup(
                    input = PaginationTagGroup.Input(
                        page = 1,
                        pageSize = 10
                    )
                )
                
                result shouldNotBe null
                result.data.size shouldBe 7
            }
        }
        When("태그그룹 등록 시 바로가기 체크하면") {
            then("태그그룹 및 바로가기 동시 등록") {
                val result = admin.createTagGroup(
                    input = CreateTagGroup.Input(
                        title = "H2태그그룹_바로가가",
                        content = "H2태그그룹_바로가가",
                        imageUrl = "https://studymoa-moim.s3.ap-northeast-2.amazonaws.com/a7b7b537-38c6-432e-b5ab-64a229fca366",
                        shortcut = true,
                        recommend = false,
                    )
                )
                result shouldNotBe null
                result.seq shouldNotBe 0
                result.seq shouldNotBe null
            }
            then("신규 태그그룹 등록 확인 (7건 -> 8건)") {
                val result = admin.paginationTagGroup(
                    input = PaginationTagGroup.Input(
                        page = 1,
                        pageSize = 10
                    )
                )
                
                result shouldNotBe null
                result.data.size shouldBe 8
            }
            then("신규 바로가기 등록 확인 (0건 -> 1건)") {
                val result = admin.findAllMainShortcut()
                result shouldNotBe null
                result.size shouldBe 1
            }
        }
        When("태그그룹 등록 시 추천목록 체크하면") {
            then("태그그룹 및 추천목록 동시 등록") {
                val result = admin.createTagGroup(
                    input = CreateTagGroup.Input(
                        title = "H2태그그룹_추천목록",
                        content = "H2태그그룹_추천목록",
                        imageUrl = "https://studymoa-moim.s3.ap-northeast-2.amazonaws.com/a7b7b537-38c6-432e-b5ab-64a229fca366",
                        shortcut = false,
                        recommend = true,
                    )
                )
                result shouldNotBe null
                result.seq shouldNotBe 0
                result.seq shouldNotBe null
            }
            then("신규 태그그룹 등록 확인 (8건 -> 9건)") {
                val result = admin.paginationTagGroup(
                    input = PaginationTagGroup.Input(
                        page = 1,
                        pageSize = 10
                    )
                )
                
                result shouldNotBe null
                result.data shouldNotBe null
                result.data.size shouldBe 9
            }
            then("신규 추천목록 등록 확인 (0건 -> 1건)") {
                val result = admin.findAllMainStudy()
                result shouldNotBe null
                result.size shouldBe 1
            }
        }
        When("태그그룹 수정할 때") {
            then("키 값(seq)는 0 이하이면 불가") {
                val exception = shouldThrow<IllegalArgumentException> {
                    UpdateTagGroup.Input(
                        seq = 0,
                        title = "",
                        content = "H2태그그룹",
                        imageUrl = "https://studymoa-moim.s3.ap-northeast-2.amazonaws.com/a7b7b537-38c6-432e-b5ab-64a229fca366",
                        shortcut = false,
                        recommend = false,
                    )
                }
                exception.message shouldStartWith("태그그룹을 다시 선택해주세요.")
            }
            then("제목이 '' 빈 문자열이면 불가") {
                val exception = shouldThrow<IllegalArgumentException> {
                    UpdateTagGroup.Input(
                        seq = 7,
                        title = "",
                        content = "H2태그그룹",
                        imageUrl = "https://studymoa-moim.s3.ap-northeast-2.amazonaws.com/a7b7b537-38c6-432e-b5ab-64a229fca366",
                        shortcut = false,
                        recommend = false,
                    )
                }
                exception.message shouldStartWith("제목을 입력해주세요.")
            }
            then("제목이 '   ' 공백이여도 불가") {
                val exception = shouldThrow<IllegalArgumentException> {
                    UpdateTagGroup.Input(
                        seq = 7,
                        title = " ",
                        content = "H2태그그룹",
                        imageUrl = "https://studymoa-moim.s3.ap-northeast-2.amazonaws.com/a7b7b537-38c6-432e-b5ab-64a229fca366",
                        shortcut = false,
                        recommend = false,
                    )
                }
                exception.message shouldStartWith("제목을 입력해주세요.")
            }
            then("맞는 값일 시 수정") {
                val result = admin.updateTagGroup(
                    input = UpdateTagGroup.Input(
                        seq = 7,
                        title = "H2태그그룹222",
                        content = "H2태그그룹222",
                        imageUrl = "https://studymoa-moim.s3.ap-northeast-2.amazonaws.com/a7b7b537-38c6-432e-b5ab-64a229fca366",
                        shortcut = false,
                        recommend = false,
                    )
                )
                result shouldNotBe null
                result.seq shouldBe 7
            }
        }
        When("태그그룹 수정 시 바로가기 체크하면") {
            then("바로가기 등록") {
                val tapGroup = admin.updateTagGroup(
                    input = UpdateTagGroup.Input(
                        seq = 7,
                        title = "H2태그그룹222",
                        content = "H2태그그룹222",
                        imageUrl = "https://studymoa-moim.s3.ap-northeast-2.amazonaws.com/a7b7b537-38c6-432e-b5ab-64a229fca366",
                        shortcut = true,
                        recommend = false,
                    )
                )
                tapGroup shouldNotBe null
                tapGroup.seq shouldNotBe null
                
                val shortcut = main.mainShortcuts
                    .filter { it.tagGroupSeq eq tapGroup.seq!! }
                    .filter { it.deleted eq false }
                    .firstOrNull()
                shortcut shouldNotBe null
            }
        }
        When("태그그룹 수정 시 바로가기 체크해제") {
            then("바로가기 삭제") {
                val tapGroup = admin.updateTagGroup(
                    input = UpdateTagGroup.Input(
                        seq = 7,
                        title = "H2태그그룹222",
                        content = "H2태그그룹222",
                        imageUrl = "https://studymoa-moim.s3.ap-northeast-2.amazonaws.com/a7b7b537-38c6-432e-b5ab-64a229fca366",
                        shortcut = false,
                        recommend = false,
                    )
                )
                tapGroup shouldNotBe null
                tapGroup.seq shouldNotBe null
                
                val shortcut = main.mainShortcuts
                    .filter { it.tagGroupSeq eq tapGroup.seq!! }
                    .filter { it.deleted eq false }
                    .firstOrNull()
                shortcut shouldBe null
            }
        }
        When("태그그룹 수정 시 추천목록 체크하면") {
            then("추천목록 등록") {
                val tapGroup = admin.updateTagGroup(
                    input = UpdateTagGroup.Input(
                        seq = 7,
                        title = "H2태그그룹222",
                        content = "H2태그그룹222",
                        imageUrl = "https://studymoa-moim.s3.ap-northeast-2.amazonaws.com/a7b7b537-38c6-432e-b5ab-64a229fca366",
                        shortcut = false,
                        recommend = true,
                    )
                )
                tapGroup shouldNotBe null
                tapGroup.seq shouldNotBe null
                
                val recommend = main.mainStudies
                    .filter { it.tagGroupSeq eq tapGroup.seq!! }
                    .filter { it.deleted eq false }
                    .firstOrNull()
                recommend shouldNotBe null
            }
        }
        When("태그그룹 수정 시 추천목록 체크해제") {
            then("추천목록 삭제") {
                val tapGroup = admin.updateTagGroup(
                    input = UpdateTagGroup.Input(
                        seq = 7,
                        title = "H2태그그룹222",
                        content = "H2태그그룹222",
                        imageUrl = "https://studymoa-moim.s3.ap-northeast-2.amazonaws.com/a7b7b537-38c6-432e-b5ab-64a229fca366",
                        shortcut = false,
                        recommend = false,
                    )
                )
                tapGroup shouldNotBe null
                tapGroup.seq shouldNotBe null

                val recommend = main.mainStudies
                    .filter { it.tagGroupSeq eq tapGroup.seq!! }
                    .filter { it.deleted eq false }
                    .firstOrNull()
                recommend shouldBe null
            }
        }
    }
})