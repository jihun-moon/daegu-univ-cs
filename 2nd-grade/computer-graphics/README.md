# 🧠 컴퓨터 그래픽스 (Computer Graphics)

**[과목 정보]**
- **수강:** 2학년 2학기
- **언어/라이브러리:** `C++`, `OpenGL`, `GLUT`
- **핵심:** `3D 렌더링 파이프라인`, `선형대수학 응용`, `팀 협업`

---

## 📖 학습 목표 및 과정 (Learning Goals & Process)

OpenGL API를 기반으로 **'정적인 3D 객체 렌더링'이라는 개인 역량을 '사용자와 상호작용하는 3D 시뮬레이션 개발'이라는 팀의 성과로 발전**시키는 것을 목표로 한 과정입니다. 개인 실습을 통해 그래픽스 프로그래밍의 기초를 다진 후, 이를 바탕으로 팀원들과 협업하여 복잡한 요구사항을 갖춘 3D 애플리케이션을 완성하는 **단계적(Step-by-Step) 프로젝트**를 수행했습니다.

---

### Step 1. [개인 실습] OpenGL 기초 및 인터랙티브 프로그래밍

- **학습 내용:** 개발 환경 설정부터 시작하여, 정점(Vertex) 데이터로 도형을 렌더링하고, 최종적으로는 사용자 입력에 반응하는 동적 애니메이션을 구현하며 개인의 기초 역량을 확보했습니다. 이 과정은 아래 3단계의 세부 실습으로 구성됩니다.
    1.  **개발 환경 구축:** Visual Studio에서 OpenGL/GLUT 라이브러리를 연동하여 모든 그래픽 프로그래밍의 기반을 마련했습니다. ([결과물](./assignment-1_setup/))
    2.  **정적 도형 렌더링:** `glVertex3f` 함수로 좌표를 직접 제어하며 별, 선분 등 2D 도형을 렌더링했습니다. ([결과물](./assignment-2_vertex/))
    3.  **동적 애니메이션 구현:** `glutKeyboardFunc`, `glutCreateMenu` 등 콜백 함수를 활용해 사용자와 상호작용하는 애니메이션을 구현했습니다. ([결과물](./assignment-3_animation/))

**[주요 실습 결과물 (Key Lab Works)]**
<table>
  <tr>
    <td align="center">
      <img src="./assets/setup-screenshot.png" alt="OpenGL 개발 환경 구축" width="280"/>
      <br/>
      <i>1. 개발 환경 구축</i>
    </td>
    <td align="center">
      <img src="./assets/star-rendering.png" alt="별 모양 렌더링 결과" width="280"/>
      <br/>
      <i>2. 정적 도형 렌더링</i>
    </td>
    <td align="center">
      <img src="./assignment-3_animation/demo.gif" alt="인터랙티브 애니메이션 데모" width="280"/>
      <br/>
      <i>3. 동적 애니메이션</i>
    </td>
  </tr>
</table>

---

### Step 2. [팀 프로젝트] 3D 지진 대피 시뮬레이션 구현

- **과제:** '3D 지진 대피 안전 교육 시뮬레이션' 팀 프로젝트 개발
- **학습 내용:** Step 1에서 습득한 개인 역량을 바탕으로 팀 프로젝트에 기여했습니다. 저는 사용자의 시점과 경험을 총괄하는 **인터랙티브 카메라 및 UX 시스템 개발을 전담**하여, 팀원들이 만든 3D 월드가 효과적인 교육 콘텐츠로 완성되도록 만들었습니다.

> **➡️ [[팀 프로젝트] 3D 지진 대피 시뮬레이션 전체 기록 보러가기](https://github.com/jihun-moon/opengl-earthquake-simulation)**

---
> ↩️ **[전체 학습 로드맵으로 돌아가기](../../README.md)**
