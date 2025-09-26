-- 실습문제1
INSERT INTO 고객(고객번호, 담당자명, 고객회사명, 도시)
VALUES('ZZZAA', '한동욱', '자유트레이딩', '서울특별시');

-- 실습문제2
UPDATE 고객
SET 도시 = '부산광역시', 마일리지 = 100, 담당자직위 = '대표이사'
WHERE 고객번호 = 'ZZZAA';

-- 실습문제3
UPDATE 고객 AS c
SET c.마일리지 = (
    SELECT AVG(마일리지)
    FROM 고객
    WHERE 담당자직위 = '대표 이사'
)
WHERE c.고객번호 = 'ZZZAA';


-- 실습문제4
INSERT INTO 사원 (사원번호, 이름, 직위)
VALUES ('E15', '이석진', '수습사원')
ON DUPLICATE KEY UPDATE
    이름 = '이석진',
    직위 = '수습사원';

-- 실습문제5
DELETE FROM 고객
WHERE 고객번호 = 'ZZZAA';

-- 실습문제6
DELETE FROM 사원
WHERE 사원번호 = 'E15';

-- 조회컬럼
SELECT * FROM 고객 WHERE 고객번호= 'ZZZAA';
SELECT * FROM 사원 WHERE 사원번호 = 'E15';