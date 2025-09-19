# 📊 데이터 마이닝 (Data Mining)

![Python](https://img.shields.io/badge/python-3670A0?style=for-the-badge&logo=python&logoColor=ffdd54) ![Pandas](https://img.shields.io/badge/pandas-%23150458.svg?style=for-the-badge&logo=pandas&logoColor=white) ![scikit-learn](https://img.shields.io/badge/scikit--learn-%23F7931E.svg?style=for-the-badge&logo=scikit-learn&logoColor=white)

---

### **[과목 정보]**
- **수강:** 3학년 2학기
- **핵심 기술:** `Python`, `Pandas`, `Scikit-learn`
- **핵심 역량:** `데이터 전처리`, `분류/군집 모델링`, `연관 규칙 분석`, `추천 시스템`

---

## 📖 과목 개요 (Overview)

대규모 데이터 속에서 의미 있는 패턴과 인사이트를 발견하는 핵심 기법들을 배우는 과목입니다. 데이터 정제 및 전처리부터 시작하여, **분류(Classification), 군집화(Clustering), 연관 규칙 분석(Association Rule)** 등 주요 데이터 마이닝 알고리즘의 원리를 학습하고, 이를 파이썬 라이브러리를 이용해 실제 데이터에 적용하는 실습을 진행했습니다. 이 과목을 통해 데이터를 기반으로 미래를 예측하거나 숨겨진 관계를 찾아내는 데이터 분석가로서의 기본 역량을 다졌습니다.

## ✏️ 핵심 역량 (Core Competencies)

-   **데이터 전처리:** **Pandas** 라이브러리를 이용해 결측치, 이상치를 처리하고 데이터를 분석에 적합한 형태로 가공하는 능력.
-   **분류 모델링:** **의사결정트리(Decision Tree), 서포트 벡터 머신(SVM)** 등 지도학습 기반의 분류 알고리즘을 이해하고 **Scikit-learn**으로 구현하는 능력.
-   **군집 분석:** **K-평균(K-Means)** 알고리즘 등 비지도학습 기반의 군집화 기법을 사용하여 데이터의 숨겨진 그룹을 발견하는 능력.
-   **연관 규칙 탐사:** **Apriori** 알고리즘을 이용해 '장바구니 분석'과 같이 항목들 간의 연관성을 찾아내는 능력.

---

## 💡 대표 프로젝트: MovieLens 데이터를 활용한 영화 추천 시스템 구축

> **프로젝트 목표:** MovieLens 데이터셋의 사용자별 영화 평점 데이터를 기반으로, 협업 필터링(Collaborative Filtering) 알고리즘을 이용한 개인화 영화 추천 모델을 구축했습니다.

#### 📈 분석 과정
1.  **데이터 탐색 및 전처리 (EDA & Preprocessing):** Pandas를 활용해 데이터의 분포를 확인하고, 분석에 적합하도록 평점 데이터를 정제했습니다.
2.  **사용자-아이템 행렬 생성:** 사용자와 영화를 기준으로 한 평점 행렬(User-Item Matrix)을 생성하여 알고리즘에 적용할 수 있는 형태로 변환했습니다.
3.  **유사도 측정 및 협업 필터링 구현:** Scikit-learn의 `cosine_similarity`를 사용하여 사용자 간 또는 아이템 간 유사도를 측정하고, 이를 기반으로 특정 사용자가 좋아할 만한 영화를 예측하는 협업 필터링 로직을 구현했습니다.
4.  **개인화 추천 리스트 생성:** 구현된 모델을 통해 특정 사용자에게 평점 순으로 상위 N개의 영화를 추천하는 기능을 완성했습니다.

#### 🎬 주요 결과물
- [데이터 분포를 보여주는 시각화 자료 (예: 평점 분포 히스토그램) 이미지 삽입]
- [특정 유저에 대한 최종 추천 영화 리스트 결과 (표 또는 캡처) 삽입]

#### 🔗 관련 링크
- **Source Code:** `[분석 코드가 담긴 Jupyter Notebook 또는 폴더 링크]`

---
> ↩️ **[전체 학습 로드맵으로 돌아가기](../../README.md)**
