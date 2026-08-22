# 컴퓨터 프로그래밍 기초

1학년 '컴퓨터 프로그래밍 기초' 과목에서 제출한 파이썬 과제 세 개입니다. 콘솔, Turtle, Tkinter를 하나씩 써 봤습니다.

과목 회고는 노션에 적어 뒀습니다. [컴퓨터 프로그래밍 기초 회고](https://www.notion.so/b26ffa438670490da0abf7f9c9aaf876?source=copy_link)

## 과제 목록

| 폴더 | 내용 | 쓴 것 |
| --- | --- | --- |
| [angry-turtle](angry-turtle) | 각도와 세기를 맞춰 표적을 맞히는 발사 게임 | turtle |
| [ball-simulation-tkinter](ball-simulation-tkinter) | 벽에서 튕기는 공, 클릭하면 총알 발사 | tkinter |
| [fortress-game-console](fortress-game-console) | 콘솔로 만든 포트리스 | 표준 입출력 |

## Angry Turtle

방향키로 발사각과 세기를 정하고 스페이스바로 쏘는 게임입니다. 땅 위 임의의 위치에 빨간 표적이 생기고, 착탄 지점이 표적에서 10 이내면 명중입니다. 각도는 0도에서 90도까지 2도씩, 세기는 0에서 80까지 5씩 조절할 수 있고 기회는 다섯 번입니다.

포탄은 x = v·cosθ·t, y = v·sinθ·t − 4.9t² 를 0.1초 간격으로 계산해 `goto()` 로 옮겼습니다. 거북이가 날아가는 방향을 보게 하려고 매 걸음 `towards()` 로 직전 좌표를 향한 각도를 구한 뒤 180도 돌렸습니다.

게임이 끝나는 순간 스페이스바를 연타하면 오류가 났습니다. 원인을 못 찾아서 재시작 사이에 `time.sleep()` 을 넣어 막아 뒀고, 소스에도 그렇게 주석을 달아 놨습니다.

<img src="angry-turtle/docs/angry-turtle-1.png" alt="Angry Turtle 실행 화면" width="600"/>

## 공 튀기기 시뮬레이션

`Ball` 클래스 하나로 공과 총알을 전부 만듭니다. 매 프레임 `canvas.coords()` 로 좌표를 읽고, 800x400 캔버스 경계에 닿으면 그 축의 속도 부호를 뒤집습니다. 캔버스를 클릭하면 총알이 하나 생겨 오른쪽으로 날아가는데, 총알도 같은 규칙을 쓰기 때문에 벽에 닿으면 사라지지 않고 되돌아옵니다.

`while True` 안에서 `window.update()` 로 화면을 직접 갱신하는 구조라 마지막 줄의 `window.mainloop()` 까지는 실행이 가지 않습니다. 초록색 공은 만들어만 두고 루프에서 `move()` 를 부르지 않아 제자리에 서 있습니다.

<img src="ball-simulation-tkinter/docs/tkinter-balls.png" alt="공 튀기기 시뮬레이션 실행 화면" width="600"/>

## 콘솔 포트리스 게임

각도와 세기를 입력받아 사거리를 구하고, 1에서 100 사이 임의의 목표 거리와 비교합니다. 오차가 2 미만이면 명중이고 기회는 다섯 번입니다.

체공 시간은 `t = v·sinθ / 4.9` 로 한 번에 구해 수평 속도에 곱했습니다. Angry Turtle과 물리 공식은 같지만, 이쪽은 착탄 거리만 계산하고 Angry Turtle은 궤적을 0.1초씩 찍어 그립니다.

각도를 0에서 90 밖으로 넣으면 다시 입력을 받는데, 이때는 시도 횟수가 올라가지 않습니다. 마지막 줄의 `os.system('pause')` 는 윈도우에서만 동작합니다.

<img src="fortress-game-console/docs/fortress-console.png" alt="콘솔 포트리스 게임 실행 화면" width="600"/>

## 실행 방법

turtle과 tkinter는 파이썬 표준 라이브러리라 따로 설치할 것은 없습니다. Python 3.10에서 확인했습니다.

```bash
cd angry-turtle
python angry-turtle.py
```
