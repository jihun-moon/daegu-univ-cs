
-- 1번

SELECT *

FROM 제품
WHERE 제품명 LIKE '%주스%';


-- 2번

SELECT *
FROM 제품
WHERE 5000 <= 단가 <= 10000 AND 제품명 LIKE '%주스%';


-- 3번

SELECT *
FROM 제품
WHERE 제품번호 IN (1, 2, 4, 7, 11, 20);


-- 4번

SELECT 제품번호,
	   제품명,
	   단가,
       재고,
       단가*재고 AS 재고금액
       
FROM 제품
ORDER BY 단가*재고 DESC
LIMIT 10;


       