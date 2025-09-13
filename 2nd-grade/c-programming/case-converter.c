#include <stdio.h>

void main(void) {
    char n;
    printf("알파벳 소문자 한 개 입력하세요: ");
    scanf("%c", &n);
    if ('a' <= n && n <= 'z') {
        printf("알파벳 소문자 %c 에 해당하는 대문자는 %c 입니다.\n", n, n - 32);
    }
}
