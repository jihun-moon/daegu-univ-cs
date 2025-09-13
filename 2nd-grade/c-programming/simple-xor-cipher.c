#include <stdio.h>

void main(void){
    int i, key = 3;
    char orgTxT[30];
    char codedTxT[30];
    char rcvTxT[30];

    printf("오늘의 암호 단어 하나 입력해봐");
    scanf("%s", orgTxT);
    printf("오늘의 암호 단어는 %s입니다\n", orgTxT);

    // 암호화
    i = 0;
    while(orgTxT[i] != '\0'){
        codedTxT[i] = orgTxT[i] ^ key;
        i++;
    }
    codedTxT[i] = '\0';
    
    printf("\n오늘의 암호 단어를 암호화하면 %s입니다\n", codedTxT);
    
    // 암호 풀기
    i = 0;
    while(codedTxT[i] != '\0'){
        rcvTxT[i] = codedTxT[i] ^ key;
        i++;
    }

    rcvTxT[i] = '\0';
    printf("\n암호화된 단어를 복호하면 %s입니다\n", rcvTxT);
}