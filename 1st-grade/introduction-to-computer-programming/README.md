# Introduction to Computer Programming — 1학년 실습 모음

Python으로 기본 문법과 GUI 프로그래밍을 연습한 미니 프로젝트 모음입니다. 동일 코어 로직을 GUI(Turtle/Tkinter)와 Console에 분리 적용하는 연습에 중점을 두었습니다.

## 데모 스크린샷
- Angry Turtle: ./assets/angry-turtle-game-screenshot.png
- Ball Simulation (Tkinter): ./assets/ball-simulation-tkinter-screenshot.png

## 프로젝트 목록

| Name | Tech | 한 줄 설명 | 실행 |
|---|---|---|---|
| Angry Turtle | Python, Turtle | 포물선 운동 슈팅. 각도·속도 조절, 충돌 시 스코어 갱신 | `python angry-turtle.py` |
| Ball Simulation | Python, Tkinter | 2D 공 이동·충돌 시뮬레이션. 고정 타임스텝 업데이트 | `python ball-simulation-tkinter.py` |
| Fortress (Console) | Python | 콘솔 기반 캐논 게임. GUI 없이 코어 로직 검증 | `python fortress-game-console.py` |

## 핵심 학습 포인트
- 입력 이벤트와 렌더링/물리 업데이트 분리 (`update()` vs `draw()`)
- 고정 타임스텝(Δt)과 보간으로 시각 떨림 감소
- 순수 함수 기반 코어 로직(예: `step(pos, vel, dt)`)으로 테스트 용이성 확보
- 로직–표현 분리로 동일 로직의 GUI/Console 재사용

## 사용 방법
1) Python 3.9+ 준비
2) 각 스크립트 실행
```bash
# Angry Turtle
python angry-turtle.py

# Ball Simulation (Tkinter)
python ball-simulation-tkinter.py

# Fortress (Console)
python fortress-game-console.py
```

## 조작법 (요약)
- Angry Turtle: ↑/↓ 각도, ←/→ 속도, Space 발사
- Fortress(Console): 안내에 따라 각도·속도 입력

## 디렉터리
```
assets/                         # 스크린샷/이미지
angry-turtle.py                 # 터틀 슈팅
ball-simulation-tkinter.py      # Tkinter 충돌 시뮬
fortress-game-console.py        # 콘솔 버전 캐논 게임
README.md                       # 이 파일
```

## 라이선스 / 사용 범위
- 교육 목적의 예제 코드
- 스크린샷 및 결과물은 개인 포트폴리오 용도 사용

## 링크
- Notion 과목 정리: 소프트웨어 개발 입문
