-- social.Category definition

DROP TABLE IF EXISTS `Category`
CREATE TABLE `Category` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '카테고리명',
  `createdAt` timestamp NULL DEFAULT NULL COMMENT '생성일',
  `updatedAt` timestamp NULL DEFAULT NULL COMMENT '갱신일',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- social.HashTag definition

DROP TABLE IF EXISTS `HashTag`
CREATE TABLE `HashTag` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '태그명',
  `createdAt` timestamp NULL DEFAULT NULL COMMENT '생성일',
  `updatedAt` timestamp NULL DEFAULT NULL COMMENT '갱신일',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '삭제여부',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- social.Category_HashTag definition

DROP TABLE IF EXISTS `Category_HashTag`
CREATE TABLE `Category_HashTag` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `categoryId` bigint unsigned DEFAULT NULL COMMENT '카테고리id',
  `hashTagId` bigint unsigned DEFAULT NULL COMMENT '태그id',
  `createdAt` timestamp NULL DEFAULT NULL COMMENT '생성일',
  `updatedAt` timestamp NULL DEFAULT NULL COMMENT '갱신일',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '삭제여부',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- social.Meeting definition

DROP TABLE IF EXISTS `Meeting`
CREATE TABLE `Meeting` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '제목',
  `content` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '내용',
  `minLimit` bigint unsigned DEFAULT NULL COMMENT '최소제한인원',
  `maxLimit` bigint unsigned DEFAULT NULL COMMENT '최대제한인원',
  `createdAt` timestamp NULL DEFAULT NULL COMMENT '생성일',
  `updatedAt` timestamp NULL DEFAULT NULL COMMENT '갱신일',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '삭제여부',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- social.Memeber definition

DROP TABLE IF EXISTS `Memeber`
CREATE TABLE `Memeber` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `username` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '계정id',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '계정password',
  `createdAt` timestamp NULL DEFAULT NULL COMMENT '생성일',
  `updatedAt` timestamp NULL DEFAULT NULL COMMENT '종료일',
  `deleted` tinyint(1) DEFAULT NULL COMMENT '삭제여부',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- social.Meeting_Member definition

DROP TABLE IF EXISTS `Meeting_Member`
CREATE TABLE `Meeting_Member` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `meetingId` bigint unsigned DEFAULT NULL,
  `memberId` bigint unsigned DEFAULT NULL,
  `createdAt` timestamp NULL DEFAULT NULL,
  `updatedAt` timestamp NULL DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT '0',
  `memberRole` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- social.Location definition

DROP TABLE IF EXISTS `Location`
CREATE TABLE `Location` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '위치명',
  `latitude` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '위도',
  `longitude` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '경도',
  `createdAt` timestamp NULL DEFAULT NULL COMMENT '생성일',
  `updatedAt` timestamp NULL DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT '0' COMMENT '삭제여부',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;