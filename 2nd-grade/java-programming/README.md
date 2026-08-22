# 자바 프로그래밍

자바 프로그래밍 수업의 주차별 실습과 기말 프로젝트입니다. Eclipse 프로젝트 단위로 올려 두어서 폴더마다 `.classpath`, `src/`, `bin/` 이 같이 들어 있습니다.

과목 노트는 노션에 있습니다: [자바 프로그래밍](https://www.notion.so/52d43a800b744cffbf32f67c43e75e8a?source=copy_link)

## 폴더 구조

- `final-project-pacman/` 기말 프로젝트로 만든 Swing 팩맨 게임
- `labs/` 주차별 실습 코드

## 기말 프로젝트: 팩맨

<img src="./assets/pacman-game-screenshot.png" alt="Pacman Game Screenshot" width="50%">

[final-project-pacman](./final-project-pacman) · 시작점은 `src/pacman/MainGame.java` 의 `main` 입니다.

클래스 구성은 이렇습니다.

| 클래스 | 역할 |
| --- | --- |
| `MainGame` | `JFrame` 상속. 방향키 입력을 받고 타이머 네 개(팩맨 150ms, 유령 220ms, 보스 350ms, 라운드 확인)로 게임 루프를 돌립니다. 시작 전 카운트다운도 여기 있습니다. |
| `GameBoard` | `JPanel` 상속. 20행 21열 `int` 배열이 맵이고 0은 길, 1은 벽, 2는 파워업입니다. 셀 하나가 24픽셀입니다. 렌더링과 아이템·점수 처리를 맡습니다. |
| `PacMan` | 위치, 방향, 점수, 생명 3개, 무적·속도·혼란 아이템 효과를 들고 있습니다. |
| `Ghost` | 유령 추상 클래스. 이동 가능 판정, 무작위 이동, 팩맨에게서 멀어지는 이동, 공포·혼란 상태를 공통으로 처리합니다. |
| `Blinky` `Pinky` `Inky` `Clyde` | `Ghost` 를 상속한 유령 네 마리. 추적 방식이 서로 다릅니다. |
| `BossGhost` | 점수가 오르면 등장하는 보스. 순간이동을 하고 파워업을 먹어도 공포 상태가 되지 않습니다. |

유령 성격은 원작을 참고해서 나눴습니다. Blinky는 A*로 팩맨까지 최단 경로를 잡고, Pinky는 팩맨의 진행 방향 4칸 앞을 목표로 잡습니다. Inky는 Blinky의 좌표를 같이 참조하고, Clyde는 거리 8칸을 기준으로 추적과 배회를 바꿉니다. 무작위 이동 확률도 Pinky 20%, Inky 30%, Clyde 60%로 다르게 줬습니다.

아쉬운 부분도 남아 있습니다. A* 탐색과 `Node` 클래스를 Blinky, Pinky, Inky, Clyde, BossGhost 다섯 곳에 똑같이 복사해 뒀습니다. `Ghost` 로 올렸으면 한 벌로 끝났을 코드입니다.

## 주차별 실습

| 주차 | 날짜 | 내용 |
| --- | --- | --- |
| [01주](./labs/01-week-20240910-introduction) | 24.09.10 | 첫 시간. `MyInfoApp` 으로 `println` 과 `print` 차이 확인 |
| [04주](./labs/04-week-20240930-basics) | 24.09.30 | 변수, 연산자, `if`/`switch`, `while`. 실습은 가위바위보, 지구와 별 거리, 직사각형 둘레와 면적 |
| [05주](./labs/05-week-20241001-arrays-and-exceptions) | 24.10.01 | 배열, for-each, `break`/`continue`, 예외 처리. 실습은 약수 계산, 월의 일수, 숨겨진 카드 맞추기 |
| [06주](./labs/06-week-20241008-classes-and-objects) | 24.10.08 | 클래스와 객체, 배열 매개변수. 실습은 주사위 던지기, 지뢰찾기, 단어 게임 |
| [07주](./labs/07-week-20241015-inheritance-basics) | 24.10.15 | 상속 기초. `Point` 와 `ColorPoint` 를 한 파일에 두고 실습 |
| [08주](./labs/08-week-20241029-advanced-inheritance) | 24.10.29 | 업캐스팅, 추상 클래스, 인터페이스. 실습은 `OperateCar` 인터페이스를 구현하는 자율 주행 자동차 |
| [09주](./labs/09-week-20241105-packages-and-modules) | 24.11.05 | 패키지와 모듈. `lib.Calculator` 를 `app.GoodCalc` 에서 가져다 쓰기, `StringBuffer`, `StringTokenizer`, 박싱과 언박싱 |
| [10주](./labs/10-week-20241112-collections-and-generics) | 24.11.12 | 컬렉션과 제네릭. `ArrayList`, `Vector`, `HashMap`, `Iterator`. 실습은 카드 게임(`Card`, `Deck`, `Player`) |
| [11주](./labs/11-week-20241119-gui-basics) | 24.11.19 | Swing 기초. `JFrame` 과 패널 배치. 실습은 피자 주문 화면, 계산기 화면, 온도 변환 |
| [12주](./labs/12-week-20241126-event-handling) | 24.11.26 | 이벤트 처리. 키·마우스 리스너, 익명 클래스 리스너, 애니메이션, 트리 그리기 |

02주와 03주, 13주 이후는 실습 코드가 남아 있지 않습니다.

## 실행

Eclipse에서 폴더를 프로젝트로 가져오면 바로 실행됩니다. `.classpath` 는 JavaSE-22로 잡혀 있고, 팩맨의 `MainGame` 에서 화살표 `switch` 문법을 써서 옛 JDK로는 컴파일되지 않습니다.

터미널에서 팩맨을 실행할 때는 이미지 리소스를 클래스패스에 같이 넣어야 합니다.

```bash
cd final-project-pacman
javac -d bin -sourcepath src src/pacman/MainGame.java
cp -r src/resources bin/resources
java -cp bin pacman.MainGame
```
