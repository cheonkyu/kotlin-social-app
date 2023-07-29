package app.types

// enum class MODE(
//     val koName: String
// ) {
//     TEST("test"),
//     PRODUCTION("production"),
// }

enum class STUDY_STATUS(
    val koName: String
) {
    RECRUITING("모집중"),
    RECRUIT_COMPLETE("모집완료"),
    MEETING("모임중"),
    MEET_COMPLETE("모임완료"),
    CANCELLED("취소"),
}

enum class STUDY_GROUP_USER_ROLE(
    val koName: String
) {
    LEADER("리더"),
    MEMBER("참여자"),
}

enum class MATRIC_COMMAND(
    val koName: String
) {
    FILTER("필터"),
    SORT("정렬"),
}

enum class MATRIC_TARGET_TYPE(
    val koName: String
) {
    INT("Int"),
    LONG("Long"),
    STRING("String"),
    INSTANT("Instant"),
    TIMESTAMP("Instant"),
}


enum class MATRIC_TARGET_OPERATOR_TYPE(
    val koName: String
) {
    EQ("=="),
    GT(">"),
    GTE(">="),
    LT("<"),
    LTE("<="),
}

enum class MATRIC_GROUP_TYPE(
    val koName: String
) {
    MYSTUDY("MYSTUDY"),
    BOOKMARK("BOOKMARK"),
    TAGGROUP("TAGGROUP"),
}

enum class MATRIC_CONDITION_TYPE(
    val koName: String
) {
    MY_STUDY("MY_STUDY"),
    BOOKMARK("BOOKMARK"),
    NEW_STUDY("NEW_STUDY"),
    TAGGROUP("TAGGROUP"),
    TIME_LIMIT("TIME_LIMIT"),
}

enum class TAGGROUP_CONDITION_TYPE(
    val koName: String
) {
    MY_STUDY("MY_STUDY"),
    BOOKMARK("BOOKMARK"),
    NEW_STUDY("NEW_STUDY"),
    TAGGROUP("TAGGROUP"),
    TIME_LIMIT("TIME_LIMIT"),
}

enum class TAGGROUP_SHOW_TYPE(
    val koName: String
) {
    ALL("ALL"),
    USER("USER"),
    PUBLIC("PUBLIC"),
}

enum class STUDY_PAGE_ORDER(
    val koName: String
) {
    NEW_STUDY_DESC("NEW_STUDY_DESC"),
    TIME_LIMIT_DESC("TIME_LIMIT_DESC"),
}
