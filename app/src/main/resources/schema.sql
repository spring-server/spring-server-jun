CREATE TABLE user
(
    id               BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT COMMENT 'PK',
    username         VARCHAR(40)        NOT NULL COMMENT '사용자 이름',
    password         VARCHAR(255)       NOT NULL COMMENT '패스워드',
    created_at       TIMESTAMP          NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
    last_modified_at TIMESTAMP          NULL     DEFAULT NULL COMMENT '최종 수정일',
    deleted          VARCHAR(10)        NOT NULL COMMENT '삭제 유무'
) engine 'InnoDB';
