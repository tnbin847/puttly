--- H2 인메모리 DB 테이블 설계


DROP TABLE IF EXISTS USER_ACCOUNT CASCADE;
CREATE TABLE IF NOT EXISTS USER_ACCOUNT COMMENT '사용자 계정 테이블' (
    ID                  INT                 NOT NULL AUTO_INCREMENT COMMENT '사용자계정 번호',
    EMAIL               VARCHAR(40)         UNIQUE NOT NULL COMMENT '이메일',
    PASSWORD            VARCHAR(255)        NOT NULL COMMENT '비밀번호',
    NICKNAME            VARCHAR(15)         UNIQUE NOT NULL COMMENT '닉네임',
    NAME                VARCHAR(15)         NOT NULL COMMENT '이름',
    PHONE               VARCHAR(11)         NOT NULL COMMENT '휴대폰번호',
    LOGIN_TYPE          VARCHAR(5)          NOT NULL COMMENT '로그인 유형 - LOCAL : 일반 로그인 계정 / OAUTH : 소셜 로그인 계정',
    ENABLED             TINYINT(1)          NOT NULL COMMENT '계정 활성화 값',
    DELETED_YN          CHAR(1)             NOT NULL DEFAULT 'N' COMMENT '(논리)계정 삭제 여부',
    CREATED_AT          DATETIME            NOT NULL DEFAULT NOW() COMMENT '가입 일자',
    UPDATED_AT          DATETIME            NOT NULL COMMENT '변경 일자',
    DELETED_AT          DATETIME            NULL COMMENT '(논리)삭제 일자',
    PRIMARY KEY (ID)
);


DROP TABLE IF EXISTS USER_ROLE CASCADE;
CREATE TABLE IF NOT EXISTS USER_ROLE COMMENT '사용자별 권한 테이블' (
    USER_ID             INT                 NOT NULL COMMENT '사용자 계정 번호',
    ROLE                VARCHAR(15)         NOT NULL COMMENT '권한 명 (ROLE_USER : 일반회원 / ROLE_MANAGER : 매니저 / ROLE_ADMIN : 관리자)',
    USED_YN             CHAR(1)             NOT NULL DEFAULT 'Y' COMMENT '부여된 권한의 사용 여부',
    CREATED_AT          DATETIME            NOT NULL DEFAULT NOW() COMMENT '권한 부여 일자',
    UPDATED_AT          DATETIME            NOT NULL COMMENT '권한 변경 일자',
    PRIMARY KEY (USER_ID, ROLE)
);
ALTER TABLE USER_ROLE ADD CONSTRAINT USER_ROLE_USER_ID_FK
FOREIGN KEY (USER_ID) REFERENCES USER_ACCOUNT (ID) ON UPDATE CASCADE;