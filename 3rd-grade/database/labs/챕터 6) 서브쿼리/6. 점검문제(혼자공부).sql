-- 점검문제1
-- 상관서브쿼리
-- '배재용' 사원의 부서명을 보이시오.
SELECT ( SELECT 부서명
			   FROM 부서 AS d
			   WHERE d.부서번호 = e.부서번호
			) AS 부서명
FROM 사원 AS e
WHERE 이름 = '배재용';

-- WHERE 절에서의 서브쿼리
SELECT 부서명
FROM 부서
WHERE 부서번호 = (SELECT 부서번호
							  FROM 사원
                              WHERE 이름 = '배재용'
							);
                            
                            
-- 점검문제2 
-- 한 번도 주문한 적이 없는 제품의 정보를 보이시오.
-- 상관서브쿼리
SELECT *,
			단가 * 재고 AS 재고금액
FROM 제품 AS p
WHERE NOT EXISTS (
								SELECT *
                                FROM 주문세부 AS d
                                WHERE d.제품번호 = p.제품번호
								);
                                
                                
-- 외부 조인
SELECT 제품.*,
			제품.단가 * 제품.재고 AS 재고금액
FROM 제품
LEFT OUTER JOIN 주문세부
ON 제품.제품번호 = 주문세부. 제품번호           
WHERE 주문세부.제품번호 IS NULL;        


-- 점검문제3
SELECT 담당자명,
			고객회사명,
            주문건수,
            최초주문일,
            최종주문일
FROM 고객,
				(
                SELECT 고객번호,
							COUNT(*) AS 주문건수,
                            MIN(주문일) AS 최초주문일,
                            MAX(주문일) AS 최종주문일      
				FROM 주문
                GROUP BY 고객번호
                ) AS 주문요약
WHERE 고객.고객번호 =  주문요약.고객번호;