# pip install pymysql pandas
import pymysql
import pandas as pd

# ————————————————
# DB 연결 정보
DB_CONFIG = {
    'host': 'localhost',
    'user': 'root',
    'password': 'abcd1234',
    'db': '한빛무역',
    'charset': 'utf8mb4',
    'cursorclass': pymysql.cursors.DictCursor
}

# ————————————————
# 지역·제품군·제품별 매출 집계 쿼리
SALES_QUERY = """
SELECT 고객.지역 AS 지역,
        CASE
            WHEN 제품.제품명 LIKE '%%소스%%' OR 제품.제품명 LIKE '%%식초%%' OR 제품.제품명 LIKE '%%간장%%' OR 제품.제품명 LIKE '%%잼%%' OR 제품.제품명 LIKE '%%허니헬시 마말레이드%%' THEN '소스류'
            WHEN 제품.제품명 LIKE '%%콜라%%' OR 제품.제품명 LIKE '%%탄산%%' OR 제품.제품명 LIKE '%%맥주%%' OR 제품.제품명 LIKE '%%로하이 브랜디%%' OR 제품.제품명 LIKE '%%커피%%' OR 제품.제품명 LIKE '%%시럽%%' OR 제품.제품명 LIKE '%%주스%%' OR 제품.제품명 LIKE '%%포도주%%' OR 제품.제품명 LIKE '%%초콜릿 드링크%%' OR 제품.제품명 LIKE '%%칵테일%%' OR 제품.제품명 LIKE '%%셰이크%%' OR 제품.제품명 LIKE '%%파스 페이스 티%%' THEN '음료류'
            WHEN 제품.제품명 LIKE '%%아이스크림%%' OR 제품.제품명 LIKE '%%젤리%%' OR 제품.제품명 LIKE '%%쿠키%%' OR 제품.제품명 LIKE '%%파이%%' OR 제품.제품명 LIKE '%%캔디%%' THEN '디저트류'
            WHEN 제품.제품명 LIKE '%%라면%%' OR 제품.제품명 LIKE '%%카레%%' OR 제품.제품명 LIKE '%%시리얼%%' OR 제품.제품명 LIKE '%%뉴트리 콘 플레이크%%' OR 제품.제품명 LIKE '%%연어알 조림%%' THEN '즉석식품'
            WHEN 제품.제품명 LIKE '%%소시지%%' OR 제품.제품명 LIKE '%%훈제육%%' OR 제품.제품명 LIKE '%%멸치 가루%%' OR 제품.제품명 LIKE '%%훈제 통닭%%' OR 제품.제품명 LIKE '%%어묵%%' OR 제품.제품명 LIKE '%%햄%%' OR 제품.제품명 LIKE '%%통조림%%' OR 제품.제품명 LIKE '%%앨리스 포장육%%' THEN '가공식품'
            WHEN 제품.제품명 LIKE '%%분유%%' OR 제품.제품명 LIKE '%%치즈%%' OR 제품.제품명 LIKE '%%버터%%' OR 제품.제품명 LIKE '%%우유%%' OR 제품.제품명 LIKE '%%연유%%' THEN '유제품'
            WHEN 제품.제품명 LIKE '%%칠면조%%' OR 제품.제품명 LIKE '%%북미산 상등육 쇠고기%%' THEN '육류'
            WHEN 제품.제품명 LIKE '%%상어알%%' OR 제품.제품명 LIKE '%%포장 참치%%' OR 제품.제품명 LIKE '%%버뮤다 포장 문어%%' THEN '수산물'
            WHEN 제품.제품명 LIKE '%%헬시 건과%%' THEN '건강식품'
            WHEN 제품.제품명 LIKE '%%옥수수%%' OR 제품.제품명 LIKE '%%통밀가루%%' OR 제품.제품명 LIKE '%%필로 믹스%%' OR 제품.제품명 LIKE '%%옥수수 가루%%' THEN '식재료'
            WHEN 제품.제품명 LIKE '%%김%%' OR 제품.제품명 LIKE '%%태평양 포장 파래%%' OR 제품.제품명 LIKE '%%다시마%%' OR 제품.제품명 LIKE '%%미역%%' OR 제품.제품명 LIKE '%%해조류%%' THEN '해조류'
            ELSE '기타'
        END AS 제품군,
        제품.제품명 AS 제품명,
        SUM(주문세부.주문수량 * 주문세부.단가) AS 매출
FROM 고객
    JOIN 주문     ON 고객.고객번호 = 주문.고객번호
    JOIN 주문세부 ON 주문.주문번호 = 주문세부.주문번호
    JOIN 제품     ON 주문세부.제품번호 = 제품.제품번호
    WHERE 고객.지역 != ''
    GROUP BY 고객.지역, 제품군, 제품.제품명
    ORDER BY 지역, 제품군, 매출 DESC;
"""

def fetch_sales_data(db_config: dict, query: str) -> pd.DataFrame:
    """
    MySQL에서 매출 데이터를 조회해 DataFrame으로 반환합니다.
    """
    conn = pymysql.connect(**db_config)
    try:
        with conn.cursor() as cursor:
            cursor.execute(query)
            rows = cursor.fetchall()
    finally:
        conn.close()
    return pd.DataFrame(rows)

def main():
    # 1) 데이터 조회
    sales_df = fetch_sales_data(DB_CONFIG, SALES_QUERY)
    
    # 2) 결과 확인 (간단 출력)
    print("조회된 행 수:", len(sales_df))
    print(sales_df.head())

    # 3) 분석 예시: 지역별 매출 합계
    region_summary = (
        sales_df
        .groupby('지역', as_index=False)['매출']
        .sum()
        .sort_values('매출', ascending=False)
    )
    print("\n== 지역별 매출 합계 ==")
    print(region_summary)

    # 4) 분석 예시: 제품군별 매출 비중
    category_summary = (
        sales_df
        .groupby('제품군', as_index=False)['매출']
        .sum()
        .sort_values('매출', ascending=False)
    )
    category_summary['비중(%)'] = (category_summary['매출'] / category_summary['매출'].sum() * 100).round(1)
    print("\n== 제품군별 매출 비중 ==")
    print(category_summary)

if __name__ == "__main__":
    main()
