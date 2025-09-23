# ⚙️ C Programming – Mini Utilities

> ### 3줄 요약
>
>   - **콘솔 실습 4종**: 구구단 출력기, 대소문자 변환, XOR 암복호화, 포인터를 이용한 최대·최소값 탐색 유틸리티 모음입니다.
>   - **핵심 문법 연습**: 표준 입출력, 포인터, 비트 연산자, 제어문 등 C언어의 핵심 문법을 종합적으로 연습합니다.
>   - **통합 관리**: 단일 README 파일을 통해 여러 프로그램의 빌드, 실행, 테스트 방법을 한 곳에서 관리합니다.

-----

## 📁 파일 목록

  - `multiplication-table.c`: 구구단 출력기
  - `case-converter.c`: 대소문자 변환기
  - `simple-xor-cipher.c`: XOR 암호화/복호화기
  - `find-max-min-pointer.c`: 포인터를 이용한 최대·최소값 탐색기
  - `assets/`: 샘플 입출력, 스크린샷 등

-----

## 🔧 빌드 방법

### 단일 파일 컴파일

`bin` 디렉터리를 생성한 후, 각 파일을 개별적으로 컴파일합니다. (Windows MinGW, WSL, macOS, Linux 환경)

```bash
mkdir -p bin
gcc -O2 -Wall -Wextra -o bin/mul multiplication-table.c
gcc -O2 -Wall -Wextra -o bin/case case-converter.c
gcc -O2 -Wall -Wextra -o bin/xor  simple-xor-cipher.c
gcc -O2 -Wall -Wextra -o bin/ptr  find-max-min-pointer.c
```

### Makefile 사용 (선택)

프로젝트 루트에 `Makefile`을 생성하고 아래 내용을 붙여넣은 뒤, `make` 명령어로 모든 파일을 한 번에 빌드할 수 있습니다.

```makefile
CC=gcc
CFLAGS=-O2 -Wall -Wextra
BIN=bin

TARGETS=$(BIN)/mul $(BIN)/case $(BIN)/xor $(BIN)/ptr

all: $(TARGETS)

$(BIN)/mul: multiplication-table.c
	$(CC) $(CFLAGS) -o $@ $<

$(BIN)/case: case-converter.c
	$(CC) $(CFLAGS) -o $@ $<

$(BIN)/xor: simple-xor-cipher.c
	$(CC) $(CFLAGS) -o $@ $<

$(BIN)/ptr: find-max-min-pointer.c
	$(CC) $(CFLAGS) -o $@ $<

clean:
	rm -f $(TARGETS)
```

-----

## 1\. 구구단 출력기 (`multiplication-table.c`)

정수 하나를 입력받아 해당하는 구구단을 형식에 맞춰 콘솔에 출력합니다.

#### 실행

```bash
# 7단을 출력하는 예시
echo 7 | ./bin/mul
```

#### 요구사항 체크

  - [ ] 단 입력 유효성 검사 (2\~9단)
  - [ ] 포맷팅: `"7 x i = 값"` 형식으로 한 줄씩 9개 출력
  - [ ] 잘못된 입력 시 에러 메시지 출력 또는 재입력 요구

-----

## 2\. 대소문자 변환기 (`case-converter.c`)

알파벳 문자 하나를 입력받아 아스키코드(ASCII) 값 연산을 통해 대문자는 소문자로, 소문자는 대문자로 변환합니다.

#### 실행

```bash
echo a | ./bin/case
```

#### 요구사항 체크

  - [ ] 입력된 문자가 알파벳인지 검증
  - [ ] 소문자 → 대문자 변환 로직 (`c - 32` 또는 `c - 'a' + 'A'`)
  - [ ] 대문자 → 소문자 변환 로직 (`c + 32` 또는 `c - 'A' + 'a'`)
  - [ ] 알파벳이 아닐 경우 원본 문자 그대로 출력

-----

## 3\. XOR 암호화/복호화기 (`simple-xor-cipher.c`)

XOR 비트 연산의 특징(`A ^ B ^ B = A`)을 이용하여, 동일한 키(Key)로 암호화와 복호화를 모두 수행하는 간단한 암호화 프로그램입니다.

#### 실행

```bash
# "hello"라는 평문을 "secret" 키로 암호화하여 enc.bin 파일에 저장
echo "hello" | ./bin/xor secret > assets/enc.bin

# 암호화된 파일을 동일한 키로 복호화하여 dec.txt 파일에 저장
./bin/xor secret < assets/enc.bin > assets/dec.txt
```

#### 요구사항 체크

  - [ ] 표준 입력(stdin)으로 데이터를 받고, 표준 출력(stdout)으로 결과를 출력
  - [ ] 암호화/복호화에 사용할 키 문자열을 인자로 전달받음
  - [ ] 키의 길이를 순환하며 XOR 연산을 적용

-----

## 4\. 포인터 최대·최소 탐색기 (`find-max-min-pointer.c`)

두 개의 정수를 입력받아, 함수의 인자로 전달된 포인터 변수에 최대값과 최소값을 저장하여 반환합니다.

#### 실행

```bash
echo "12 5" | ./bin/ptr
```

#### 요구사항 체크

  - [ ] 함수 시그니처: `void findMinMax(int a, int b, int* min, int* max);`
  - [ ] 주소 값 전달을 통해 `main` 함수에 선언된 변수의 값을 변경
  - [ ] 입력 값에 대한 유효성 검증 및 예외 처리

-----

## 🧪 빠른 테스트 시나리오

```bash
# 1) 구구단: 9줄이 정상적으로 출력되는지 확인
for n in 2 5 9; do echo $n | ./bin/mul | wc -l; done

# 2) 대소문자: a -> A, Z -> z, 3 -> 3으로 변환되는지 확인
printf "aZ3" | ./bin/case

# 3) XOR: 암호화 -> 복호화 후 원본 텍스트로 돌아오는지 확인
echo "Hello, World!" | ./bin/xor mySecretKey | ./bin/xor mySecretKey

# 4) 포인터: 최대/최소값이 올바르게 출력되는지 확인
echo "10 20" | ./bin/ptr
```

-----

## 🪪 라이선스

이 프로젝트는 [MIT 라이선스](https://opensource.org/licenses/MIT)를 따릅니다.
