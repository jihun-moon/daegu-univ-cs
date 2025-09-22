# Pac‑Man (Java, Swing) — Individual Project

객체지향 설계 + 이벤트 기반 GUI + 멀티스레딩을 통합한 Pac‑Man 구현.

## Quick start
# JDK 17+ 권장
# 1) Gradle 사용
./gradlew run

# 2) 순수 javac (소스가 pacman-game/src 아래에 있는 경우)
find pacman-game/src -name "*.java" > sources.txt
javac -d out @sources.txt
# 메인 클래스명으로 교체 (예: com.example.pacman.Main)
java -cp out com.example.pacman.Main
```

## Demo
<img src="assets/pacman-game-screenshot.png" width="420" alt="Pac-Man game screenshot" />

## Key points
- OOP: 엔티티 분리, 인터페이스 기반 설계(캡슐화·상속·다형성)
- GUI: Swing, MVC 분리, EDT 규칙 준수
- Concurrency: 고정 틱 게임 루프, AI 로직 별도 스레드, 안전한 공유 상태

## Directory
```
assets/                  # 스크린샷
pacman-game/             # src/, resources/ 등
README.md
```

## Run options (예시)
- --speed=FAST|NORMAL|SLOW
- --seed=42

## Link
- Notion: 자바 프로그래밍 페이지(https://www.notion.so/052f54c39c5448d8baf1e403326f9e0e)
