# 🧠 Algorithm – Edit Distance, Merge Sort, Selection Sort, BFS

> ### 3줄 요약
>
>   - **알고리즘 구현**: 동적 계획법, 분할 정복, 선택 정렬, 너비 우선 탐색(BFS) 등 핵심 알고리즘을 Python으로 구현한 실습 모음입니다.
>   - **이론과 실제**: 각 알고리즘의 핵심 로직과 시간 복잡도를 이해하고, 실제 실행 예시와 테스트 스크립트를 통해 검증합니다.
>   - **통합 문서**: 단일 README 파일을 통해 여러 알고리즘 코드의 실행과 검증 방법을 통합 관리합니다.

-----

## 📁 파일 구조

```
algorithm/
├── README.md
├── edit_distance.py       # Edit Distance (Dynamic Programming)
├── merge_sort.py          # Merge Sort (Divide & Conquer)
├── selection_sort.py      # Selection Sort (O(n^2))
├── bfs_shortest.py        # BFS Shortest Path (Unweighted Graph)
└── assets/                # 예제 입출력, 스크린샷(선택)
```

-----

## 🔧 개발 환경

  - **Python**: 3.10+ 권장
  - **가상환경**: `venv` 권장 (외부 의존성 없음)

<!-- end list -->

```bash
# 가상환경 생성 및 활성화
python -m venv .venv
source .venv/bin/activate  # Windows: .venv\Scripts\activate
```

-----

## 1\. 편집 거리 (Edit Distance / Levenshtein)

동적 계획법(Dynamic Programming)을 이용하여 두 문자열 간의 편집 거리(삽입/삭제/교체 비용=1)를 계산합니다.

#### 실행 방법

```bash
# 인자로 두 문자열 전달
python edit_distance.py --a kitten --b sitting

# 파이프를 이용한 표준 입력
echo "kitten sitting" | python edit_distance.py
```

#### 체크리스트

  - [x] `dp[n+1][m+1]` 테이블 초기화
  - [x] 점화식 구현: `dp[i][j] = min(삭제, 삽입, 교체)`
  - [ ] (Optional) 최소 비용 경로 복원 기능
  - [x] 시간 복잡도 `O(nm)`, 공간 복잡도 `O(nm)` 명시

-----

## 2\. 병합 정렬 (Merge Sort)

'분할 정복' 패러다임을 기반으로, 재귀적으로 배열을 분할하고 정렬된 부분 배열을 병합하는 안정 정렬(Stable Sort) 알고리즘입니다.

#### 실행 방법

```bash
python merge_sort.py --arr 5 2 9 1 5 6
```

#### 체크리스트

  - [x] 분할 기준 `m = len(a) // 2` 설정
  - [x] 병합(Merge) 과정에서 안정성(Stable) 유지
  - [x] 시간 복잡도 `O(n log n)`, 공간 복잡도 `O(n)` 명시
  - [ ] (Optional) 랜덤 및 역정렬된 배열에 대한 테스트 케이스 추가

-----

## 3\. 선택 정렬 (Selection Sort)

배열에서 최대(또는 최소) 원소를 찾아 맨 끝(또는 맨 앞)으로 보내는 과정을 반복하는 O(n²) 시간 복잡도의 정렬 알고리즘을 단계별로 출력합니다.

#### 실행 방법

```bash
python selection_sort.py --arr 8 31 48 73 3 65 20 29
```

#### 체크리스트

  - [x] 최댓값(theLargest)을 찾는 함수 구현
  - [x] 교환(Swap) 후 각 단계별 배열 상태 출력
  - [x] 시간 복잡도 `O(n²)`, 제자리 정렬(In-place sort) 특징 명시
  - [ ] (Optional) 비교 및 교환 횟수 카운터 추가

-----

## 4\. BFS 최단 경로 (BFS Shortest Path)

가중치 없는 그래프(Unweighted Graph)에서 너비 우선 탐색(BFS)을 이용하여 시작 정점으로부터 다른 모든 정점까지의 최단 거리(간선 수)를 계산합니다.

#### 실행 방법

```bash
# 정점 6개, 간선 목록, 시작 정점 0
python bfs_shortest.py --n 6 --edges "0-1,0-2,1-3,2-3,3-4,4-5" --start 0
# 예상 출력: dist = [0, 1, 1, 2, 3, 4]
```

#### 체크리스트

  - [x] 인접 리스트(Adjacency List)로 그래프 구성
  - [x] `visited/dist` 배열과 큐(`deque`)를 사용한 BFS 로직 구현
  - [x] 연결되지 않은 정점은 거리 값을 `-1`로 처리
  - [ ] (Optional) 여러 시작점 또는 다중 컴포넌트 그래프 케이스 처리

-----

## 🧠 복잡도 요약

| 알고리즘 | 시간 복잡도 | 공간 복잡도 | 특징 |
| :--- | :--- | :--- | :--- |
| **Edit Distance** | O(nm) | O(nm) | 동적 계획법 (DP) |
| **Merge Sort** | O(n log n) | O(n) | 분할 정복, 안정 정렬 |
| **Selection Sort** | O(n²) | O(1) | 비교 기반, 제자리 정렬 |
| **BFS** | O(V + E) | O(V + E) | 가중치 없는 그래프 최단 경로 |

-----

## 🗺️ 로드맵 (향후 개발 계획)

  - [ ] **Edit Distance**: 연산 비용에 가중치 부여, Damerau-Levenshtein 거리로 확장
  - [ ] **Merge Sort**: 제자리(In-place) 병합 시도, 하이브리드 정렬(Timsort) 개념 도입
  - [ ] **BFS**: 경로 복원(Parent 추적) 기능 추가, 다중 소스(Multi-source) BFS 구현

-----

## 🪪 라이선스

이 프로젝트는 [MIT 라이선스](https://opensource.org/licenses/MIT)를 따릅니다.
