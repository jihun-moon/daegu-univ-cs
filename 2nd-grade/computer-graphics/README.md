# 🧠 컴퓨터 그래픽스 (Computer Graphics)

**[과목 정보]**
- **수강:** 2학년 2학기
- **언어/라이브러리:** `C++`, `OpenGL`, `GLUT`
- **핵심:** `3D 렌더링 파이프라인`, `선형대수학 응용`, `팀 협업`

---

## 📖 과목 개요 (Overview)
3D 그래픽 객체를 수학적으로 정의하고, 행렬 변환과 조명 계산을 통해 화면에 렌더링하는 전체 파이프라인을 학습한 과목입니다. OpenGL API를 활용해 직접 3D 공간과 객체를 만들고 제어하며, **수학적 모델이 생동감 있는 시각적 결과물로 구현되는 과정**을 깊이 있게 체득했습니다.

이 페이지는 개인의 기초 역량을 다지기 위해 진행했던 주요 실습 과제들을 기록한 아카이브이며, 이 경험들은 아래의 최종 팀 프로젝트를 성공적으로 완성하는 밑거름이 되었습니다.

---

## 🚀 대표 팀 프로젝트: '지진 대피 요령' 3D 시뮬레이션

이 과목의 최종 결과물로, **'3D 지진 대피 안전 교육 시뮬레이션'**을 만드는 팀 프로젝트를 진행했습니다. 저는 팀 내에서 사용자의 시점과 경험을 총괄하는 **인터랙티브 카메라 및 UX 시스템 개발을 전담**했습니다.

> 👇 아래 링크를 클릭하시면 프로젝트의 전체 소스 코드와 저의 구체적인 역할, 기술적인 성장 경험이 담긴 **단독 레포지토리**로 이동합니다.

**➡️ [팀 프로젝트 'opengl-earthquake-simulation' 리포지토리 바로가기](https://github.com/jihun-moon/opengl-earthquake-simulation)**

---

## 🛠️ 개인 실습 아카이브 (Labs Archive)

### 1주차: OpenGL 개발 환경 구축
- **과제:** Visual Studio에서 OpenGL 및 GLUT 라이브러리를 연동하여 그래픽 프로그래밍을 위한 기본 개발 환경을 설정했습니다.
- **결과물:** [assignment-1_setup/](./assignment-1_setup/) 폴더에서 PDF 보고서 확인 가능

<p align="center">
  <img src="./assets/setup-screenshot.png" alt="OpenGL 개발 환경 구축" width="600"/>
  <br/>
  <i>Visual Studio에서 OpenGL 개발 환경을 성공적으로 구축하고 테스트 코드를 실행한 화면</i>
</p>

---

### 3주차: 정점(Vertex)을 이용한 기본 도형 렌더링
- **과제:** `glVertex3f` 함수를 사용하여 2D 좌표 위에 별 모양과 선분을 직접 정의하고 렌더링했습니다.
- **결과물:** [assignment-2_vertex/](./assignment-2_vertex/) 폴더에서 코드 및 결과 이미지 확인 가능

<table>
  <tr>
    <td align="center">
      <img src="./assets/star-rendering.png" alt="별 모양 렌더링 결과" width="420"/>
      <br/>
      <i>GL_LINE_LOOP를 이용해 렌더링한 별 모양</i>
    </td>
    <td align="center">
      <img src="./assets/line-rendering.png" alt="선분 렌더링 결과" width="420"/>
      <br/>
      <i>GL_LINES를 이용해 렌더링한 선분들</i>
    </td>
  </tr>
</table>

---

### 애니메이션 실습: 인터랙티브 애니메이션 구현
- **과제:** 도형의 이동, 확장, 변형 및 사용자 상호작용을 포함하는 애니메이션을 구현했습니다.
- **결과물:** [assignment-3_animation/](./assignment-3_animation/) 폴더에서 전체 소스 코드(`main.cpp`) 확인 가능

**[Demo]**
<p align="center">
  <img src="./assignment-3_animation/demo.gif" alt="인터랙티브 애니메이션 데모" width="600"/>
  <br/>
  <i>키보드와 마우스 입력에 반응하는 인터랙티브 애니메이션</i>
</p>

---
> ↩️ **[전체 학습 로드맵으로 돌아가기](../../README.md)**
