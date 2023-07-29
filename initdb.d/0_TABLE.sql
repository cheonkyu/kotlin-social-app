use social;
DROP TABLE IF EXISTS User;
CREATE TABLE User (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `userId` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'userId',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'pw',
  `createdBy` bigint NOT NULL COMMENT '최초 생성한 사용자 PK',
  `updatedBy` bigint NOT NULL COMMENT '갱신한 사용자 PK',
  `createdAt` timestamp NULL DEFAULT NULL COMMENT '생성일',
  `updatedAt` timestamp NULL DEFAULT NULL COMMENT '갱신일',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '삭제여부',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS Post;
CREATE TABLE Post (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'title',
  `content` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'content',
  `createdBy` bigint NOT NULL COMMENT '최초 생성한 사용자 PK',
  `updatedBy` bigint NOT NULL COMMENT '갱신한 사용자 PK',
  `createdAt` timestamp NULL DEFAULT NULL COMMENT '생성일',
  `updatedAt` timestamp NULL DEFAULT NULL COMMENT '갱신일',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '삭제여부',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
