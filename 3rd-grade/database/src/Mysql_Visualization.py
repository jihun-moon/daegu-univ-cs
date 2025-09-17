# pip install pymysql pandas sqlalchemy plotly matplotlib seaborn
import platform
import pandas as pd
import matplotlib.pyplot as plt
import plotly.express as px
from sqlalchemy import create_engine

# ————————————————
# 한글 폰트 설정 (Matplotlib)
def set_korean_font():
    os_name = platform.system()
    if os_name == 'Windows':
        plt.rcParams['font.family'] = 'NanumGothic'
    elif os_name == 'Darwin':
        plt.rcParams['font.family'] = 'AppleGothic'
    else:
        plt.rcParams['font.family'] = 'Malgun Gothic'
    plt.rcParams['axes.unicode_minus'] = False

# ————————————————
# 상수 정의
DB_URL = "mysql+pymysql://root:abcd1234@localhost/한빛무역?charset=utf8mb4"
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

# ————————————————
# 데이터 조회
def fetch_sales_df(db_url: str, query: str) -> pd.DataFrame:
    """
    DB에서 매출 데이터를 조회하여 DataFrame으로 반환합니다.
    """
    engine = create_engine(db_url)
    df = pd.read_sql(query, engine)
    engine.dispose()
    return df

# ————————————————
# Sunburst 차트 그리기
def plot_sunburst(df: pd.DataFrame):
    fig = px.sunburst(
        df,
        path=['지역', '제품군', '제품명'],
        values='매출',
        title='지역·제품군·제품별 매출 Sunburst',
        branchvalues='total'
    )
    fig.update_traces(
        textinfo='label+percent root',
        insidetextorientation='radial',
        hovertemplate=(
            '<b>%{label}</b><br>'
            '매출: %{value:,}원<br>'
            '전체 대비: %{percentRoot:.2%}<br>'
            '상위 대비: %{percentParent:.2%}<extra></extra>'
        )
    )
    fig.show()

# ————————————————
# 지역별 막대 차트 그리기
def plot_bar(df: pd.DataFrame):
    region_df = df.groupby('지역', as_index=False)['매출'].sum()
    fig = px.bar(
        region_df,
        x='지역',
        y='매출',
        title='지역별 매출 합계',
        text='매출'
    )
    fig.update_traces(texttemplate='%{text:,}원', textposition='outside')
    fig.show()

# ————————————————
# 제품군별 파이(도넛) 차트 그리기
def plot_pie(df: pd.DataFrame):
    category_df = df.groupby('제품군', as_index=False)['매출'].sum()
    fig = px.pie(
        category_df,
        names='제품군',
        values='매출',
        title='제품군별 매출 비중',
        hole=0.4
    )
    fig.update_traces(textinfo='label+percent')
    fig.show()

# ————————————————
# 메인 실행 흐름
def main():
    set_korean_font()

    # 1) 데이터 로드
    sales_df = fetch_sales_df(DB_URL, SALES_QUERY)

    # 2) 시각화
    plot_sunburst(sales_df)
    plot_bar(sales_df)
    plot_pie(sales_df)

if __name__ == "__main__":
    main()
