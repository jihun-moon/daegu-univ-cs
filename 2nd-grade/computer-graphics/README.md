# Computer Graphics (OpenGL, C++/GLUT)

OpenGL 파이프라인(MVP)과 입력·애니메이션을 단계별 과제로 정리.

## Demo
<table>
<tr>
<td align="center"><strong>Setup</strong></td>
<td align="center"><strong>Lines</strong></td>
<td align="center"><strong>Star</strong></td>
<td align="center"><strong>Animation</strong></td>
</tr>
<tr>
<td><img src="assets/setup-screenshot.png" width="200"/></td>
<td><img src="assets/line-rendering.png" width="200"/></td>
<td><img src="assets/star-rendering.png" width="200"/></td>
<td><img src="assignment-3_animation/demo.gif" width="200"/></td>
</tr>
</table>

## Contents
- assignment-1_setup: VS + GLUT 환경 구성, 정사각형 렌더링
- assignment-2_vertex: draw_lines.cpp, draw_star.cpp
- assignment-3_animation: main.cpp, demo.gif

## Key Concepts
- VAO/VBO, 셰이더, 뷰포트/깊이 테스트/컬링
- 모델·뷰·프로젝션(M·V·P) 변환
- GLUT 콜백(키보드/타이머), 고정 timestep 애니메이션

## Build (Windows, Visual Studio)
- 링커 종속성: opengl32.lib, glu32.lib, freeglut.lib
- 실행 작업 디렉터리: 프로젝트 루트

## Run
- assignment-1_setup/test.cpp 실행 → “Hello, OpenGL!”
- assignment-2_vertex/*.cpp 빌드 → result_* 이미지와 일치 확인
- assignment-3_animation/main.cpp 실행 → demo.gif 동작 확인

## Directory
```
.
├── assets/
│   ├── setup-screenshot.png
│   ├── line-rendering.png
│   └── star-rendering.png
├── assignment-1_setup/
│   ├── test.cpp
│   └── report_opengl_setup.pdf
├── assignment-2_vertex/
│   ├── draw_lines.cpp
│   ├── draw_star.cpp
│   ├── result_lines.png
│   └── result_star.png
├── assignment-3_animation/
│   ├── main.cpp
│   └── demo.gif
└── README.md
```

## Documentation
- Notion: 컴퓨터 그래픽스 페이지[[1]](https://www.notion.so/3be1e063958c490b8c59646abf021a86)
