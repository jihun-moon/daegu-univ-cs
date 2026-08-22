# 컴퓨터 소프트웨어 개발 입문

1학년 '컴퓨터 소프트웨어 개발 입문' 과목의 과제와 실습입니다. App Inventor 블록 코딩과 Processing 스케치를 하나씩 다뤘습니다.

수업에서 다룬 내용은 노션에 정리해 뒀습니다. [컴퓨터 소프트웨어 개발 입문 정리](https://www.notion.so/0004c06de9de47be9d6937f7f33cb2a7?source=copy_link)

## 과제

### 구구단 트레이너

App Inventor로 만든 안드로이드 앱이고, 앱 이름은 '구구단을 외자!' 입니다. 시작 화면에서 구구단표를 보거나 테스트를 고를 수 있습니다. 테스트는 쉬움, 보통, 어려움 세 단계이고 문제당 10초가 지나면 다음 문제로 넘어갑니다. 한 판은 10문제이고, 연습 모드는 100문제이면서 중간에 나갈 수 있습니다.

화면을 하나만 두고 만들었습니다. 버튼 16개, 레이블 27개, 시간을 재는 Clock 4개, 효과음 3개가 그 한 화면에 들어가 있고 블록은 1,100개가 넘습니다. 그러다 보니 나중에는 어떤 블록이 어디에 붙어 있는지 찾는 데 시간이 더 걸렸습니다. 모드별로 화면을 나눴어야 했습니다.

<img src="gugudan-trainer/docs/gugudan-app-screenshot.jpg" alt="구구단 트레이너 시작 화면" width="300"/>

### 프로그래밍 언어 비교

C, Java, 파이썬, C++, C# 다섯 가지 언어의 특징을 손으로 정리한 과제입니다. 노트 사진 두 장이 [docs](docs) 에 있습니다.

## 실습

### 인터랙티브 Zoog

Processing으로 만든 480x270 크기의 스케치입니다. 캐릭터 하나를 그려 놓고 마우스에 반응하게 했습니다.

- 몸 전체가 마우스 위치를 따라 움직입니다.
- 두 눈의 색은 마우스 좌표로 정해집니다 (`fill(mouseX % 255, 0, mouseY % 255)`).
- 양팔은 -45도에서 45도 사이를 계속 왕복합니다.
- 화면을 클릭하면 콘솔에 `Take me to your leader!!!` 가 찍힙니다.

팔은 `pushMatrix()` 로 좌표계를 어깨 위치로 옮기고 `rotate()` 를 준 다음 사각형을 그리는 식으로 붙였습니다. 팔 끝 좌표를 삼각함수로 직접 계산하지 않아도 되는 방식이라, 좌표계를 옮겨 놓고 그린다는 개념을 여기서 처음 써 봤습니다.

<img src="interactive-zoog/docs/zoog-screenshot.png" alt="인터랙티브 Zoog 실행 화면" width="600"/>

## 실행 방법

- 구구단 트레이너: [MIT App Inventor](http://ai2.appinventor.mit.edu/) 에서 `gugudan-trainer-app.aia` 를 가져오면 됩니다.
- 인터랙티브 Zoog: [Processing](https://processing.org/download) 에서 `interactive-zoog.pde` 를 열고 실행합니다.
