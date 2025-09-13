from tkinter import *
import time


class Ball:				
    def __init__(self, canvas, color, size, x, y, xspeed, yspeed):	# 생성자
        self.canvas = canvas            # 캔버스 객체
        self.color = color		# Ball의 색상
        self.size = size		# Ball의 크기
        self.x = x			# Ball의 x좌표
        self.y = y			# Ball의 y좌표
        self.xspeed = xspeed		# Ball의 수평방향 속도
        self.yspeed = yspeed		# Ball의 수직방향 속도
        self.id = canvas.create_oval(x, y, x+size, y+size, fill=color)

    def move(self):			# Ball을 이동시키는 함수
        self.canvas.move(self.id, self.xspeed, self.yspeed)
        (x1, y1, x2, y2) = self.canvas.coords(self.id)	# 공의 현재 위치를 얻는다. 
        (self.x, self.y) = (x1, y1)
        if x1 <= 0 or x2 >= WIDTH:
            self.xspeed = - self.xspeed
        if y1 <= 0 or y2 >= HEIGHT:
            self.yspeed = - self.yspeed	    # 속도의 부호를 반전시킨다
      
WIDTH = 800		# 윈도우의 가로 크기를 저장한다. 
HEIGHT = 400

window = Tk()
canvas = Canvas(window, width=WIDTH, height=HEIGHT)
canvas.pack()

enemy = Ball(canvas, "red", 100, 500, 0, 0, 5)
ufo = Ball(canvas, "green", 100, 100, 200, 0, 5)

bullets = []


def fire(event):
    bullets.append(Ball(canvas, "red", 10, 150, 250, 10, 0))

canvas.bind("<Button-1>", fire)

    
while True:
    for bullet in bullets:
        bullet.move()

    enemy.move()
    window.update()
    time.sleep(0.03)

window.mainloop()





