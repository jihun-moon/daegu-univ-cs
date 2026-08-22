# 자료구조 (과제·실습 모음)

자료구조 과목에서 Python으로 작성한 과제와 주차별 실습 코드입니다. 교재 코드 번호가 주석으로 붙어 있어서 어느 단원 코드인지 바로 찾을 수 있습니다.

과목 개요와 과제 설명은 [Notion 노트](https://www.notion.so/a4fb4460bea2442f9db42ca58ed5754a?source=copy_link)에 있습니다.

## 폴더 구조

| 폴더 | 내용 |
| --- | --- |
| [`src/`](src) | 제출한 과제 소스 |
| [`labs/`](labs) | 주차별 실습 코드 |
| [`docs/`](docs) | 수업 노트 스캔본과 정리 PDF |
| [`results/`](results) | 과제 실행 화면 |

## 과제

| 과제 | 파일 | 구현한 것 |
| --- | --- | --- |
| 후위 표기법 계산기 | [`src/postfix-calculator.py`](src/postfix-calculator.py) | 배열 기반 `ArrayStack`, 괄호 검사, 중위식을 후위식으로 바꾸는 `Infix2Postfix`, 후위식 계산 `evalPostfix` |
| 이진 트리 연산 | [`src/binary-tree-operations.py`](src/binary-tree-operations.py) | 전위·중위·후위 순회, 원형 큐를 쓴 레벨 순회, 노드 수·단말 수·높이 계산 |
| 허프만 코딩 | [`src/huffman_coding.py`](src/huffman_coding.py) | `heapq`로 트리 구성, 코드표 생성, 8비트 패딩 후 바이트 저장, 압축과 복원 |

계산기 과제에서 시간을 가장 많이 쓴 건 스택이 아니라 입력 검증이었습니다. 괄호 짝은 스택으로 금방 잡히는데 `( 3 + 2 ) 8`처럼 괄호 바깥에 연산자가 빠진 경우가 안 걸렸습니다. `has_operator_inside_parentheses`를 따로 만들어 조건을 하나씩 늘려가며 맞췄고, 소스에 "제일 힘들었던 부분"이라고 적어둔 게 그 함수입니다. 파일 맨 아래에 확인용 케이스 11개를 주석으로 남겨두었습니다. 토큰은 공백으로 구분해 받으므로 `3+2`가 아니라 `3 + 2`로 넣어야 합니다.

허프만 코딩은 압축 파일만으로 복원이 되어야 해서 코드표를 파일 안에 같이 넣었습니다. 맨 앞에 코드표 길이 4바이트를 쓰고 그 뒤에 코드표를 JSON으로 붙였습니다. 패딩 비트 수는 데이터 첫 8비트에 적어둡니다.

## 실습

| 주차 | 폴더 | 파일 |
| --- | --- | --- |
| 01 연결 리스트 | [`labs/01-linked-list`](labs/01-linked-list) | `LinkedList.py`, `LinkedStack.py`, `LinkedStackEx.py`, `LinkedQueue.py`, `DoublyLinkedDeque.py` |
| 02 스택과 큐 | [`labs/02-stacks-and-queues`](labs/02-stacks-and-queues) | `BinaryTree_CircularQueue.py` |
| 03 트리 | [`labs/03-trees`](labs/03-trees) | `CircularQueue.py`, `BinaryTree.py`, `BinSrchTree.py`, `AVLTree.py` |

`LinkedQueue.py`는 tail 하나만 들고 원형으로 이어붙인 큐입니다. `DoublyLinkedDeque.py`는 앞뒤 양쪽에서 넣고 빼는 덱입니다.

`labs/03-trees`는 파일끼리 import로 물려 있습니다. `AVLTree.py`가 `BinaryTree`와 `BinSrchTree`를, `BinaryTree.py`가 `CircularQueue`를 가져다 씁니다. 다른 위치에서 실행하면 `ModuleNotFoundError`가 나므로 해당 폴더로 들어가서 실행해야 합니다. `BinSrchTree.py`는 `__main__` 블록이 없어서 단독으로 실행하면 출력이 없습니다.

## 실행

```bash
# 후위 표기법 계산기 (숫자·연산자·괄호를 공백으로 구분해 입력)
python src/postfix-calculator.py

# 이진 트리 연산
python src/binary-tree-operations.py

# 허프만 코딩
python src/huffman_coding.py compress input.txt output.bin
python src/huffman_coding.py decompress output.bin restored.txt
```

```bash
# 트리 실습은 폴더 안에서 실행
cd labs/03-trees
python AVLTree.py
```

## 실행 결과

<img src="results/postfix-calculator-result.png" alt="후위 표기법 계산기 실행 결과" width="600"/>

<img src="results/binary-tree-operations-result.png" alt="이진 트리 연산 실행 결과" width="600"/>

<img src="results/huffman-coding-result.png" alt="허프만 코딩 실행 결과" width="600"/>
