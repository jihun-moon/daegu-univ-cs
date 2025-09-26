
-- 1번
SELECT 제품번호,
	   SUM(주문수량) AS 주문수량합,
       SUM(단가 * 주문수량) AS 주문금액합
FROM 주문세부
GROUP BY 제품번호
ORDER BY 제품번호; -- 정렬되게함. 이거 없어도 1번 문제에 조건에 어긋나지않음.

-- 2번 ★★
SELECT 주문번호,
	   GROUP_CONCAT(제품번호) AS 제품번호목록,
       SUM(단가 * 주문수량) AS 주문금액합
FROM 주문세부
GROUP BY 주문번호;

-- 3번
SELECT YEAR(주문일) AS 주문년도,
	   고객번호,
       COUNT(*) AS 주문건수
FROM 주문
WHERE YEAR(주문일) = '2021'
GROUP BY 주문년도, 고객번호
-- HAVING 주문년도 = '2021'
ORDER BY 주문건수 DESC
LIMIT 3;

-- 4번
SELECT 직위,
	   COUNT(*) AS 사원수,
       GROUP_CONCAT(이름) AS 사원이름목록
FROM 사원
GROUP BY 직위
ORDER BY 직위; -- 정렬되게함. 이거 없어도 4번 문제에 조건에 어긋나지않음.