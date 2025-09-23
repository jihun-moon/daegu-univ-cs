import random
import math
import os


targetDistance = random.randint(1, 100)
counter = 0

while (counter < 5):
    angle = int (input ("각도를 입력하세요: "))
    if 0 <= angle <= 90:
        power = int (input ("세기를 입력하세요: "))

        angle_radian = math.radians(angle)

        x_speed = power * math.cos(angle_radian)  #포의 수평 속도
        y_speed = power * math.sin(angle_radian)  #포의 수직 속도
        
        t = y_speed / 4.9        # t = 떨어지는 데 걸리는 시간  단, (0 <= angle <= 90)

        distance = x_speed * t


        myBallPosition = distance
        
        if -2 < (targetDistance - myBallPosition) < 2:
            print ("적이 쓰러졌습니다.")
            
        else:
            print ("적과의 거리는", targetDistance - myBallPosition, "입니다.")

        counter += 1
    else:
        print("0 과 90 사이로 입력해 주세요")
os.system('pause')

