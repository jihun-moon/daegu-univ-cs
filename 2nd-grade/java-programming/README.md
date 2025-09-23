# 👾 Pac-Man GUI (Java Swing)

> ### 3줄 요약
>
>   - **게임 구현**: Java의 Swing 라이브러리를 사용하여 고전 아케이드 게임 Pac-Man을 구현했습니다.
>   - **핵심 기능**: 각기 다른 AI를 가진 유령, 실시간 충돌 판정, 점수 및 아이템 시스템을 포함합니다.
>   - **객체지향 설계**: 플레이어, 유령, 게임 보드 등 핵심 요소를 클래스로 분리하여 객체지향적으로 설계하고 모듈화했습니다.

-----

## ✨ 주요 기능

  - **플레이어**: 키보드 입력에 따른 상하좌우 이동 및 방향별 애니메이션
  - **유령 AI**: 각 유령(Blinky, Pinky, Inky, Clyde)의 고유한 추격/회피 로직 및 '공포(Scared)' 상태 구현
  - **게임 시스템**: 점수, 파워업 아이템, 생명 시스템 구현
  - **맵 로딩 및 충돌**: 2차원 배열 기반의 맵 로딩 및 벽 충돌 판정
  - **게임 상태 관리**: 일시정지, 게임 오버, 재시작 기능

-----

## 🧱 아키텍처 개요

  - **엔티티 (Entities)**
      - `PacMan`: 위치, 속도, 방향, 아이템 효과 등 플레이어 상태 관리
      - `Ghost` (추상 클래스) → `Blinky`, `Pinky`, `Inky`, `Clyde`, `BossGhost`: 각 유령의 고유한 추격/회피 로직 및 상태 머신(FSM) 구현
      - `GameBoard`: 2D 배열 맵, 충돌 판정, 점수 시스템, 게임 루프 관리
  - **게임 루프 (Game Loop)**
      - `javax.swing.Timer`를 사용하여 주기적으로 게임 상태를 `update`하고 화면을 `repaint` 합니다.
  - **입력 처리 (Input)**
      - `KeyListener`를 통해 사용자의 키보드 입력을 받아 플레이어의 방향 전환 및 게임 상태를 제어합니다.

-----

## ⌨️ 조작법

| 키 | 기능 |
| :--- | :--- |
| **방향키** | 팩맨 이동 |
| **P** | 일시정지 |
| **R** | 게임 재시작 |
| **Q** | 게임 종료 |

-----

## 🚀 실행 방법

### A) JDK로 직접 실행

프로젝트의 상위 폴더에서 아래 명령어를 순서대로 실행합니다.

```bash
# 1. 모든 .java 파일 컴파일 (UTF-8 인코딩)
javac -encoding UTF-8 pacman/*.java

# 2. 메인 게임 실행
java pacman.MainGame
```

### B) Gradle로 실행 (선택 사항)

`build.gradle` 파일에 아래 내용을 추가하고 `gradle run` 명령어로 실행할 수 있습니다.

```groovy
plugins { id 'application' }
repositories { mavenCentral() }
application { mainClass = 'pacman.MainGame' }
tasks.withType(JavaCompile).configureEach {
    options.encoding = 'UTF-8'
    sourceCompatibility = '17'
    targetCompatibility = '17'
}
```

-----

## 🧠 유령 AI 상세

| 유령 | 성격 (타겟 설정 방식) |
| :--- | :--- |
| **Blinky** | **추격 (Chase)**: 플레이어의 현재 위치를 직접 타겟으로 설정합니다. |
| **Pinky** | **매복 (Ambush)**: 플레이어의 현재 이동 방향 4칸 앞을 타겟으로 설정합니다. |
| **Inky** | **변칙 (Erratic)**: Blinky와 플레이어의 위치를 조합하여 예측 불가능한 타겟을 설정합니다. |
| **Clyde** | **변덕 (Whimsical)**: 플레이어와의 거리에 따라 추격 또는 회피 모드를 전환합니다. |

-----

## ✅ 개발 체크리스트

  - [ ] FPS 안정화와 `Timer` 드리프트 보정
  - [ ] 벽 통과(터널) 로직 처리
  - [ ] '공포(Scared)' 모드 타이머 및 유령 색상 반전 구현
  - [ ] 게임 오버 및 재시작 루틴 안정화

-----

## 🪪 라이선스

이 프로젝트는 [MIT 라이선스](https://opensource.org/licenses/MIT)를 따릅니다.
