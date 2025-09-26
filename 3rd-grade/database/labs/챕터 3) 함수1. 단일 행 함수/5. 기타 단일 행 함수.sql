SELECT 
    CAST('-1' AS SIGNED),   -- 결과: -1 (SIGNED로 변환 시 음수 그대로 반환)
    CAST(2 AS CHAR(1));     -- 결과: '2' (숫자 2가 길이 1의 문자열로 변환됨)
