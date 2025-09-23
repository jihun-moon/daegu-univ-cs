# 🎨 OpenGL 렌더링·애니메이션 실습

> ### 3줄 요약
>
>   - **단계별 실습**: OpenGL + GLUT 환경 설정부터 기본 도형 렌더링, 변환 행렬, 타이머 기반 애니메이션까지 단계적으로 실습합니다.
>   - **핵심 기능 구현**: 선분과 별 그리기를 통해 정점(Vertex) 처리 방식을 익히고, 타이머 콜백을 이용해 이동/스케일 애니메이션을 구현합니다.
>   - **통합 문서**: 각 과제별 소스 코드와 결과 스냅샷, 데모 GIF를 포함하여 전체 개발 과정을 기록합니다.

-----

## 📁 프로젝트 구조

```
computer-graphics/
├── README.md
├── assets/                      # 대표 스크린샷 및 데모 GIF
│   ├── setup-screenshot.png
│   ├── line-rendering.png
│   └── star-rendering.png
├── assignment-1_setup/
│   └── report_opengl_setup.pdf  # 환경 설정 리포트
├── assignment-2_vertex/
│   ├── draw_lines.cpp
│   ├── draw_star.cpp
│   ├── result_lines.png
│   └── result_star.png
└── assignment-3_animation/
    ├── main.cpp
    └── demo.gif
```

-----

## 🧰 환경 설정 및 빌드

  - **언어/라이브러리**: C++17, OpenGL, GLUT (또는 FreeGLUT)
  - **Windows**: Visual Studio 또는 MinGW (g++)
  - **macOS / Linux**: g++ 또는 clang

#### g++ 빌드 예시

`bin` 디렉터리를 생성한 후, 각 실습 코드를 컴파일합니다.

```bash
mkdir -p bin

# 2) 정점 렌더링 실습
g++ assignment-2_vertex/draw_lines.cpp -o bin/draw_lines -lglut -lGL -lGLU
g++ assignment-2_vertex/draw_star.cpp  -o bin/draw_star  -lglut -lGL -lGLU

# 3) 변환·애니메이션 실습
g++ assignment-3_animation/main.cpp -o bin/anim -lglut -lGL -lGLU
```

-----

## 1\. 과제 1 — 환경 설정 (Setup)

  - **목표**: C++ 컴파일러와 OpenGL/GLUT 개발 환경을 구성하고, “Hello, OpenGL\!” 창을 띄워 환경 설정을 검증합니다.
  - **제출물**: `assignment-1_setup/report_opengl_setup.pdf`
  - **체크리스트**:
      - [x] 라이브러리 설치 및 IDE 링커/인클루드 경로 설정
      - [x] 샘플 코드 빌드 및 실행 화면 캡처

\<img src="assets/setup-screenshot.png" alt="Setup Screenshot" width="600"/\>

-----

## 2\. 과제 2 — 정점 렌더링 (Vertex)

  - **목표**: `glBegin`, `glEnd`, `glVertex` 함수를 사용하여 2D 기본 도형을 렌더링하며 그래픽스 파이프라인의 기초를 이해합니다.
  - **소스 코드**: `assignment-2_vertex/draw_lines.cpp`, `draw_star.cpp`

#### 실행 방법

```bash
./bin/draw_lines
./bin/draw_star
```

#### 결과 예시

| 선 그리기 | 별 그리기 |
| :---: | :---: |
| \<img src="assets/line-rendering.png" width="300"/\> | \<img src="assets/star-rendering.png" width="300"/\> |

-----

## 3\. 과제 3 — 변환·애니메이션 (Animation)

  - **목표**: 변환 행렬(이동, 회전, 크기 조절)과 타이머 콜백 함수를 사용하여 동적인 2D 애니메이션을 구현합니다.
  - **소스 코드**: `assignment-3_animation/main.cpp`

#### 핵심 API

  - **변환**: `glTranslatef`, `glRotatef`, `glScalef`, `glPushMatrix`, `glPopMatrix`
  - **타이머**: `glutTimerFunc(interval_ms, callback, value)`
  - **더블 버퍼링**: `glutSwapBuffers()`

#### 실행 방법

```bash
./bin/anim
```

#### 데모

\<img src="assignment-3\_animation/demo.gif" alt="Animation Demo" width="400"/\>

-----

## 🔧 문제 해결 가이드

  - **컴파일 오류**: 라이브러리 링크 순서(`-lglut -lGL -lGLU`) 또는 경로 설정을 확인하세요.
  - **빈 화면 오류**: `glClearColor`, `glClear` 호출 및 `glutDisplayFunc` 콜백 등록 여부를 확인하세요.
  - **화면 깜빡임**: `GLUT_DOUBLE` 버퍼 모드를 활성화하고, 렌더링 마지막에 `glutSwapBuffers()`를 호출했는지 확인하세요.
