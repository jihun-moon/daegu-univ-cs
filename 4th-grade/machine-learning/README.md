# 머신러닝

4학년 머신러닝 수업의 실습 기록입니다. 주차별 실습을 노트북 하나에 이어서 정리했고, scikit-learn 으로 회귀부터 군집화까지 다뤘습니다.

## 폴더 구성

| 경로 | 내용 |
| --- | --- |
| [`labs/1~14주차_결과물.ipynb`](labs/1~14주차_결과물.ipynb) | 실습 노트북. 주차별 셀이 순서대로 이어집니다 |
| [`data/`](data) | 실습에 쓴 데이터 파일 4개 |
| [`lectures/`](lectures) | 주차별 실습 예제 이미지입니다 |

데이터 파일은 각각 다음 주차에서 씁니다.

- [`diamonds.csv`](data/diamonds.csv) : 4주차 선형 회귀
- [`BankNote_Authentication.csv`](data/BankNote_Authentication.csv) : 6주차 결정 트리
- [`customer_churn_dataset-testing-master.csv`](data/customer_churn_dataset-testing-master.csv) : 13주차 앙상블
- [`land.jpg`](data/land.jpg) : 14주차 K-Means 색상 압축

## 주차별 실습

| 주차 | 주제 | 데이터 | 노트북에 남은 결과 |
| --- | --- | --- | --- |
| 2 | NumPy 마스킹, 브로드캐스팅으로 표준화 | 난수 배열 | 표준화 후 평균이 0으로 수렴하는지 확인 |
| 3 | Matplotlib 임계값 마스킹, Seaborn 펭귄 데이터 | seaborn `penguins` | 종별 산점도와 몸무게 KDE |
| 4 | 선형 회귀 (table, depth, carat 으로 price 예측) | `diamonds.csv` | R² 훈련 0.8538, 테스트 0.8532 |
| 5 | 로지스틱 회귀 (설비 고장 여부) | [UCI AI4I 2020 예지보전 데이터](https://archive.ics.uci.edu/dataset/601/ai4i+2020+predictive+maintenance+dataset) | 테스트 정확도 0.9725 |
| 6 | 결정 트리, 깊이별 성능 비교 | `BankNote_Authentication.csv` | max_depth 3에서 0.909, 5에서 0.967 |
| 7 | SVR 커널 비교 (linear, poly, rbf) | 잡음을 섞은 sin 곡선 | 커널별 예측 곡선 3장 |
| 9 | KNN, k 값 탐색 | `load_breast_cancer` | 최적 k=4, 정확도 0.9649 |
| 10 | SGD 최적화, `partial_fit` 으로 에폭별 손실 추적 | `load_breast_cancer` | `fit()` 0.9737, `partial_fit()` 100에폭 0.9561 |
| (표기 없음) | 스케일러 6종에 따른 KNN 결정 경계 비교 | `load_wine` 의 proline, hue | 스케일러별 결정 경계 6장 |
| 12 | 회귀·분류 평가 지표 | 직접 만든 예시 데이터 | MAE 8.5분, R² 26.8%, 분류 정확도 0.70 |
| 13 | 소프트 보팅 앙상블 | `customer_churn_dataset-testing-master.csv` | LR·KNN·DT·RF·GB·AdaBoost 와 보팅의 정확도 막대그래프 |
| 14 | K-Means 색상 압축 | `land.jpg` | K=3 으로 원본과 압축본 비교 |

노트북은 2주차부터 시작합니다. 주차 표기가 없는 스케일러 비교 셀은 10주차와 12주차 사이에 들어 있고, 8주차는 노트북에도 `lectures/` 에도 없습니다.

## 해 보고 알게 된 것

- 결정 트리의 `max_depth` 를 2에서 5로 올리는 동안 테스트 정확도가 0.88에서 0.967까지 훈련 정확도와 같이 올라갔습니다. 깊이를 늘리면 과적합부터 걱정했는데 이 데이터에서는 5까지 둘 다 좋아졌습니다.
- `partial_fit` 으로 100에폭을 돌리며 손실을 직접 찍어 보니 훈련 손실은 0.0268에서 더 내려가지 않는데 테스트 손실은 계속 조금씩 줄었습니다. 그런데 최종 정확도는 한 번에 `fit()` 을 부른 쪽(0.9737)이 더 높았습니다.
- 12주차 회귀 예제는 MAE 가 8.5분이라 나쁘지 않아 보이는데 R² 는 26.8% 였습니다. 지표 하나만 보고 판단하면 안 된다는 것을 숫자로 확인했습니다.
- 같은 KNN(k=20)에 스케일러만 바꿔 끼웠는데 결정 경계 모양이 전부 달랐습니다.

## 실행

Jupyter 나 Colab 에서 노트북을 열고 실행하면 되며, 5주차 셀은 `ucimlrepo` 로 데이터를 내려받으므로 네트워크가 필요합니다.

실행 전에 두 가지를 확인해 주세요. CSV 를 읽는 셀의 경로가 `/content/drive/MyDrive/...` 로 되어 있어서 이 저장소에서 그대로 돌리려면 `data/` 로 바꿔야 합니다. 그리고 5주차 예측 셀은 `input()` 으로 값을 받으므로 실행하면 입력을 기다립니다.

## 개인 프로젝트와의 연결

여기서 다룬 트리와 부스팅 계열이 CHRONO(주식 퀀트 플랫폼)에서 XGBoost 를 쓰는 바탕이 됐습니다.
