-- ==================================================
-- 예제 1) 학과 테이블 생성 (컬럼 레벨 제약조건)
-- 목적: 각 컬럼 정의 시 직접 PRIMARY KEY, NOT NULL을 설정
-- SQL:
CREATE TABLE 학과
(
  학과번호   CHAR(2)       PRIMARY KEY,    -- 컬럼 레벨 기본키
  학과명     VARCHAR(20)   NOT NULL,       -- 컬럼 레벨 NOT NULL
  학과장명   VARCHAR(20)                    -- 제약 없음
);
-- 예상 스키마:
-- 학과번호(CHAR2, PK), 학과명(VARCHAR20, NOT NULL), 학과장명(VARCHAR20)


-- ==================================================
-- 예제 2) 학과 테이블 생성 (테이블 레벨 제약조건)
-- 목적: 컬럼 정의 후 맨 아래에서 PRIMARY KEY를 선언
-- SQL:
CREATE TABLE 학과
(
  학과번호   CHAR(2),                       -- 제약은 뒤에서 선언
  학과명     VARCHAR(20)   NOT NULL,       
  학과장명   VARCHAR(20),
  PRIMARY KEY(학과번호)                    -- 테이블 레벨 기본키
);
-- 예상 스키마:
-- 학과번호(CHAR2, PK), 학과명(VARCHAR20, NOT NULL), 학과장명(VARCHAR20)


-- ==================================================
-- 예제 3) 학생 테이블 생성 (컬럼 레벨 제약조건)
-- 목적: 주요 제약을 각 컬럼 바로 뒤에 설정 (PK, NOT NULL, UNIQUE, FK, CHECK, DEFAULT)
-- SQL:
CREATE TABLE 학생
(
  학번       CHAR(5)       PRIMARY KEY,               -- 기본키
  이름       VARCHAR(20)   NOT NULL,                  -- NOT NULL
  생일       DATE          NOT NULL,                  -- NOT NULL
  연락처     VARCHAR(20)   UNIQUE,                    -- 유일 제약
  학과번호   CHAR(2)       REFERENCES 학과(학과번호),  -- 외래키
  성별       CHAR(1)       CHECK(성별 IN ('남','여')), -- 체크 제약
  등록일     DATE          DEFAULT(CURDATE())         -- 기본값
);
-- 예상 스키마:
-- 학번(PK), 이름(NOT NULL), 생일(NOT NULL), 연락처(UNIQUE), 학과번호(FK), 성별(CHECK), 등록일(DEFAULT)


-- ==================================================
-- 예제 4) 학생 테이블 생성 (테이블 레벨 제약조건)
-- 목적: 제약을 테이블 정의 하단에 모아서 선언 (PK, UNIQUE, CHECK, FK)
-- SQL:
CREATE TABLE 학생
(
  학번       CHAR(5),
  이름       VARCHAR(20)   NOT NULL,
  생일       DATE          NOT NULL,
  연락처     VARCHAR(20),
  학과번호   CHAR(2),
  성별       CHAR(1),
  등록일     DATE          DEFAULT(CURDATE()),

  PRIMARY KEY(학번),                        -- 기본키
  UNIQUE(연락처),                           -- 유일 제약
  CHECK(성별 IN ('남','여')),               -- 체크 제약
  FOREIGN KEY(학과번호) REFERENCES 학과(학과번호)  -- 외래키
);
-- 예상 스키마:
-- 학번(PK), 이름(NOT NULL), 생일(NOT NULL), 연락처(UNIQUE), 학과번호(FK), 성별(CHECK), 등록일(DEFAULT)




CREATE TABLE 과목
(
과목번호 CHAR(5) PRIMARY KEY,
과목명 VARCHAR(20) NOT NULL,
학점 INT NOT NULL CHECK(학점 BETWEEN 2 AND 4),
구분 VARCHAR(20) CHECK(구분 IN ('전공',  '교양', '일반'))
);


CREATE TABLE 수강_1
(
수강년도 CHAR(4) NOT NULL,
수강학기 VARCHAR(20) NOT NULL CHECK(수강학기 IN ('1학기', '2학기', '여름학기', '겨울학기')),
학번 CHAR(5) NOT NULL,
과목번호 CHAR(5) NOT NULL,
성적  NUMERIC(3,1) CHECK(성적 BETWEEN 0 AND 4.5),
PRIMARY KEY(수강년도, 수강학기, 학번, 과목번호),
FOREIGN KEY (학번) REFERENCES 학생(학번),
FOREIGN KEY (과목번호) REFERENCES 과목(과목번호)
);


CREATE TABLE 수강_2
(
수강번호 INT PRIMARY KEY AUTO_INCREMENT,
수강년도 CHAR(4) NOT NULL,
수강학기 VARCHAR(20) NOT NULL CHECK(수강학기 IN ('1학기', '2학기', '여름학기', '겨울학기')),
학번 CHAR(5) NOT NULL,
과목번호 CHAR(5) NOT NULL,
성적  NUMERIC(3,1) CHECK(성적 BETWEEN 0 AND 4.5),
FOREIGN KEY (학번) REFERENCES 학생(학번),
FOREIGN KEY (과목번호) REFERENCES 과목(과목번호)
);


