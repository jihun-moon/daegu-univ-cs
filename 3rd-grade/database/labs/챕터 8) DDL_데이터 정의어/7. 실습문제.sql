-- 실습문제1
CREATE TABLE 영화 (
    영화번호 CHAR(5)           PRIMARY KEY,
    타이틀   VARCHAR(100)      NOT NULL,
    장르     VARCHAR(20)
              CHECK (장르 IN ('코미디','드라마','다큐','SF','액션','역사','기타')),
    배우     VARCHAR(100)      NOT NULL,
    감독     VARCHAR(50)       NOT NULL,
    제작사   VARCHAR(50)       NOT NULL,
    개봉일   DATE,
    등록일   DATE              DEFAULT (CURDATE())
);

-- 실습문제2
CREATE TABLE 평점관리 (
    번호         INT            AUTO_INCREMENT PRIMARY KEY, 
    평가자닉네임 VARCHAR(50)    NOT NULL,
    영화번호     CHAR(5)        NOT NULL,
    평점         TINYINT        NOT NULL CHECK (평점 BETWEEN 1 AND 5),
    평가         VARCHAR(2000)  NOT NULL,
    등록일       DATE           DEFAULT (CURDATE()),
    FOREIGN KEY (영화번호) REFERENCES 영화(영화번호)
);

