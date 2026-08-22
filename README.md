# 대구대학교 컴퓨터소프트웨어 전공 아카이브

대구대학교 컴퓨터소프트웨어 전공 4년 동안 제출한 실습, 과제, 프로젝트 코드를 모아 둔 저장소입니다.
수업에서 만든 결과물을 학년과 과목 단위로 남겨 둔 기록이라, 완성된 서비스보다는 배우는 과정에 가깝습니다.

완성해서 배포한 프로젝트는 이 저장소가 아니라 [별도 저장소](#만든-프로젝트)에 있습니다.

## 사용한 언어

<img src="https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=java&logoColor=white"/>
<img src="https://img.shields.io/badge/Python-3776AB?style=for-the-badge&logo=python&logoColor=white"/>
<img src="https://img.shields.io/badge/C-A8B9CC?style=for-the-badge&logo=c&logoColor=white"/>
<img src="https://img.shields.io/badge/C++-00599C?style=for-the-badge&logo=cplusplus&logoColor=white"/>
<img src="https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black"/>
<img src="https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=html5&logoColor=white"/>
<img src="https://img.shields.io/badge/CSS3-1572B6?style=for-the-badge&logo=css3&logoColor=white"/>
<img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white"/>
<img src="https://img.shields.io/badge/Jupyter-F37626?style=for-the-badge&logo=jupyter&logoColor=white"/>

같이 쓴 도구는 Android Studio (Gradle Kotlin DSL), Unreal Engine, OpenGL, Processing, Git LFS, Linux 입니다.

GitHub 가 집계한 언어 비율은 아래와 같습니다.

| 언어 | 비율 |
| --- | --- |
| Jupyter Notebook | 92.5% |
| HTML | 4.0% |
| Java | 2.3% |
| Python | 0.7% |
| C++ | 0.3% |
| 기타 (CSS, JavaScript, C, Shell, Processing) | 0.2% |

Jupyter 비중이 이렇게 큰 이유는 노트북 파일이 실행 결과 이미지를 본문에 그대로 품고 있기 때문입니다.
노트북 자체는 25개뿐이고, 3학년 데이터마이닝 18개와 4학년 생성형AI 6개, 머신러닝 1개가 전부입니다.
파일 개수로 보면 실제 무게중심은 3학년 쪽에 있습니다.

## 학년별 규모

| 학년 | 과목 | 파일 수 | 주로 다룬 것 |
| --- | --- | --- | --- |
| [1학년](1st-grade/) | 2 | 14 | Python 기초, Processing, 앱 인벤터 |
| [2학년](2nd-grade/) | 7 | 689 | C, C++/OpenGL, Java, 자료구조와 알고리즘 |
| [3학년](3rd-grade/) | 8 | 5,562 | 안드로이드, 웹, 데이터마이닝, MySQL, 언리얼 |
| [4학년](4th-grade/) | 3 | 75 | 머신러닝, 생성형 AI, 종합설계 |

3학년만 5,562개인 것은 게임프로그래밍 때문입니다.
언리얼 프로젝트를 통째로 올리면서 `.uasset` 3,601개와 `.umap` 24개가 들어갔고, 저장소가 1.3GB 가 된 것도 여기서 나왔습니다.
바이너리라 diff 가 안 잡히길래 이때 Git LFS 를 처음 붙여 봤습니다.

## 폴더 구조

```
daegu-univ-cs/
├─ 1st-grade/
│  ├─ introduction-to-computer-programming/
│  └─ introduction-to-software-development/
├─ 2nd-grade/
│  ├─ algorithm/
│  ├─ c-programming/
│  ├─ computer-graphics/
│  ├─ computer-networks/
│  ├─ data-structures/
│  ├─ discrete-mathematics/
│  └─ java-programming/
├─ 3rd-grade/
│  ├─ app-programming/
│  ├─ data-mining/
│  ├─ database/
│  ├─ game-programming/
│  ├─ server-system-administration/
│  ├─ software-engineering/
│  ├─ systems-software-practice/
│  └─ web-programming/
└─ 4th-grade/
   ├─ generative-ai/
   ├─ machine-learning/
   └─ software-capstone-design/
```

## 과목 목록

### 1학년

| 과목 | 파일 | 내용 |
| --- | --- | --- |
| [introduction-to-computer-programming](1st-grade/introduction-to-computer-programming/) | 7 | Python turtle, tkinter, 콘솔 게임 |
| [introduction-to-software-development](1st-grade/introduction-to-software-development/) | 7 | Processing 스케치, 앱 인벤터 구구단 앱 |

### 2학년

| 과목 | 파일 | 내용 |
| --- | --- | --- |
| [computer-graphics](2nd-grade/computer-graphics/) | 368 | C++와 OpenGL 실습 (Visual Studio 프로젝트 포함) |
| [java-programming](2nd-grade/java-programming/) | 277 | Java 문법과 객체지향 실습 |
| [data-structures](2nd-grade/data-structures/) | 20 | Python 으로 구현한 자료구조 |
| [c-programming](2nd-grade/c-programming/) | 9 | C 기초 실습 |
| [algorithm](2nd-grade/algorithm/) | 7 | 정렬과 탐색 등 알고리즘 구현 |
| [discrete-mathematics](2nd-grade/discrete-mathematics/) | 5 | 이산수학 과제 |
| [computer-networks](2nd-grade/computer-networks/) | 3 | 네트워크 과제 |

### 3학년

| 과목 | 파일 | 내용 |
| --- | --- | --- |
| [game-programming](3rd-grade/game-programming/) | 3,710 | 언리얼 프로젝트 2개 (Apartment_Cinematic, Shooting_Project) |
| [web-programming](3rd-grade/web-programming/) | 901 | HTML, CSS, JavaScript 주차별 실습 |
| [app-programming](3rd-grade/app-programming/) | 798 | 안드로이드 실습 (Java + Gradle Kotlin DSL) |
| [database](3rd-grade/database/) | 54 | MySQL 챕터별 쿼리 실습과 Python 연동 과제 |
| [software-engineering](3rd-grade/software-engineering/) | 53 | 요구사항과 설계 문서 |
| [data-mining](3rd-grade/data-mining/) | 32 | Numpy, Pandas, scikit-learn 노트북 18개 |
| [systems-software-practice](3rd-grade/systems-software-practice/) | 11 | 리눅스 명령어 정리와 실습 |
| [server-system-administration](3rd-grade/server-system-administration/) | 3 | 서버 관리 실습 노트 |

### 4학년

| 과목 | 파일 | 내용 |
| --- | --- | --- |
| [machine-learning](4th-grade/machine-learning/) | 56 | 1~14주차 실습 노트북과 데이터셋 |
| [generative-ai](4th-grade/generative-ai/) | 16 | 오토인코더, VAE, GAN, DCGAN 노트북 |
| [software-capstone-design](4th-grade/software-capstone-design/) | 3 | 종합설계 과목 자료 |

## 학습 노트

코드는 여기에 두고, 개념 정리와 수업 노트는 Notion 에 따로 씁니다.

- [My Knowledge Hub](https://www.notion.so/My-Knowledge-Hub-27772d9f979f80569662de9c2e49399d?pvs=21)
- [Knowledge Base (과목별 노트)](https://www.notion.so/27772d9f979f8008bf02fd7de58885b4?pvs=21)

## 만든 프로젝트

수업 밖에서 만든 것들은 저장소를 따로 두고 있습니다.

| 저장소 | 내용 |
| --- | --- |
| chrono (비공개) | 암호화폐 시세 분석 및 신호 서비스 |
| [im-bank-n8n-agent](https://github.com/jihun-moon/im-bank-n8n-agent) | n8n + Node.js + Upstage Solar 로 만든 보안 로그 개인정보 유출 탐지 워크플로 |
| [PII-Guardian](https://github.com/jihun-moon/PII-Guardian) | 개인정보 탐지 NER 모델, 네이버 클라우드 배포와 재학습 루프 |
| [battle-rogue](https://github.com/jihun-moon/battle-rogue) | 언리얼 엔진 5 데디케이티드 서버 기반 1대1 온라인 대전 게임 |
| [opengl-earthquake-simulation](https://github.com/jihun-moon/opengl-earthquake-simulation) | C++와 OpenGL 로 만든 지진 대피 훈련 시뮬레이터 |
| [mobile-doctor-app](https://github.com/jihun-moon/mobile-doctor-app) | 위치 기반 병원 검색과 진료 기록 관리 안드로이드 앱 |
| [Edu-Bridge-Library](https://github.com/jihun-moon/Edu-Bridge-Library) | 2025 도서관 데이터 활용 공모전 제안서 |
| [eth-autotrade-bot](https://github.com/jihun-moon/eth-autotrade-bot) | ETH 선물 15분봉 자동매매 봇 (전략 교체 구조 실험) |
| [Lecture-Summarizer-AI](https://github.com/jihun-moon/Lecture-Summarizer-AI) | Whisper 로 강의 음성을 받아쓰고 요약하는 도구 |

프로필: [github.com/jihun-moon](https://github.com/jihun-moon)

## 라이선스

MIT ([LICENSE](LICENSE))
