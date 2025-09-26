#!/bin/bash

echo "Enter 'Y' to see all files (including hidden ones)."
echo "Enter 'N' to see only non-hidden files."
echo "Enter 'Q' to quit."
read -p "Enter your choice: " reply

# 사용자의 입력을 대문자로 변환하여 비교의 일관성을 유지합니다.
choice=$(echo "$reply" | tr 'a-z' 'A-Z')

case "$choice" in
    Y|YES)
        echo "Displaying all files..."
        ls -a ;;
    N|NO)
        echo "Displaying non-hidden files..."
        ls ;;
    Q)
        echo "Quitting."
        exit 0 ;;
    *)
        echo "Invalid choice! Please try again."
        exit 1 ;;
esac