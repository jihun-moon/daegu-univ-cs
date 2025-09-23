# 📊 MySQL × Python 매출 분석 파이프라인

> ### 3줄 요약
>
>   - **데이터 파이프라인**: MySQL(한빛무역 샘플 스키마)에 저장된 데이터를 Python으로 연동하여 분석하고 시각화하는 엔드투엔드(End-to-End) 파이프라인입니다.
>   - **핵심 기술**: 복합 SQL(`JOIN`, `GROUP BY`, `CASE`)로 데이터를 집계하고, `pandas`로 가공한 뒤 `plotly`와 `matplotlib`으로 시각화합니다.
>   - **결과물**: 재현 가능한 분석 스크립트와 대시보드형 차트(막대, 도넛, Sunburst)를 산출합니다.

-----

## 🖼️ 예시 산출물

| 지역별 매출 (막대그래프) | 제품군별 매출 비중 (도넛 차트) |
| :---: | :---: |
| \<img src="assets/sales-by-region.png" alt="Sales by Region" width="400"/\> | \<img src="assets/sales-by-category.png" alt="Sales by Category" width="400"/\> |

  - **ERD**: `assets/db-erd.png`
  - **인터랙티브 대시보드**: `outputs/sunburst.html` (스크립트 실행 후 생성)

-----

## 🧰 개발 환경 및 설정

  - **언어/DB**: Python 3.10+, MySQL 8.x
  - **핵심 라이브러리**: `pymysql`, `pandas`, `matplotlib`, `plotly`
  - **권장**: 가상환경(`venv`) 사용

#### 1\. 환경 설정

```bash
# 가상환경 생성 및 활성화
python -m venv .venv
source .venv/bin/activate  # Windows: .venv\Scripts\activate

# 라이브러리 설치
pip install -U pip
pip install pymysql pandas matplotlib plotly python-dotenv
```

#### 2\. 접속 정보 설정 (`.env` 파일)

프로젝트 루트에 `.env` 파일을 생성하고 아래와 같이 데이터베이스 접속 정보를 입력합니다.

```env
MYSQL_HOST=localhost
MYSQL_PORT=3306
MYSQL_USER=hanbit
MYSQL_PASSWORD=secret
MYSQL_DB=hanbit_trade
```

-----

## 🚀 빠른 실행

### 1\. 기초 연동 및 데이터 확인

`Mysql_Basic.py` 스크립트는 DB 연결을 테스트하고 기본 데이터를 조회하는 역할을 합니다.

```bash
python src/Mysql_Basic.py
```

### 2\. 분석 및 시각화 파이프라인 실행

`Mysql_Visualization.py` 스크립트는 SQL 쿼리로 데이터를 집계하고, `outputs` 폴더에 시각화 결과물을 저장합니다.

```bash
python src/Mysql_Visualization.py --save-dir outputs
```

#### 스크립트 인자 (선택)

```bash
python src/Mysql_Visualization.py \
  --start-date 2023-01-01 \
  --end-date 2023-12-31 \
  --top-n 10 \
  --save-dir outputs
```

  - `--start-date`, `--end-date`: 분석 기간 필터
  - `--top-n`: 상위 카테고리/지역 필터
  - `--save-dir`: 결과 저장 폴더

-----

## 🧩 대표 SQL 쿼리 예시

#### 지역 및 연도별 매출 집계

```sql
SELECT r.region,
       YEAR(o.order_date) AS y,
       SUM(od.quantity * od.unit_price * (1 - od.discount)) AS revenue
  FROM orders o
  JOIN order_details od ON o.order_id = od.order_id
  JOIN customers c ON o.customer_id = c.customer_id
  JOIN regions r ON c.region_id = r.region_id
 GROUP BY r.region, YEAR(o.order_date)
 ORDER BY y, revenue DESC;
```

#### 제품군별 매출 비중

```sql
SELECT p.category AS category,
       SUM(od.quantity * od.unit_price) AS revenue
  FROM order_details od
  JOIN products p ON od.product_id = p.product_id
 GROUP BY p.category
 ORDER BY revenue DESC;
```

-----

## 🐳 (선택) Docker로 MySQL 환경 구성

로컬에 MySQL이 설치되어 있지 않은 경우, Docker를 사용하여 간단하게 테스트 환경을 구성할 수 있습니다.

```bash
docker run --name hanbit-mysql -e MYSQL_ROOT_PASSWORD=root \
-e MYSQL_DATABASE=hanbit_trade -p 3306:3306 -d mysql:8
```

-----

## 🪪 라이선스

이 프로젝트는 [MIT 라이선스](https://opensource.org/licenses/MIT)를 따릅니다.
