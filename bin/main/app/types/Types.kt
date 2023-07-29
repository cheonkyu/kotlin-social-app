package app.types


import java.time.LocalDateTime
// 
// @JvmInline
// ktorm 호환 안됨 => 우선 typealias

typealias Seq = Long
typealias UserSeq = Long

typealias CreatedAt = LocalDateTime
typealias UpdatedAt = LocalDateTime
typealias Deleted = Boolean

// Location
typealias ParentSeq = Long
typealias Depth = Int
typealias Latitude = Double // 위도
typealias Longitude = Double // 경도
typealias Sigu = String
typealias Dong = String
typealias Selected = Boolean

// Study
typealias PlaceSeq = Long
typealias LocationSeq = Long
typealias OpenChatRoomLink = String
typealias Title = String
typealias Content = String
typealias IsOffline = Boolean
typealias Public = Boolean
typealias Password = String
typealias openChatRoomLink = String
typealias PlaceName = String
typealias AddressName = String
typealias RoadAddressName = String
typealias JoinedContent = String
typealias PriceContent = String
typealias DetailAddressName = String
typealias MinMember = Int
typealias MaxMember = Int

// StudyGroup
typealias StudySeq = Long

typealias Recommend = Boolean
typealias BackGroundColor = String
typealias Shortcut = Boolean
typealias TagGroupSeq = Long

// Matric
typealias GroupSeq = Long
typealias HashtagSeq = Long
typealias MatricGroupSeq = Long

typealias ImageUrl = String
typealias Order = Int

typealias MaxLimit = Int

typealias Page = Int
typealias PageSize = Int

typealias Name = String
typealias OnlineLink = String

typealias MatricSeq = Long
typealias MainStudySeq = Long

typealias Total = Int

typealias FileSeq = Long
typealias FileGroupSeq = Long
typealias FileName = String
typealias Extension = String
typealias Url = String

typealias Keyword = String

// todo enum
typealias MODE = String

typealias LastMakeDate = LocalDateTime
typealias LastJoinDate = LocalDateTime

typealias ThumbURL = String