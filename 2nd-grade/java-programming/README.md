# Pac‑Man Game in Java (Swing)

객체지향 설계 원칙, 이벤트 기반 GUI, 멀티스레딩을 통합하여 Java Swing으로 구현한 개인 프로젝트입니다.

## Demo
<img src="assets/pacman-game-screenshot.png" width="420" alt="Pac-Man game screenshot" />

## Key Features & Concepts
- 객체지향 설계(OOD)
  - 플레이어, 고스트, 맵 등 핵심 엔티티를 클래스로 분리
  - Movable, Renderable 인터페이스로 다형성 구현 → 확장성과 테스트 용이성 확보
- GUI & Event Handling
  - Swing 기반 화면 구성, MVC 분리
  - 키 입력 처리는 EDT 규칙 준수로 안정적 상호작용 보장
- Concurrency
  - 고정 tick 루프(메인 스레드)
  - 각 고스트 AI는 별도 스레드에서 동작하여 렌더링과 분리

## Prerequisites
- JDK 17+

## Quick Start

### 1) Gradle (권장)
```bash
./gradlew run
```

### 2) Javac (수동 컴파일, 소스가 pacman-game/src 아래에 있는 경우)
```bash
# 1. 모든 Java 소스 파일 목록 생성
find pacman-game/src -name "*.java" > sources.txt

# 2. 컴파일 (out 디렉터리에 .class 생성)
javac -d out @sources.txt

# 3. 실행 (메인 클래스명으로 교체: 예 com.example.pacman.Main)
java -cp out com.example.pacman.Main
```

## Run Options (예시)
- --speed=FAST|NORMAL|SLOW  기본: NORMAL
- --seed=42                 고스트 AI 랜덤 시드

## Directory Structure
```
.
├── assets/         # 스크린샷 등 이미지 리소스
├── pacman-game/    # Java 소스 코드 및 리소스 (src/, resources/)
└── README.md       # 이 파일
```

## Project Documentation
더 자세한 설계와 학습 노트는 노션 문서를 참고하세요.
- Notion: https://www.notion.so/052f54c39c5448d8baf1e403326f9e0e
