# 🐍 Data Structures – Mini Projects (Stack, Tree, Huffman)

> ### 3줄 요약
>
>   - **핵심 자료구조 구현**: 스택 기반 후위 표기 계산기, 이진 트리 핵심 연산, 허프만 인코더 3종을 Python으로 구현한 실습 모음입니다.
>   - **이론과 실제의 연결**: 자료구조와 알고리즘 이론을 코드로 직접 구현하며 시간 복잡도에 대한 감각을 함께 훈련합니다.
>   - **통합 문서**: 단일 README 파일을 통해 여러 프로젝트의 실행 방법과 테스트 체크리스트를 통합 관리합니다.

-----

## 📁 파일 구조

```
data-structures/
├── README.md
├── postfix-calculator.py        # 후위 표기 계산기
├── binary-tree-operations.py    # 이진 트리 연산
├── huffman_coding.py            # 허프만 인코더
└── assets/                      # 샘플 입출력, 스크린샷
```

-----

## 🔧 개발 환경

  - **Python**: 3.10+ 권장
  - **가상환경**: `venv` 사용 권장
  - **의존성**: 외부 라이브러리 없이 표준 라이브러리만 사용

<!-- end list -->

```bash
# 가상환경 생성 및 활성화
python -m venv .venv
source .venv/bin/activate  # Windows: .venv\Scripts\activate
```

-----

## 1\. 후위 표기 계산기 (`postfix-calculator.py`)

배열/리스트 기반의 스택을 직접 구현하여, 사용자가 입력한 중위 표기식을 후위 표기식으로 변환하고 최종 결과값을 계산합니다.

#### 실행 방법

```bash
# 인자로 수식 전달
python postfix-calculator.py --expr "(3+4)*2"

# 파이프를 이용한 표준 입력
echo "(3+4)*2" | python postfix-calculator.py
```

#### 필수 요건 체크리스트

  - [x] **스택 API**: `push`, `pop`, `peek`, `is_empty` 등 핵심 연산 구현
  - [x] **중위 → 후위 변환**: 연산자 우선순위 및 괄호 처리 로직 구현
  - [x] **후위식 평가**: 스택을 이용한 계산 로직 구현
  - [ ] **예외 처리**: 언더플로우, 잘못된 토큰, 불일치 괄호 등

-----

## 2\. 이진 트리 연산 (`binary-tree-operations.py`)

노드(Node) 기반의 이진 트리를 정의하고, 순회(Traversal), 노드 수/단말 수/높이 계산 등 트리의 핵심 연산을 재귀적으로 구현합니다.

#### 실행 방법

```bash
python binary-tree-operations.py --demo
```

#### 필수 요건 체크리스트

  - [x] **노드 클래스**: `TNode(value, left, right)` 정의
  - [x] **4대 순회 알고리즘**: 전위(Pre-order), 중위(In-order), 후위(Post-order), 레벨(Level-order)
  - [x] **트리 정보 계산**: `size`(노드 수), `leaf_count`(단말 수), `height`(높이)
  - [ ] **확장 기능**: 이진 탐색 트리(BST)의 삽입/탐색/삭제, 균형 트리(AVL) 스텁(stub) 구현

-----

## 3\. 허프만 인코더 (`huffman_coding.py`)

텍스트 파일의 문자 빈도를 분석하고, 최소 힙(Min-Heap) 기반의 우선순위 큐를 활용하여 허프만 트리를 구성합니다. 이를 통해 데이터를 압축하고 다시 원본으로 복원하는 인코더/디코더를 구현합니다.

#### 실행 방법

```bash
# 인코딩 (압축)
python huffman_coding.py encode --in assets/sample.txt --out assets/sample.huf

# 디코딩 (복원)
python huffman_coding.py decode --in assets/sample.huf --out assets/sample.dec.txt
```

#### 필수 요건 체크리스트

  - [x] **빈도수 테이블** 생성
  - [x] **Min-Heap**으로 허프만 트리 구성
  - [x] **문자 → 비트** 매핑 테이블 생성
  - [x] **인코딩/디코딩** 및 파일 입출력

#### 복잡도 메모

  - **트리 빌드**: O(n log n)
  - **인코딩**: O(L) (L = 전체 문자 수)

-----

## 🧪 빠른 테스트 스크립트

```bash
# 1) 후위 표기 계산기 테스트
python postfix-calculator.py --expr "(1+2)*3-4/2"

# 2) 이진 트리 테스트
python binary-tree-operations.py --demo

# 3) 허프만 인코더/디코더 테스트
echo "mississippi river" > assets/sample.txt
python huffman_coding.py encode --in assets/sample.txt --out assets/sample.huf
python huffman_coding.py decode --in assets/sample.huf --out assets/sample.dec.txt
diff assets/sample.txt assets/sample.dec.txt && echo "Huffman Test OK"
```

-----

## 🗺️ 로드맵 (향후 개발 계획)

  - [ ] **후위 계산기**: 토큰 파서 개선, `sin`, `pow` 등 수학 함수 추가
  - [ ] **이진 트리**: 이진 탐색 트리(BST) 및 AVL 트리 기능 확장, `Graphviz`를 이용한 시각화
  - [ ] **허프만 코더**: 비트 단위 바이너리 포맷 적용, 대용량 파일 벤치마크

-----

## 🪪 라이선스

이 프로젝트는 [MIT 라이선스](https://opensource.org/licenses/MIT)를 따릅니다.
