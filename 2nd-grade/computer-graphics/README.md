# 컴퓨터 그래픽스

2학년 컴퓨터 그래픽스 과목에서 작성한 C++ / OpenGL 코드입니다. Visual Studio 프로젝트를 그대로 올려 두었고, 주차별 실습은 `labs/`, 제출한 과제는 `assignments/` 로 나눠 두었습니다.

과목 정리 노트: [컴퓨터 그래픽스 (Notion)](https://www.notion.so/48af0e124a3e4ee69c88dda2a1907bc2?source=copy_link)

## 폴더 구조

```
assignments/  제출 과제 3건 (코드 + 결과물)
labs/         주차별 실습 9개 (Visual Studio 솔루션)
assets/       개발 환경 설정 확인 화면
```

## 실행

각 폴더의 `.sln` 을 Visual Studio 로 열고 Debug / Win32 구성으로 빌드합니다. OpenGL 과 freeglut 이 필요하며, 실습 폴더마다 `freeglut.dll` 을 실행 파일 옆에 같이 넣어 두었습니다. 이 DLL 이 없으면 빌드는 통과해도 실행 단계에서 창이 뜨지 않습니다.

## 과제

### 01. OpenGL 개발 환경 설정

폴더: [`assignments/01-opengl-setup`](assignments/01-opengl-setup)

freeglut 헤더와 라이브러리를 프로젝트에 연결하고, 흰 사각형 하나가 창에 뜨는 것까지 확인하는 과정을 보고서로 정리했습니다. ([report_opengl_setup.pdf](assignments/01-opengl-setup/report_opengl_setup.pdf))

![OpenGL 개발 환경 설정 확인 화면](assets/setup-screenshot.png)

### 02. 정점으로 도형 그리기

폴더: [`assignments/02-vertex-drawing`](assignments/02-vertex-drawing)

- `draw_lines.cpp`: `GL_LINES` 로 평행한 선분 세 개를 그립니다. 정점을 두 개씩 끊어 읽는 방식이라 개수가 홀수면 마지막 정점이 그냥 버려집니다.
- `draw_star.cpp`: `GL_LINE_LOOP` 에 꼭짓점 10개(바깥 5개, 안쪽 5개)를 번갈아 넣어 별을 그립니다. 꼭짓점을 넣는 순서가 곧 선을 잇는 순서라, 하나만 자리를 바꿔도 별이 아니라 엉킨 다각형이 나옵니다.

| 선분 | 별 |
| --- | --- |
| ![선분 세 개](assignments/02-vertex-drawing/results/line-rendering.png) | ![별](assignments/02-vertex-drawing/results/star-rendering.png) |

빌드 로그까지 같이 담은 전체 화면은 [`results/`](assignments/02-vertex-drawing/results) 안에 함께 있습니다.

### 03. 변환을 이용한 애니메이션

폴더: [`assignments/03-animation`](assignments/03-animation)

`glTranslatef` 로 옮기면서 `glScalef` 로 키우는 애니메이션입니다. 40ms 타이머로 갱신하고, 오른쪽 클릭 메뉴에서 삼각형 / 사각형과 크기를 고르며, `f` 와 `s` 키로 이동 속도를 0.01 에서 0.2 사이로 조절합니다. `MyReshape` 에서 종횡비를 계산해 창을 늘려도 도형이 찌그러지지 않게 했습니다.

실행 화면: [demo.gif](assignments/03-animation/results/demo.gif) (13MB 라 링크로 둡니다)

10.07 실습을 다듬어 제출한 것이라 `labs/05-week-1007-animation-basics` 와 코드가 거의 같습니다.

## 주차별 실습

| 폴더 | 날짜 | 내용 |
| --- | --- | --- |
| [`01-week-0909`](labs/01-week-0909) | 09.09 | GLUT 기본 골격. `glutCreateWindow` → `glutDisplayFunc` → `glutMainLoop` 순서와 `GL_POLYGON` 사각형 하나 |
| [`02-week-0916-drawing-basics`](labs/02-week-0916-drawing-basics) | 09.16 | `Line` 과 `Star` 두 솔루션. `GL_LINES` 와 `GL_LINE_LOOP` 의 차이 확인 |
| [`03-week-0923`](labs/03-week-0923) | 09.23 | 마우스 콜백. 클릭 지점과 드래그 지점을 두 꼭짓점 삼아 사각형을 그림. `Q` 와 `ESC` 로 종료 |
| [`04-week-0930`](labs/04-week-0930) | 09.30 | 타이머 콜백(`glutTimerFunc`, 40ms)으로 사각형 이동, 더블 버퍼링 적용. 파일 앞부분에 메뉴 콜백 예제가 주석으로 남아 있습니다 |
| [`04-week-0930-callbacks`](labs/04-week-0930-callbacks) | 09.30 | 같은 날 진행한 콜백 실습문제. 방향키로 사각형 이동, 마우스 왼쪽 클릭으로 종료, 우클릭 메뉴로 이동 켜고 끄기 |
| [`05-week-1007-animation-basics`](labs/05-week-1007-animation-basics) | 10.07 | 이동과 확대를 같이 거는 애니메이션. 메뉴로 도형과 크기 전환, `f` 와 `s` 로 속도 조절 |
| [`06-week-1014`](labs/06-week-1014) | 10.14 | `glRotatef` 뒤에 `glTranslatef` 를 걸어 구를 공전시킴. 16ms 타이머, 한 바퀴 돌 때마다 `rand()` 로 색 변경 |
| [`07-week-1028`](labs/07-week-1028) | 10.28 | 태양계. 수성부터 해왕성까지 공전시키고, 지구 변환 안쪽에 달 변환을 중첩 |
| [`08-week-1125`](labs/08-week-1125) | 11.25 | 극좌표 시점 함수 `PolarView(radius, elevation, azimuth, twist)` 로 와이어프레임 주전자를 돌려 봄. 깊이 테스트 사용 |

번호 `04` 가 두 개인 것은 09.30 한 주에 프로젝트를 두 개 만들었기 때문입니다. 폴더 번호는 주차 번호가 아니라 만든 순서입니다.

## 하면서 걸렸던 것

- **마우스 좌표계** (09.23): 윈도우 좌표는 왼쪽 위가 원점이고 아래로 갈수록 y 가 커지는데 OpenGL 은 반대입니다. `glVertex3f(X / 300.0, (300 - Y) / 300.0, 0.0)` 처럼 y 를 뒤집고 창 크기로 나눠 정규화해야 마우스를 따라옵니다.
- **방향키** (09.30 콜백): 방향키는 `glutKeyboardFunc` 으로 들어오지 않습니다. `glutSpecialFunc` 에 `GLUT_KEY_LEFT` 같은 상수로 따로 받아야 했습니다.
- **행렬 스택** (10.28): 달을 지구 기준으로 돌리려면 지구의 `glPushMatrix` / `glPopMatrix` 안쪽에서 한 번 더 감싸야 합니다. 짝을 하나라도 놓치면 뒤에 그리는 행성이 지구 변환을 그대로 물려받습니다.
- **11.25 타이머의 남은 문제**: `if (angle2 <= 90) angle1 -= 5; else angle2 += 5;` 구조인데 `angle2` 가 0 에서 늘어나질 않아 조건이 항상 참입니다. 결과적으로 `angle1` 만 줄어들어 한 방향으로만 시점이 돕니다. 당시 제출 상태 그대로 두었습니다.
