-- 실습문제 1번
SELECT 제품명
FROM 제품
WHERE 단가 = (SELECT MAX(단가)
             FROM 제품
            );
                        
-- 실습문제 2번
SELECT 
    SUM(d.주문수량) AS 주문수량합
FROM 
    주문세부 AS d
WHERE 
    d.제품번호 IN (SELECT p.제품번호
		 FROM 제품 AS p
		 WHERE p.단가 = (SELECT MAX(단가)
				FROM 제품
		               )
	        );
                        
-- 실습문제 3번
SELECT 
    SUM(d.주문수량) AS 주문수량합
FROM 
    주문세부 AS d
WHERE 
    d.제품번호 IN (
        SELECT p.제품번호
        FROM 제품 AS p
        WHERE p.제품명 LIKE '%아이스크림%'
    );


-- 실습문제 4번
SELECT
    YEAR(o.주문일) AS 주문년도,
    COUNT(*)       AS 주문건수
FROM 
    주문 AS o
WHERE 
    o.고객번호 IN (
        SELECT c.고객번호
        FROM 고객 AS c
        WHERE c.도시 = '서울특별시'
    )
GROUP BY 
    YEAR(o.주문일)
ORDER BY 
    YEAR(o.주문일) ASC;


