# C Programming Exercises

간단한 문제를 절차적으로 분해해 함수 단위로 구현하는 연습을 통해, 자료형·제어문·함수·배열·문자열·포인터·동적 메모리와 디버깅 습관을 확립합니다.

- 학년: 2학년
- 언어: C
- 빌드: gcc 또는 clang

## 폴더 구조

- assets
  - case-converter.png
  - find-max-min-pointer.png
  - multiplication-table.png
  - simple-xor-cipher.png
- case-converter.c
- find-max-min-pointer.c
- multiplication-table.c
- simple-xor-cipher.c
- README.md

## 빠른 실행

```bash
# 1) 컴파일
gcc -O2 -Wall -Wextra -std=c11 -o case-converter case-converter.c
gcc -O2 -Wall -Wextra -std=c11 -o find-max-min-pointer find-max-min-pointer.c
gcc -O2 -Wall -Wextra -std=c11 -o multiplication-table multiplication-table.c
gcc -O2 -Wall -Wextra -std=c11 -o simple-xor-cipher simple-xor-cipher.c

# 2) 실행 예
./case-converter
./find-max-min-pointer
./multiplication-table
./simple-xor-cipher
```

- 권장 옵션: -fsanitize=address,undefined 로 런타임 검사를 추가해 메모리/UB를 조기에 발견하세요.

## 과제별 설명

### 1) case-converter.c
- 내용: 입력된 영문 문자열의 대소문자를 변환
- 포인트: 문자 범위 검사, 입력 길이 제한, 버퍼 종료 처리
- 입력 예: Hello → hELLO
- 스크린샷  
  <image source="assets/case-converter.png">case-converter output</image>

### 2) find-max-min-pointer.c
- 내용: 포인터를 이용해 배열의 최댓값·최솟값 탐색
- 포인트: 포인터 산술, const 포인터 인터페이스, 경계값 테스트
- 입력 예: 3 9 -1 7 → max=9, min=-1
- 스크린샷  
  <image source="assets/find-max-min-pointer.png">find-max-min-pointer output</image>

### 3) multiplication-table.c
- 내용: n까지의 구구단 또는 곱셈표 출력
- 포인트: 중첩 반복, 출력 포맷 정렬
- 입력 예: n=5 → 5×5 표
- 스크린샷  
  <image source="assets/multiplication-table.png">multiplication-table output</image>

### 4) simple-xor-cipher.c
- 내용: 고정 키로 XOR 암복호화
- 포인트: 비트연산 가역성 a^k^k=a, 파일/표준입력 선택, 널 종료
- 입력 예: plain + key → cipher, 같은 key로 복호화
- 스크린샷  
  <image source="assets/simple-xor-cipher.png">simple-xor-cipher output</image>

## 코드 스타일과 품질

- 포맷: clang-format 권장
- 경고: -Wall -Wextra 기본, 필요 시 -Wshadow -Wconversion 추가
- 입력 검증: scanf에는 길이 제한 사용. 문자열은 항상 널 종료 보장
- 메모리: malloc 결과 검사, 사용 후 free, 이중 해제 방지 위해 p=NULL

## 테스트 가이드

- 경계값: 빈 문자열, 최솟값/최댓값이 같은 배열, n=1·n=0
- 비정상 입력: 비문자·음수·과도한 길이
- 시간복잡도: 변환/탐색/출력 모두 O(n). 표 출력은 O(n^2)

## 빌드 스크립트 예시

```bash
#!/usr/bin/env bash
set -euo pipefail
CC=${CC:-gcc}
CFLAGS="-O2 -Wall -Wextra -std=c11"
$CC $CFLAGS -o case-converter case-converter.c
$CC $CFLAGS -o find-max-min-pointer find-max-min-pointer.c
$CC $CFLAGS -o multiplication-table multiplication-table.c
$CC $CFLAGS -o simple-xor-cipher simple-xor-cipher.c
echo "Build OK"
```

## 라이선스

- 교육 목적의 예제 코드입니다. 필요 시 MIT 등으로 라이선스를 지정하세요.

## 참고

- C 언어 레퍼런스: https://en.cppreference.com
- C 보안 코딩(문자열): https://wiki.sei.cmu.edu


## Notion Page
- C Programming: https://www.notion.so/771d07fe436e4f58a0a490fdde107ca6
