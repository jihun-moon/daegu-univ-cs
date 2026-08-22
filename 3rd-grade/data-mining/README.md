# 데이터 마이닝 (Data Mining)

3학년 2학기 데이터 마이닝 수업의 실습 노트북 18개입니다. NumPy 와 pandas 로 시작해 시각화와 EDA 를 거쳐 선형회귀, 로지스틱회귀, 결정트리까지 갔습니다. 수업은 Google Colab 에서 진행했습니다.

## 폴더 구조

- `labs/` 진행 순서대로 번호를 붙인 노트북
- `data/` 실습에 쓴 CSV 10개
- `outputs/` 노트북에서 저장한 결과물 2개

## 실행하기 전에

노트북 안의 읽기 경로가 전부 `./drive/MyDrive/<파일명>.csv` 입니다. Colab 에서 구글 드라이브를 마운트해 쓴 흔적이라 그대로는 로컬에서 안 돌아갑니다. 파일 이름은 `data/` 안의 것과 같으니 경로만 `../data/` 로 바꾸면 됩니다.

결정트리를 그림으로 뽑는 노트북(12, 13, 14번)은 `graphviz` 파이썬 패키지 외에 Graphviz 본체도 설치되어 있어야 합니다.

## 노트북

| 노트북 | 다룬 것 | 데이터 |
| --- | --- | --- |
| [0](labs/0_환경설정과이미지출력.ipynb) | 드라이브 마운트 확인, `matplotlib.image` 로 이미지 읽어 출력 | `du.png` (저장소에는 없음) |
| [1](labs/1_Numpy_배열생성과조작.ipynb) | `arange`·`linspace`·`zeros`·`eye`·`random`, `shape`/`ndim`/`dtype`, `reshape`, `argmax`/`argmin` | 없음 |
| [2](labs/2_Numpy_인덱싱과Pandas기초.ipynb) | 2차원 인덱싱과 슬라이싱, 불리언 인덱싱, 행렬곱(`np.dot`, `@`), Series 로 막대·원 그래프 | 없음 |
| [3](labs/3_Pandas_Series생성과연산.ipynb) | Series 를 리스트·라벨·ndarray·딕셔너리로 만들기, 인덱스가 어긋난 Series 를 더했을 때 NaN 이 생기는 것 | 없음 |
| [4](labs/4_Pandas_DataFrame데이터처리.ipynb) | DataFrame 생성, 열 추가와 삭제, `loc`/`iloc`, 조건 필터, `dropna`/`fillna`, `groupby` | 없음 |
| [5](labs/5_Pandas와_Matplotlib_기초.ipynb) | `read_html` 로 웹 페이지 표 읽기, `read_csv`, 정렬과 조건 조회, matplotlib 기본 플롯 | `Salaries.csv`, `2020-2025.csv` |
| [6](labs/6_Scikit-learn과_Seaborn_기초.ipynb) | `subplot`·`savefig`, MNIST(`fetch_openml`) 손글씨 출력, seaborn `displot`/`relplot`/`pairplot` | OpenML MNIST, seaborn tips |
| [7](labs/7_탐색적_데이터_분석%28EDA%29_기초.ipynb) | tips·iris 로 boxplot·swarmplot·violinplot·pairplot, 타이타닉 생존자를 등급·승선항으로 나눠 보기 | seaborn tips·iris, `Titanic_train.csv` |
| [8](labs/8_슈퍼마켓_매출_데이터_분석.ipynb) | 지점별 매출 합계, 시간대별 추이, 결제수단 비율 | `supermarket_sales.csv` |
| [9](labs/9_단순선형회귀_캘리포니아_주택가격.ipynb) | 캘리포니아 주택가격을 `MedInc` 한 변수로 단순선형회귀, `np.newaxis` 로 차원 맞추기 | sklearn California Housing |
| [10](labs/10_라리가_선수_골예측_선형회귀.ipynb) | 슛 수로 골 수 예측(단순), 포지션·출전시간·패스 등 7개 변수로 다중회귀 | `laliga.csv` |
| [11](labs/11_보강수업_선형회귀_%28날씨%29로지스틱회귀.ipynb) | 보강수업. 습도로 기온 예측(단순·다중), iris·유방암 로지스틱회귀, 타이타닉 생존 예측, 강수 형태 분류 | `weatherHistory.csv`, `Titanic_train.csv` |
| [12](labs/12_Decision_Tree_Iris.ipynb) | iris 결정트리 분류, `plot_tree` 와 graphviz 로 트리 그리기 | sklearn iris |
| [13](labs/13_Weather_Decision_Tree_Regression.ipynb) | 기상 관측값 7개로 체감온도 예측. 결정트리 회귀, `max_depth=3` | `weatherHistory.csv` |
| [14](labs/14_Wine_Clf_and_Car_Reg.ipynb) | 와인 종류 분류, 자동차 구매액 회귀. 둘 다 결정트리 | `winequalityN.csv`, `Car_Purchasing_Data.csv` |
| [15](labs/15_.ipynb) | 교재 11장 전처리 부분. 강의만 듣고 코드 실습은 안 한 주차라 셀 하나에 메모만 있습니다 | 없음 |
| [16](labs/16_Simple_vs_Multiple_Regression_GPA.ipynb) | 단순회귀와 다중회귀 비교. 주당 공부시간 하나로 GPA 예측한 것과 나머지 변수를 다 넣은 것 | `Student_performance_data _.csv` |
| [17](labs/17_.ipynb) | 멜버른 집값. `get_dummies` 로 원핫 인코딩한 뒤 LinearRegression·Lasso·Ridge·ElasticNet 비교 | `MELBOURNE_HOUSE_PRICES_LESS.csv` |

15번과 17번은 파일 이름에 제목을 못 붙인 채로 남아 있습니다. 8번 노트북 끝에 "중간고사 범위 끝" 이라고 적어 뒀는데, 그 뒤부터가 회귀와 결정트리입니다.

## 실습하면서

수식을 보는 것보다 아는 값을 직접 넣어 보는 쪽이 이해가 빨랐습니다. 11번 타이타닉 로지스틱회귀에서는 잭과 로즈의 성별, 나이, 객실등급, 승선항을 넣어 생존 확률을 뽑았고, 같은 노트북 뒤쪽 강수 형태 분류에서는 대구대, 포항, 서울, 대구의 기온과 습도를 넣어 봤습니다. 10번에서도 라키티치, 이강인, 라모스, 벤제마의 시즌 기록으로 골 수를 예측했습니다.

17번에서는 Lasso 와 Ridge 셀을 앞의 LinearRegression 셀에서 복사해 만들다가 변수명 `reg` 를 그대로 둔 곳이 있었습니다. 지금은 `lasso`, `ridge` 로 고쳤고 그 줄에 `# 수정!` 주석을 남겨 뒀습니다.

## 결과물

- `outputs/test.jpg` 6번 노트북에서 `plt.savefig(..., dpi=300)` 으로 저장한 그림
- `outputs/iris.pdf` 12번 노트북에서 graphviz `render("iris")` 로 뽑은 결정트리

## 수업 노트

정리한 개념은 노션에 있습니다. [데이터 마이닝 노트](https://www.notion.so/c81036c9f1e74459affcede126206228?source=copy_link)
