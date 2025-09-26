#!/bin/bash

COUNTER=0

echo "Starting the while loop..."

# COUNTER 변수가 10보다 작은 동안 루프를 반복합니다.
while [ "$COUNTER" -lt 10 ]; do
    echo "The counter is $COUNTER"
    
    # 'let' 명령어를 사용하여 변수를 1 증가시킵니다.
    let COUNTER=COUNTER+1
done

echo "Loop finished."