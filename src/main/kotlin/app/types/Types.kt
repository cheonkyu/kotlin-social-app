package app.types


import java.time.LocalDateTime
// 
// @JvmInline
// ktorm 호환 안됨 => 우선 typealias

typealias Id = Long
typealias UserSeq = Long

typealias CreatedAt = LocalDateTime
typealias UpdatedAt = LocalDateTime
typealias Deleted = Boolean

typealias Title = String
typealias Content = String
typealias Name = String

typealias UserId = String
typealias Password = String

typealias Page = Int
typealias PageSize = Int
typealias Total = Int