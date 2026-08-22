# 시스템 소프트웨어 실무

3학년 1학기 시스템 소프트웨어 실무 강의의 실습·과제 저장소입니다. 리눅스 기본 명령어,
Bash 셸 스크립트, 가상화(가상 머신과 Docker) 세 갈래를 다뤘습니다.

## 폴더 구조

- `notes/` … 수업을 들으며 정리한 노트
- `scripts/` … 실습으로 작성한 셸 스크립트
- `assets/` … 스크립트 실행 화면 캡처와 가상 머신 사용 녹화

## 학습 노트

- [리눅스 명령어 · 셸 스크립트 · 가상화 정리 노트](./notes/linux_command_summary.md)

과목 개요와 이론 정리는 Notion에 따로 두었습니다.
[시스템 소프트웨어 실무](https://www.notion.so/SW-9b39b015ff834c1399da7b35872cfa52)

## 실습 스크립트

| 파일 | 다루는 문법 | 하는 일 |
| --- | --- | --- |
| [`counter_loop.sh`](./scripts/counter_loop.sh) | `while`, `let` | 카운터를 0부터 9까지 올리며 출력 |
| [`user_input_example.sh`](./scripts/user_input_example.sh) | `read -p` | 한 줄로 받은 입력을 이름과 성 두 변수로 분리 |
| [`file_checker.sh`](./scripts/file_checker.sh) | `$#`, `[[ ]]`, `-f` `-r` `-w` | 인수로 받은 파일이 읽고 쓸 수 있는 일반 파일인지 검사 |
| [`menu_selector.sh`](./scripts/menu_selector.sh) | `case`, `tr` | Y/N/Q 메뉴 입력에 따라 `ls` 결과를 다르게 출력 |

### 실행 방법

```bash
chmod +x scripts/counter_loop.sh
./scripts/counter_loop.sh
```

`file_checker.sh`는 검사할 파일 이름을 인수로 받습니다.

```bash
./scripts/file_checker.sh counter_loop.sh
```

### 실행 화면

`counter_loop.sh`

![counter_loop.sh 실행 결과](assets/counter-loop.png)

`user_input_example.sh`

![user_input_example.sh 실행 결과](assets/user_input_example.png)

`menu_selector.sh`

![menu_selector.sh 실행 결과](assets/file_checker.png)

`file_checker.sh`

![file_checker.sh 실행 결과](assets/menu_selector.png)

> 위 두 캡처는 `assets/file_checker.png`와 `assets/menu_selector.png`에 서로 바뀐 이름으로 올라가 있습니다.
> 여기서는 실제 출력에 맞게 연결했습니다.

가상 머신에서 작업하던 화면은 [`assets/ubuntu-vm-usage.gif`](assets/ubuntu-vm-usage.gif)에 있습니다. 파일이 26MB라 링크로만 걸어 둡니다.

## 하면서 걸렸던 것

- `let`은 등호 양옆에 공백을 두면 안 됩니다. `counter_loop.sh`의 `let COUNTER=COUNTER+1`, 노트의 `let Net=Income-Expense`가 그래서 다 붙여 쓴 형태입니다.
- 수업 예제의 `case` 메뉴는 대문자만 받아서 소문자 `y`를 치면 `Invalid choice!`로 빠집니다. `menu_selector.sh`에서는 `tr 'a-z' 'A-Z'`로 한 번 변환한 뒤 `case`에 넘기도록 바꿨습니다.
- `file_checker.sh`의 `[[ ! -f "$1" || ! -r "$1" || ! -w "$1" ]]`는 `[ ]`로는 그대로 옮겨 쓸 수 없습니다. `||`로 조건을 묶는 건 bash 전용인 `[[ ]]`라서 되는 것이었습니다.
- `read -p "enter your name: " first last`처럼 변수를 두 개 적으면 한 줄 입력이 공백 기준으로 나뉘어 각각 들어갑니다.
