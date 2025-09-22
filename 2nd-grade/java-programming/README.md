# Pac-Man Game in Java (Swing)

객체지향 설계 원칙, 이벤트 기반 GUI, 멀티스레딩을 통합하여 Java Swing으로 구현한 개인 프로젝트입니다.

## Demo

<img src="assets/pac-man-game-screenshot.png" width="420" alt="Pac-Man game screenshot" />

## Key Features & Concepts

-   **객체지향 설계 (Object-Oriented Design):**
    -   플레이어, 고스트, 맵 등 게임의 핵심 엔티티를 클래스로 명확히 분리했습니다.
    -   `Movable`, `Renderable` 등의 인터페이스를 기반으로 다형성을 구현하여 코드의 확장성과 유지보수성을 높였습니다.

-   **GUI 및 이벤트 처리 (GUI & Event Handling):**
    -   Java Swing을 사용하여 게임 화면과 UI를 구축했으며, MVC 패턴을 적용하여 게임 로직과 뷰를 분리했습니다.
    -   키보드 이벤트 처리는 Swing의 Event Dispatch Thread (EDT) 규칙을 준수하여 안정적인 상호작용을 보장합니다.

-   **동시성 프로그래밍 (Concurrency):**
    -   고정된 틱(tick) 속도를 유지하는 게임 루프를 메인 스레드에서 관리합니다.
    -   각 고스트의 AI 로직은 별도의 스레드에서 동작하도록 설계하여, 복잡한 연산이 GUI 렌더링을 방해하지 않도록 했습니다.

## Prerequisites

-   JDK 17 또는 그 이상

## Quick Start

### 1. Gradle (Recommended)
프로젝트 루트 디렉터리에서 아래 명령어를 실행하세요.
```bash
./gradlew run
````

\<details\>
\<summary\>\<strong\>2. Javac (Manual Compilation)\</strong\>\</summary\>

소스 파일이 `pacman-game/src` 디렉터리 아래에 있는 경우, 아래와 같이 컴파일하고 실행할 수 있습니다.

```bash
# 1. 모든 Java 소스 파일 목록 생성
find pacman-game/src -name "*.java" > sources.txt

# 2. 소스 파일 컴파일 (out 디렉터리에 .class 파일 생성)
javac -d out @sources.txt

# 3. 프로그램 실행 (메인 클래스명을 정확히 기입해야 함)
# 예: com.example.pacman.Main
java -cp out [메인 클래스명]
```

\</details\>

## Run Options

커맨드라인 인자를 통해 게임의 일부 동작을 제어할 수 있습니다.

  - `--speed=[FAST|NORMAL|SLOW]` : 게임 속도를 조절합니다. (기본값: NORMAL)
  - `--seed=[정수]` : 고스트 AI의 행동 패턴을 위한 랜덤 시드를 지정합니다. (예: `--seed=42`)

## Directory Structure

```
.
├── assets/         # 스크린샷 등 이미지 리소스
├── pacman-game/    # Java 소스 코드 및 리소스 (src/, resources/)
└── README.md       # 현재 파일
```

## Project Documentation

더 상세한 설계 과정 및 학습 노트는 아래 노션 페이지에 기록되어 있습니다.

  - **Notion:** [자바 프로그래밍 학습 노트](https://www.notion.so/052f54c39c5448d8baf1e403326f9e0e)
