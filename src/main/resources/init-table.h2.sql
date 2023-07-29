DROP TABLE IF EXISTS "user";
CREATE TABLE "user" (
"id" bigint NOT NULL AUTO_INCREMENT COMMENT 'PK',
"userid" varchar(20) NOT NULL COMMENT '내용',
"password" varchar(250) NOT NULL COMMENT '글',
"createdBy" bigint NOT NULL COMMENT '최초 생성한 사용자 PK',
"createdAt" datetime NOT NULL COMMENT '생성일',
"updatedBy" bigint DEFAULT NULL COMMENT '최종 수정한 사용자 PK',
"updatedAt" datetime DEFAULT NULL COMMENT '최종 수정일',
"deleted" tinyint NOT NULL DEFAULT '0' COMMENT '삭제 유무(참(1)/거짓(0))',
);

DROP TABLE IF EXISTS "post";
CREATE TABLE "post" (
"id" bigint NOT NULL AUTO_INCREMENT COMMENT 'PK',
"title" varchar(20) NOT NULL COMMENT '사용자 아이디',
"content" varchar(20) NOT NULL COMMENT '사용자 비밀번호',
"createdBy" bigint NOT NULL COMMENT '최초 생성한 사용자 PK',
"createdAt" datetime NOT NULL COMMENT '생성일',
"updatedBy" bigint DEFAULT NULL COMMENT '최종 수정한 사용자 PK',
"updatedAt" datetime NULL DEFAULT NULL COMMENT '최종 수정일',
"deleted" tinyint NOT NULL DEFAULT '0' COMMENT '삭제 유무(참(1)/거짓(0))',
);
