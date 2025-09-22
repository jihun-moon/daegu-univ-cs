# Computer Graphics (OpenGL, C++/GLUT)

OpenGL 파이프라인(MVP)과 입력·카메라·애니메이션 기초를 단계별 과제로 정리한 저장소.

## Demo
<table>
<tr>
<td align="center"><strong>Setup (정사각형)</strong></td>
<td align="center"><strong>Vertex - Lines</strong></td>
<td align="center"><strong>Vertex - Star</strong></td>
<td align="center"><strong>Animation (GIF)</strong></td>
</tr>
<tr>
<td><img src="assets/setup-screenshot.png" width="200"/></td>
<td><img src="assets/line-rendering.png" width="200"/></td>
<td><img src="assets/star-rendering.png" width="200"/></td>
<td><img src="assignment-3_animation/demo.gif" width="200"/></td>
</tr>
</table>

## Contents
- assignment-1_setup
  - Visual Studio + GLUT 환경 구성, 정사각형 렌더링
  - 보고서: report_opengl_setup.pdf
- assignment-2_vertex
  - draw_lines.cpp, draw_star.cpp
  - 결과: result_lines.png, result_star.png
- assignment-3_animation
  - main.cpp, demo.gif

## Key Concepts
- OpenGL 파이프라인과 버퍼(VAO/VBO), Immediate 모드 → Modern API로의 이행 기반
- 변환 행렬(M·V·P), 뷰포트, 깊이 테스트·컬링
- GLUT 콜백(키보드·타이머) 기반 인터랙션과 고정 timestep 애니메이션

## Build (Windows, Visual Studio 예시)
- 솔루션 생성 시 문자 인코딩 UTF-8, 멀티바이트 설정
- 링커/추가 종속성에 opengl32.lib, glu32.lib, freeglut.lib 추가
- 실행 파일 작업 디렉터리를 프로젝트 루트로 설정

## Run
- assignment-1_setup/test.cpp 실행 → “Hello, OpenGL!” 윈도우
- assignment-2_vertex/*.cpp 빌드 후 결과 이미지와 일치 확인
- assignment-3_animation/main.cpp 실행 → demo.gif와 동일 동작 확인

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

## Notes
- 솔루션/출력 경로는 학번-이름 규칙 충족 (과제 가이드 준수)
- 결과 스크린샷은 assets 및 각 과제 폴더에 보관

## Documentation
- Notion 과목 정리: 컴퓨터 그래픽스 페이지(https://www.notion.so/3be1e063958c490b8c59646abf021a86?source=copy_link)
