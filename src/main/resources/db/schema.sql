--
-- H2 인 메모리 DB 테이블 설계
--

DROP TABLE IF EXISTS USER_ACCOUNT CASCADE;
CREATE TABLE IF NOT EXISTS USER_ACCOUNT COMMENT '사용자 계정 테이블' (
    ID                  INT                 NOT NULL AUTO_INCREMENT COMMENT '사용자 계정 번호',
    EMAIL               VARCHAR(45)         UNIQUE NOT NULL COMMENT '이메일',
    PASSWORD            VARCHAR(255)        NOT NULL COMMENT '비밀번호',
    NICKNAME            VARCHAR(15)         UNIQUE NOT NULL COMMENT '닉네임',
    NAME                VARCHAR(15)         NOT NULL COMMENT '이름',
    PHONE               VARCHAR(11)         NOT NULL COMMENT '휴대폰 번호',
    LOGIN_TYPE          VARCHAR(5)          NOT NULL COMMENT '로그인 유형',
    ENABLED             TINYINT(1)          NOT NULL COMMENT '계정 활성화 여부',
    DELETED_YN          CHAR(1)             NOT NULL DEFAULT 'N' COMMENT '삭제 여부',
    CREATED_AT          DATETIME            NOT NULL DEFAULT NOW() COMMENT '가입 일자',
    UPDATED_AT          DATETIME            NOT NULL COMMENT '변경 일자',
    DELETED_AT          DATETIME            NULL COMMENT '삭제 일자',
    PRIMARY KEY (ID)
);


DROP TABLE IF EXISTS USER_ROLE CASCADE;
CREATE TABLE IF NOT EXISTS USER_ROLE COMMENT '사용자별 권한 테이블' (
    USER_ID             INT                 NOT NULL COMMENT '사용자 번호',
    ROLE                VARCHAR(20)         NOT NULL COMMENT '권한 코드 : ROLE_USER / ROLE_MANAGER / ROLE_ADMIN',
    USED_YN             CHAR(1)             NOT NULL DEFAULT 'Y' COMMENT '권한 사용 여부',
    CREATED_AT          DATETIME            NOT NULL DEFAULT NOW() COMMENT '권한 등록 일자',
    UPDATED_AT          DATETIME            NOT NULL COMMENT '권한 변경 일자',
    PRIMARY KEY (USER_ID, ROLE)
);
ALTER TABLE USER_ROLE ADD CONSTRAINT USER_ROLE_USER_ID_FK
FOREIGN KEY (USER_ID) REFERENCES USER_ACCOUNT (ID) ON UPDATE CASCADE;


DROP TABLE IF EXISTS USER_PROFILE CASCADE;
CREATE TABLE IF NOT EXISTS USER_PROFILE COMMENT '사용자 프로필 테이블' (
    ID                  INT                 NOT NULL AUTO_INCREMENT COMMENT '프로필 번호',
    USER_ID             INT                 NOT NULL COMMENT '사용자 계정 번호',
    PROFILE_IMG_URL     VARCHAR(255)        NOT NULL COMMENT '프로필 이미지 저장 경로',
    BIO                 VARCHAR(150)        NOT NULL COMMENT '한 줄 소개',
    UNIV                VARCHAR(45)         NOT NULL COMMENT '재학 또는 졸업 대학교 명',
    CITY                VARCHAR(20)         NOT NULL COMMENT '거주 도시명',
    CREATED_AT          DATETIME            NOT NULL DEFAULT NOW() COMMENT '프로필 등록 일자',
    UPDATED_AT          DATETIME            NOT NULL COMMENT '프로필 변경 일자',
    PRIMARY KEY (ID)
);
ALTER TABLE USER_PROFILE ADD CONSTRAINT USER_PROFILE_USER_ID_FK
FOREIGN KEY (USER_ID) REFERENCES USER_ACCOUNT (ID) ON UPDATE CASCADE;


DROP TABLE IF EXISTS LOGIN_HISTORY CASCADE;
CREATE TABLE IF NOT EXISTS LOGIN_HISTORY COMMENT '로그인/로그아웃 이력 테이블' (
    ID                  INT                 NOT NULL AUTO_INCREMENT COMMENT '이력 번호',
    USER_ID             INT                 NOT NULL COMMENT '사용자 계정 번호',
    IP_ADDRESS          VARCHAR(15)         NOT NULL COMMENT '접속 IP 주소',
    AGENT               VARCHAR(255)        NOT NULL COMMENT '사용자 접속 식별 정보',
    EVENT_TYPE          CHAR(1)             NOT NULL COMMENT '로그인/로그아웃 구분 코드 - I : 로그인 / O : 로그아웃',
    LOGIN_DATETIME      DATETIME            NOT NULL COMMENT '로그인 일자',
    LOGOUT_DATETIME     DATETIME            NULL COMMENT '로그아웃 일자',
    PRIMARY KEY (ID)
);
ALTER TABLE LOGIN_HISTORY ADD CONSTRAINT LOGIN_HISTORY_USER_ID_FK
FOREIGN KEY (USER_ID) REFERENCES USER_ACCOUNT (ID) ON UPDATE CASCADE;
