#!/bin/bash

# 사용자에게 이름과 성을 입력하라는 메시지를 표시하고, 입력값을 변수에 저장합니다.
read -p "enter your name: " first last

# 입력받은 이름과 성을 각각 출력합니다.
echo "First name: $first"
echo "Last name: $last"