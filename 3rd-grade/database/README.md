# Database — Course Summary

관계형 데이터 모델과 SQL로 스키마를 설계하고, 질의 최적화·집계·리포팅까지 일관된 파이프라인을 실습했습니다. ERD 정규화와 인덱스 설계를 통해 읽기/쓰기 균형을 잡고, Python(Pandas) 연동으로 시각화 결과를 산출했습니다.

- DBMS: MySQL
- Language: SQL, Python
- Topics: ERD·정규화, 인덱스·실행계획, 윈도우 함수, 데이터 파이프라인

## 📸 Screenshots
| ERD | 매출 분석(제품군) |
| --- | --- |
| ![ERD](assets/db-erd.png) | ![Sales by Category](assets/sales-by-category.png) |

assets 경로·파일명은 레포에 맞게 변경하세요.

## What I Learned
- Schema Design: 정규화, PK/UK, FK·제약, 관계 무결성
- Query Patterns: JOIN·GROUP BY·HAVING, CTE/서브쿼리
- Window Functions: 누계, 랭킹, 이동 평균
- Index & EXPLAIN: 커버링 인덱스, 범위 스캔 유도, 계획 해석
- Python 연동: PyMySQL로 적재, Pandas 집계, Plotly 시각화

## Example (SQL)
```sql
-- 지역·제품군 월 매출, 누계 및 지역 내 Top-N
WITH monthly AS (
  SELECT  r.region,
          p.category,
          DATE_FORMAT(o.order_date, '%Y-%m') AS ym,
          SUM(o.amount) AS revenue
  FROM orders o
  JOIN products p ON p.id = o.product_id
  JOIN regions  r ON r.id = o.region_id
  GROUP BY r.region, p.category, ym
)
SELECT  region, category, ym, revenue,
        SUM(revenue) OVER (PARTITION BY region, category ORDER BY ym) AS cum_rev,
        RANK() OVER (PARTITION BY region, ym ORDER BY revenue DESC)      AS rk_in_region
FROM monthly
WHERE ym BETWEEN '2025-01' AND '2025-06'
ORDER BY region, category, ym;
```

## Troubleshooting
- N+1 조인 지연 → 사전 집계 CTE + 필요한 컬럼만 SELECT로 I/O 감소
- LIKE '%keyword' 인덱스 미사용 → 접두 검색 재설계 + 적절한 인덱스
- GROUP BY 오차 → NULL 규칙 합의, COALESCE 적용으로 집계 일관성 확보

## Checklist
- [ ] PK/UK, FK, ON DELETE/UPDATE 정책 정의
- [ ] SELECT * 금지, 필요한 컬럼만 명시
- [ ] EXPLAIN으로 인덱스/계획 검증
- [ ] 트랜잭션 경계·격리 수준 명시
- [ ] 리포트 쿼리와 운영 트랜잭션 분리

## Python Pipeline (optional)
```python
# pip install PyMySQL pandas plotly
import pymysql, pandas as pd, plotly.express as px

conn = pymysql.connect(host="localhost", user="user", password="pw", db="hanbit")
df = pd.read_sql("SELECT region, category, ym, revenue FROM monthly_view", conn)
fig = px.bar(df, x="ym", y="revenue", color="category", facet_col="region")
fig.write_image("assets/sales-by-region.png")
```

## Folder Structure
```
/assets/                # ERD/차트 이미지
/docs/                  # 발표 자료(PPT/PDF)
/src/                   # SQL 스크립트, Python 파이프라인
README.md
```

## How to Reproduce
1. Create Schema  
   - DDL 실행 후 인덱스/제약 설정
2. Load Sample Data  
   - CSV 또는 스크립트로 적재
3. Run Queries  
   - 01_erd.sql → 02_transform.sql → 03_reporting.sql
4. (선택) Python 시각화  
   - src/pipeline.py 실행 → assets에 결과 이미지 생성

## Links
- Notion 정리: Database 페이지[[1]](https://www.notion.so/7475d01faf314341bd4895dded72be9a)
- GitHub 실습 레포: 프로젝트 링크 기입

## License
MIT 또는 과목 가이드에 맞는 라이선스 표기
