import turtle as t
import random
import math
import time

print("="*20,"조작키","="*20)
print("\n ←, → 는 각도 조절 ")
print(" ↑, ↓ 는 힘 조절 ")
print("\n스페이스바 - 발사\n")
print("\n게임 방식 - 발사 할 수 있는 기회는 총 다섯 번.\n잘 예측하여 빨간색 부분에 표시된 곳을 맞춰라!! ")
print("="*48)

def message(m1, m2):
    t.clear()
    t.hideturtle()
    t.up()
    t.speed(0)
    t.goto(0, 100)
    t.write(m1, False, "center", ("Arial", 20, "bold"))
    t.goto(0,-100) 
    t.write(m2, False, "center", ("Arial", 15))
    t.home()

def start():      #처음 나와 있던 글씨를 그려주고 시작하는 함수
    global playing
    if playing == False:
        t.clear()
        t.speed(0)
        t.hideturtle()
        t.up()
        player.clear()
        play()
        time.sleep(2)   #게임 끝나면서 스페이스바 연속으로 누르면 게임 오류 생겨서 딜레이 만듦.
        playing = True
        background()
        cannonball()
        
    elif ( playing == True):
        play()

def play():       #카운트와 게임 상태를 가르쳐주고 끝나면 엔드 함수를 실행하는 놈.
    global playing
    global state
    global counter

    if ( playing == True):
        if (counter < 5):
            if (state != "게임 중"):
                state = "게임 중"
                shoot()
                counter += 1
        else:
        
            end()       

def background():  #땅과 적 위치를 그려주는 함수
    global target
    
    target = random.randint(-150, 250)
    t.color("green")
    t.goto(-300, 0)
    t.pendown()
    t.goto(400, 0)

    t.penup()

    t.pensize(3)
    t.color("red")
    t.goto(target - 10, 0)
    t.pendown()
    t.goto(target + 10, 0)

    t.penup()

def cannonball():  #대포 알
    player.hideturtle()
    player.color("black")
    player.goto(-300, 0)
    player.pensize(2)
    player.setheading(30)
    player.pendown()
    player.showturtle()
    
def sine(x):        #sine (사인)값 반환
    return math.sin(math.radians(x))
    
def cos(x):         #sine (사인)값 반환
    return math.cos(math.radians(x))        
        
    
def shoot():
    global v0
    global state
    global target
    gravity=9.8        #중력가속도(9.8)
    ceta=player.heading()   #발사각
    time=0
    x=-300
    y=0
    while player.ycor()>=0:
        player.goto(-300+(v0*cos(ceta)*time), v0*sine(ceta)*time-gravity*time*time/2)   #공식 대입
        time += 0.1
        player.setheading(player.towards(x,y)+180)
        x=player.xcor()
        y=player.ycor()
    player.color("red")
    player.penup()
    player.speed(0)
    player.goto(-300,0)
    player.pendown()
    player.setheading(ceta)
    player.color("black") 
    state = ""

    d = target - x

    if -10 <= d <= 10:
        print("☆★☆★적이 쓰러졌습니다.☆★☆★")
    else:
        print("적과의 거리는", d, "입니다.")


def turn_right(): # 오른쪽 키를 눌렀을 때의 함수
     if playing == True:
         if player.heading()>=2:
             player.right(2)


def turn_left(): # 왼쪽 키를 눌렀을 때의 함수
    if playing == True:
        if player.heading()<=88:
            player.left(2)

def turn_up(): # 위쪽 키를 눌렀을 때의 함수
    global playing
    global state
    global v0

    if ( playing == True):        # 첫 화면을 지나가면 real_shoot으로 변경.
        if state != "게임 중":            # real_shoot이고 state 게임 중이 아닐때
            if v0 >= 80:
                print("그 위로는 올릴 수 없습니다.")
            else:
                v0 += 5
                print("현재 강도" ,v0,"입니다.")
        else:
            print("게임 중에는 조정 할 수 없습니다.")

    if playing == False:                  # 게임 첫 화면에서 조작 가능하기에 조작 금지를 위해..
        print("게임에 들어가세요")

def turn_down(): # 아래쪽 키를 눌렀을 때의 함수
    global playing
    global state
    global v0
    if ( playing == True):         # 첫 화면을 지나가면 real_shoot으로 변경.
        if state != "게임 중":             # real_shoot이고 state 게임 중이 아닐때
            if v0 <= 0:
             print("그 밑으로는 내릴 수 없습니다.")
            else:
                v0 -= 5
                print("현재 강도" ,v0,"입니다.")

        else:
            print("게임 중에는 조정 할 수 없습니다.")
                
    if playing == False:                   # 게임 첫 화면에서 조작 가능하기에 조작 금지를 위해..
        print("게임에 들어가세요")

def end(): # 게임 끝내는 함수
    global playing
    global state
    global v0
    global counter
    if playing == True:
        v0 = 50
        state = ""
        player.reset()
        t.reset()
        t.hideturtle()
        t.speed(0)
        time.sleep(1)   #게임 끝나면서 스페이스바 연속으로 누르면 게임 오류 생겨서 딜레이 만듦.
        message("Angry Turtle","[Space]")

        playing = False # playing을 False로 변경한다.
        counter = 0



#========초기 셋팅 값========
        
v0 = 50
state = ""
counter = 0

player = t.Turtle()
screen = player.getscreen()
player.shape("turtle")

t.shape("turtle")
t.title("Angry turtle")
t.setup(1000, 600) 
t.speed(0) 
t.up()

playing = False # playing을 선언하고 False로 초기화한다.
message("Angry Turtle","[Space]")

#============================

screen.onkeypress(turn_right, "Right") 
screen.onkeypress(turn_left, "Left")
screen.onkeypress(turn_up, "Up")
screen.onkeypress(turn_down, "Down")
screen.onkeypress(start, "space")
screen.listen()

screen.mainloop()


