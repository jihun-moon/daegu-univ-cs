-- 1번

SELECT 담당자명

FROM 고객
WHERE 담당자명 LIKE '%정%';


-- 2번

SELECT *

FROM 주문
WHERE YEAR(주문일) = '2020' AND QUARTER(주문일) = '2';


-- 3번

SELECT 제품번호,
	   제품명,
       재고,
       CASE 
			WHEN 재고 >= 100 THEN '과다재고'
			WHEN 재고 >= 10 THEN '적정'
            ELSE '재고부족'
       END AS 재고구분
FROM 제품;


-- 4번 DATEDIFF 와 TIMESTAMPDIFF 괄호 안에 넣는곳 헷갈리지 않기!

SELECT 이름,
	   사원번호,
       직위,
       입사일,
       DATEDIFF(NOW(), 입사일) AS 입사일수,
       TIMESTAMPDIFF(MONTH, 입사일, NOW()) AS 입사개월수 -- 월(month) 차이
        
FROM 사원
WHERE TIMESTAMPDIFF(MONTH, 입사일, NOW()) > 40;