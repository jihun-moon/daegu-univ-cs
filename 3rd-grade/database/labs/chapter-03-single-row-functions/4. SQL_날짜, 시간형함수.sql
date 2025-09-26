SELECT 
    NOW(), -- 결과: 2025-03-28 HH:MM:SS (현재 날짜와 시간)
    YEAR(NOW()), -- 결과: 2025
    QUARTER(NOW()), -- 결과: 1  (1분기: 1~3월)
    DAY(NOW()), -- 결과: 28
    DATEDIFF(NOW(), '2025-01-01'), -- 결과: 86 (2025-03-28와 2025-01-01 사이의 일수 차이)
    TIMESTAMPDIFF(MONTH, NOW(), '2025-01-01'), -- 결과: -2 ('2025-01-01'이 현재보다 2개월 전)
    ADDDATE(NOW(), INTERVAL 100 DAY), -- 결과: 2025-07-06 (현재로부터 100일 후)
    ADDDATE(NOW(), INTERVAL 100 MONTH), -- 결과: 2033-07-28 (현재로부터 100개월 후)
    SUBDATE(NOW(), INTERVAL 100 DAY), -- 결과: 2024-12-18 (현재로부터 100일 전)
    SUBDATE(NOW(), INTERVAL 100 MONTH), -- 결과: 2016-11-28 (현재로부터 100개월 전)
    LAST_DAY(NOW()), -- 결과: 2025-03-31 (현재 달의 마지막 날)
    MONTHNAME(NOW()), -- 결과: March
    WEEKDAY(NOW()); -- 결과: 4 (월요일=0, 화요일=1, …, 금요일=4)
