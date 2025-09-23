# 🐧 리눅스 명령어 활용 및 셸 스크립트 작성 실습

> ### 3줄 요약
>
>   - **리눅스 실무**: Ubuntu 환경에서 파일 시스템, 프로세스, 네트워크 관련 핵심 명령어를 실습합니다.
>   - **자동화 스크립팅**: Bash 셸 스크립트를 사용하여 파일 일괄 처리, 로그 관리 등 반복적인 작업을 자동화하는 스크립트를 작성합니다.
>   - **핵심 문법**: 스크립트 작성에 필수적인 입출력, 조건문, 반복문, 함수, 그리고 `cron`을 이용한 스케줄링까지 단계별로 학습합니다.

-----

## 📁 폴더 구조 (권장)

```
systems-software-practice/
├── README.md
├── assets/
│   └── ubuntu-vm-usage.gif
└── scripts/
    ├── setup.sh
    ├── batch_rename.sh
    ├── log_rotate.sh
    ├── find_large_files.sh
    └── menu_tool.sh
```

-----

## 🧰 실습 환경

  - **배포판**: Ubuntu LTS (WSL, VM, 또는 베어메탈)
  - **셸**: Bash 5+
  - **편집기**: `vim` 또는 `nano`
  - **권한**: `sudo`를 이용한 관리자 권한 필요

-----

## 1\. 기본 명령어 체크리스트

| 카테고리 | 명령어 |
| :--- | :--- |
| **파일/디렉터리** | `pwd`, `ls -al`, `cd`, `mkdir -p`, `rm -rf`, `cp -r`, `mv` |
| **내용 확인** | `cat`, `less`, `head`, `tail`, `wc`, `nl` |
| **검색/필터** | `grep`, `find`, `cut`, `sort`, `uniq`, `xargs` |
| **압축** | `tar`, `zip`, `unzip` |
| **권한** | `chmod`, `chown`, `umask` |
| **시스템/프로세스**| `ps aux`, `top`/`htop`, `kill`, `df -h`, `du -sh` |
| **네트워킹** | `ping`, `curl`, `ss -tulpn` |

> **⚠️ 주의**: `rm -rf /` 와 같이 시스템에 치명적인 명령어는 사용 전 `echo`로 실행될 대상을 먼저 확인하는 습관을 들이세요.

-----

## 2\. Bash 스크립트 기초

#### 스크립트 헤더 (안전 모드)

스크립트 실행 중 오류 발생 시 즉시 중단하여 예기치 않은 동작을 방지합니다.

```bash
#!/usr/bin/env bash
set -Eeuo pipefail
IFS=$'\n\t'
```

#### 변수 및 인자 처리

```bash
# $1 인자가 없으면 "world"를 기본값으로 사용
NAME=${1:-world}
echo "Hello, $NAME"
```

#### 사용자 입력 받기

```bash
read -rp "파일명을 입력하세요: " FILE
```

#### 조건문 및 반복문

```bash
# 파일 존재 여부 확인
if [[ -f "$FILE" ]]; then echo "OK"; fi

# for 반복문
for f in *.log; do echo "$f"; done

# while 반복문 (파일 라인별 읽기)
while read -r line; do echo "$line"; done < input.txt
```

-----

## 3\. 예제 스크립트 모음

### `setup.sh` — 개발 환경 초기 설정

```bash
#!/usr/bin/env bash
set -Eeuo pipefail
sudo apt-get update -y && sudo apt-get install -y vim git curl htop tree
```

### `batch_rename.sh` — 파일 이름 일괄 변경

```bash
#!/usr/bin/env bash
set -Eeuo pipefail
prefix=${1:-img}
i=1
for f in *.png; do
    new=$(printf "%s_%03d.png" "$prefix" "$i")
    mv -- "$f" "$new"
    ((i++))
done
```

### `find_large_files.sh` — 용량이 큰 파일/디렉터리 탐색

```bash
#!/usr/bin/env bash
set -Eeuo pipefail
du -ah . | sort -hr | head -n 20
```

-----

## 4\. 크론(Cron) 스케줄링

`crontab -e` 명령어로 특정 시간에 스크립트를 자동 실행하도록 등록할 수 있습니다.

```bash
# 매일 새벽 3시에 log_rotate.sh 스크립트를 실행하고, 결과를 /tmp/logrotate.out에 기록
0 3 * * * /home/USER/scripts/log_rotate.sh >/tmp/logrotate.out 2>&1
```

-----

## 🖼️ 실행 데모

\<img src="assets/ubuntu-vm-usage.gif" alt="Ubuntu VM Usage Demo"/\>

-----

## 🪪 라이선스

이 프로젝트는 [MIT 라이선스](https://opensource.org/licenses/MIT)를 따릅니다.
