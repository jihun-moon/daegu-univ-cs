# Data Structures – Mini Projects (Stack, Tree, Huffman)

> 3줄 요약  
> - 스택 기반 후위 표기 계산기, 이진 트리 핵심 연산, 허프만 인코더 3종 실습  
> - 자료구조·알고리즘의 구현과 시간 복잡도 감각을 함께 훈련  
> - 단일 README로 실행 방법과 테스트 체크리스트를 통합 관리

## 📁 파일 구조
​
data-structures/
├── README.md
├── postfix-calculator.py        # 후위 표기 계산기
├── binary-tree-operations.py    # 이진 트리 연산
├── huffman_coding.py            # 허프만 인코더
└── assets/                      # 샘플 입력/출력, 스크린샷

## 🔧 환경
- Python 3.10+ 권장
- 가상환경(venv) 사용 권장
- 외부 의존성 없음(표준 라이브러리만 사용)

​
python -m venv .venv
source .venv/bin/activate  # Windows: .venvScriptsactivate
pip install -U pip

---

## 1) 후위 표기 계산기 (postfix-calculator.py)
스택을 직접(배열/리스트 기반) 구현하여 중위 표기식을 후위 표기식으로 변환하고 계산합니다.[^‣]

### 실행
​
python postfix-calculator.py --expr "(3+4)*2"
또는
echo "(3+4)*2" | python postfix-calculator.py

### 필수 요건 체크리스트
- [ ] 스택 API: push, pop, peek, is_empty  
- [ ] 중위 → 후위 변환(연산자 우선순위, 괄호)  
- [ ] 후위식 평가  
- [ ] 예외 처리: 언더플로우, 잘못된 토큰, 불일치 괄호  
- [ ] 확장 옵션: 음수, 소수, 공백 허용

### 예시
​
입력: (3+4)*2
후위: 3 4 + 2 *
값: 14

---

## 2) 이진 트리 연산 (binary-tree-operations.py)
노드 자료구조와 순회, 노드 수/단말 수/높이 등의 핵심 연산을 구현합니다.[^‣]

### 실행
​
python binary-tree-operations.py --demo

### 필수 요건 체크리스트
- [ ] TNode(value, left, right) 정의  
- [ ] 순회: 전위, 중위, 후위, 레벨(BFS)  
- [ ] size(노드 수), leaf_count(단말 수), height(높이)  
- [ ] 레벨 순회용 큐(리스트 or deque)  
- [ ] 확장: BST 삽입/탐색/삭제, 균형 트리(AVL) 스텁

### 예시 출력
​
Preorder: A B D E C F
Inorder : D B E A C F
Postord: D E B F C A
Level  : A B C D E F
size=6, leaves=3, height=3

---

## 3) 허프만 인코더 (huffman_coding.py)
문자 빈도 분석 후 최소 힙 기반 우선순위 큐로 허프만 트리를 구성해 인코딩/디코딩합니다.[^‣]

### 실행
​
인코딩
python huffman_coding.py encode --in assets/sample.txt --out assets/sample.huf
디코딩
python huffman_coding.py decode --in assets/sample.huf --out assets/sample.dec.txt

### 필수 요건 체크리스트
- [ ] 빈도수 테이블 생성  
- [ ] Min-Heap으로 트리 구성(leaf merge 반복)  
- [ ] 문자→비트 문자열 매핑 테이블 생성  
- [ ] 인코딩/디코딩 및 파일 입출력  
- [ ] 프리픽스 코드 검증, EOF 처리  
- [ ] 확장: 비트 단위 바이너리 포맷, 스트리밍 처리

### 복잡도 메모
- 빌드 O(n log n) ≈ 힙 push/pop 반복
- 인코딩 O(L)  L=전체 문자 수

---

## 🧪 빠른 테스트 스크립트
​
1) 후위 표기
python postfix-calculator.py --expr "(1+2)*3-4/2"
2) 이진 트리
python binary-tree-operations.py --demo
3) 허프만
echo "mississippi river" > assets/sample.txt
python huffman_coding.py encode --in assets/sample.txt --out assets/sample.huf
python huffman_coding.py decode --in assets/sample.huf --out assets/sample.dec.txt
diff assets/sample.txt assets/sample.dec.txt && echo "OK"

## 📸 스크린샷/다이어그램
- assets/postfix.png  
- assets/tree-traversal.png  
- assets/huffman.png

## 🗺 로드맵(선택)
- [ ] 후위 계산기: 토큰 파서 개선, 함수 추가(sin, pow)  
- [ ] 트리: BST·AVL 확장, 시각화  
- [ ] 허프만: 바이너리 포맷과 대용량 벤치마크

## 🬭 라이선스
MIT
​
필요하면 각 스크립트의 CLI 옵션 뼈대(argparse)와 샘플 테스트 데이터까지 바로 써넣은 버전도 만들어 드릴게요.