INSERT INTO 학과
VALUES ('AA', '컴퓨터공학과', '배경민');
INSERT INTO 학과
VALUES ('BB', '소프트웨어학과', '김남준');
INSERT INTO 학과
VALUES ('CC', '디자인융합학과', '박선영');
SELECT * FROM 학과;


INSERT INTO 학생(학번, 이름, 생일, 학과번호)
VALUE ('S0001', '이윤주', '2020-01-30', 'AA');
INSERT INTO 학생(학번, 이름, 생일, 학과번호)
VALUE ('S0002', '이승은', '2021-02-23', 'BB');
INSERT INTO 학생(학번, 이름, 생일, 학과번호)
VALUE ('S0003', '백재용', '2018-03-31', 'CC');
SELECT * FROM 학생;


INSERT INTO 과목(과목번호, 과목명, 구분, 학점)
VALUE ('C0001', '데이터베이스실습', '전공', 3);
INSERT INTO 과목(과목번호, 과목명, 구분, 학점)
VALUE ('C0002', '데이터베이스 설계와 구축', '전공', 3);
INSERT INTO 과목(과목번호, 과목명, 구분, 학점)
VALUE ('C0003', '데이터 분석', '전공', 3);
SELECT * FROM 과목;


INSERT INTO 수강_1(수강년도, 수강학기, 학번, 과목번호, 성적)
VALUE('2023', '1학기', 'S0001', 'C0001', 4.3);
INSERT INTO 수강_1(수강년도, 수강학기, 학번, 과목번호, 성적)
VALUE('2023', '1학기', 'S0001', 'C0002', 4.4);
INSERT INTO 수강_1(수강년도, 수강학기, 학번, 과목번호, 성적)
VALUE('2023', '1학기', 'S0002', 'C0002', 4.3);
SELECT * FROM 수강_1;


INSERT INTO 수강_2(수강년도, 수강학기, 학번, 과목번호, 성적)
VALUE('2023', '1학기', 'S0001', 'C0001', 4.3);
INSERT INTO 수강_2(수강년도, 수강학기, 학번, 과목번호, 성적)
VALUE('2023', '1학기', 'S0001', 'C0001', 4.5);
SELECT * FROM 수강_2;


-- 제약조건의 추가
ALTER TABLE 학생 ADD CONSTRAINT CHECK(학번 LIKE 'S%');
-- 학생 테이블에 설정되어 있는 제약조건 명세를 확인하는방법
SELECT *
FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS
WHERE CONSTRAINT_SCHEMA = '한빛학사'
AND TABLE_NAME = '학생';

-- 제약조건의 삭제
ALTER TABLE 학생 DROP CONSTRAINT 연락처;

ALTER TABLE 학생 DROP CONSTRAINT 학생_chk_1;
ALTER TABLE 학생 DROP CONSTRAINT 학생_chk_2;
ALTER TABLE 학생 ADD CHECK (학번 LIKE 'S%');

CREATE TABLE 학생_2
(
  학번       CHAR(5),               -- 기본키
  이름       VARCHAR(20)   NOT NULL,                  -- NOT NULL
  생일       DATE          NOT NULL,                  -- NOT NULL
  연락처     VARCHAR(20),                    -- 유일 제약
  학과번호   CHAR(2),  -- 외래키
  성별       CHAR(1),
  등록일     DATE          DEFAULT(CURDATE()),         -- 기본값
  PRIMARY KEY(학번),
  CONSTRAINT UK_학생2_연락처 UNIQUE(연락처),
  CONSTRAINT CK_학생2_성별 CHECK(성별 IN ('남','여')),
   CONSTRAINT CK_학생2_학과번호 FOREIGN KEY (학과번호) REFERENCES 학과(학과번호)
);

-- 학생 테이블에 설정되어 있는 제약조건 명세를 확인하는방법
SELECT *
FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS
WHERE CONSTRAINT_SCHEMA = '한빛학사'
AND TABLE_NAME = '학생_2';


CREATE TABLE 수강평가
(
평가순번 INT PRIMARY KEY AUTO_INCREMENT,
학번 CHAR(5) NOT NULL,
과목번호 CHAR(5) NOT NULL,
평점 INT CHECK(평점 BETWEEN 0 AND 5),
과목평가 VARCHAR(500),
평가일시 DATETIME DEFAULT CURRENT_TIMESTAMP,
FOREIGN KEY (학번) REFERENCES 학생(학번),
FOREIGN KEY (과목번호) REFERENCES 과목(과목번호) ON DELETE CASCADE
);


INSERT INTO 수강평가(학번, 과목번호, 평점, 과목평가)
VALUE('S0001', 'C0001', 5, 'SQL학습에 도움이 되었습니다.'),
		  ('S0001', 'C0003', 5, 'SQL 활용을 배워서 좋았습니다.'),
          ('S0002', 'C0003', 5, '데이터 분석에 관심이 생겼습니다..'),
          ('S0003', 'C0003', 5, '머신러닝과 시각화 부분이 유용했습니다.');


DELETE FROM 과목 WHERE 과목번호 = 'C0003';
SELECT * FROM 과목;
SELECT * FROM 수강평가;


DELETE FROM 과목 WHERE 과목번호 = 'C0001';