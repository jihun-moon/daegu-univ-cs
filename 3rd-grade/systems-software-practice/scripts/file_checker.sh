#!/bin/bash

# 스크립트 실행 시 전달된 인수가 1개 미만이면 사용법을 안내하고 종료합니다.
if [ $# -lt 1 ]; then
    echo "Usage: $0 <filename>"
    exit 1
fi

# 파일이 일반 파일이 아니거나, 읽기 또는 쓰기가 불가능한 경우 에러 메시지를 출력합니다.
# [[ ... ]]는 여러 조건을 동시에 확인하기에 편리합니다.
if [[ ! -f "$1" || ! -r "$1" || ! -w "$1" ]]; then
    echo "Error: File '$1' is not an accessible regular file."
    exit 1
else
    # 모든 조건을 통과하면 파일이 접근 가능하다는 메시지를 출력합니다.
    echo "Success: File '$1' is a regular file and is readable/writable."
fi