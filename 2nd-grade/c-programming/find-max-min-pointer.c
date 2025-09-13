#include <stdio.h>

// 포인터를 사용하여 최대값과 최소값을 저장하는 함수
void FindMaxMin(int a, int b, int *max, int *min) 
{
    if (a > b) 
    {
        *max = a;
        *min = b;
    } 
    else 
    {
        *max = b;
        *min = a;
    }
}


void main(void) 
{
    int a, b;
    int max, min;

    printf("숫자 두 개 입력:");
    scanf("%d %d", &a, &b);

    // 포인터를 전달하여 max와 min의 값을 수정
    FindMaxMin(a, b, &max, &min);

    printf("입력된 %d %d 중에서 큰 수는 %d, 작은 수는 %d\n", a, b, max, min);
}
