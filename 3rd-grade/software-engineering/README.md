# 소프트웨어공학 (Software Engineering)

3학년 2학기 소프트웨어공학 수업의 UML 설계 실습입니다. 유스케이스 다이어그램으로 시작해 클래스 다이어그램, 순차 다이어그램 순서로 진행했고, 마지막 두 주차에는 하나의 시스템을 세 종류 다이어그램으로 함께 설계했습니다.

원본은 StarUML 프로젝트 파일(`.mdj`)이고, 내보낸 PNG 를 주차별 `assets/` 에 넣어 뒀습니다. `.mdj` 는 StarUML 에서 열어야 보입니다.

## 폴더 구조

- `labs/` 주차별 실습. 주차마다 `.mdj` 원본과 `assets/` PNG 가 있고, 코드가 필요한 주차에는 `src/` 가 붙습니다.

## 주차별 실습

| 주차 | 다이어그램 | 대상 |
| --- | --- | --- |
| [W02](labs/W02_use-case-diagram) | 유스케이스 | 비디오 대여 시스템(고객, 관리자, 카드승인시스템), 재고 입출고 시스템(입출고담당자, 현황관리담당자, 쇼핑몰시스템) |
| [W03](labs/W03_class-diagram) | 클래스 | Account 와 Application, 배구 경기(VolleyBallPlayer 와 Libero·Setter·Left·Right·Center1·Center2, Point·Set·Game) |
| [W04](labs/W04_association-and-inheritance) | 클래스 + 자바 | 상속과 연관. `Person` 을 `Student` 와 `Professor` 가 상속 |
| [W05~09](labs/W05~09_class-relationships-implementation) | 클래스 + 자바 | 일반화·연관·의존. Person 예제, `Car` 를 상속하는 `Gcompany`·`Hcompany`·`Scompany` 와 이들을 쓰는 `Company`, `Applet` 을 상속한 `MyApplet` 에 `Graphics` 가 의존하는 예제 |
| [W11](labs/W11_Sequence-Diagram) | 순차 | 스마트 도어락. 카드 인식과 비밀번호 입력 두 갈래 |
| [W12](labs/W12_Sequence_Diagram_Examples) | 순차 | 전화 통화, 엘리베이터 제어, 영화 사이트 로그인, 주문 시스템 |
| [W13](labs/W13_VendingMachine_UML_Design) | 유스케이스 + 클래스 + 순차 | 자판기. 돈 투입, 음료 선택, 잔액 반환과 표시 |
| [W14](labs/W14_ImBank_Agent_UML_Design) | 유스케이스 + 클래스 + 순차 | SecureFlow 보안 로그 분석 시스템 |

## W14 SecureFlow 설계

마지막 과제는 따로 만들고 있던 IM Bank 보안 로그 분석 에이전트를 대상으로 잡았습니다. 유스케이스 17개와 액터 3명(External System, LLM Service, Security Engineer), 클래스 9개, 라이프라인 8개를 하나의 `.mdj` 안에 넣었습니다.

클래스를 그려 놓고 보니 `LogAnalyzer` 하나가 전처리, PII 탐지, 규칙 기반 위험도 판단, Garbage 필터링, 학습 후보 판정을 전부 들고 있었습니다. 그래서 LLM 호출은 `LlmClient` 로 빼고 `SolarLlmClient` 가 상속하게 한 다음, `LogAnalyzer` 에서는 의존 관계로만 연결했습니다. 코드만 볼 때는 이 덩어리가 큰 줄 몰랐습니다.

순차 다이어그램의 흐름은 ExternalSystem 이 로그를 보내면 Ingest_Webhook 이 받아 `LogAnalyzer` 로 넘기고, `LlmClient` 위험도 평가와 `KbService` 유사도 비교를 거쳐 저장한 뒤 `MetricsService` 와 대시보드로 스트림이 나가는 순서입니다.

![SecureFlow 시스템 클래스 다이어그램](labs/W14_ImBank_Agent_UML_Design/assets/SecureFlow%20시스템%20클래스%20다이어그램.png)

![SecureFlow 로그 분석 순차 다이어그램](labs/W14_ImBank_Agent_UML_Design/assets/SecureFlow%20로그%20분석%20순차%20다이어그램.png)

구현 쪽 저장소는 [jihun-moon/im-bank-n8n-agent](https://github.com/jihun-moon/im-bank-n8n-agent) 입니다.

## 자바 코드

W05~09 의 `src/` 는 클래스 다이어그램에서 StarUML 코드 생성으로 뽑은 골격에 본문을 채운 것입니다. `// TODO implement here` 주석과 빈 기본 생성자가 그대로 남아 있는 이유입니다.

생성된 [`src/Person/Association.java`](labs/W05~09_class-relationships-implementation/src/Person/Association.java) 는 `main` 의 인자가 `String args` 로 나와서 그대로는 실행되지 않습니다. 손으로 쓴 [W04 쪽 `Association.java`](labs/W04_association-and-inheritance/src/Association.java) 는 `String[] args` 로 되어 있습니다. 생성된 코드를 확인 없이 쓰면 안 된다는 걸 여기서 봤습니다.

## 수업 노트

개념 정리는 노션에 따로 두었습니다. [소프트웨어공학 노트](https://www.notion.so/1af694381add4c9d98c11a0e5e9ec823?source=copy_link)
