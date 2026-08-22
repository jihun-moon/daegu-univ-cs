# 앱 프로그래밍

3학년 1학기 앱 프로그래밍 수업에서 만든 안드로이드 앱을 모아 둔 폴더입니다. 주차별 실습 11개와 제출 과제 3개가 들어 있습니다.

| 항목 | 내용 |
| --- | --- |
| 언어 | Java (Android View 시스템, XML 레이아웃) |
| 빌드 | Gradle Kotlin DSL (`build.gradle.kts`), 버전 카탈로그 `gradle/libs.versions.toml` |
| SDK | compileSdk 35 / minSdk 34 / targetSdk 35, Java 11 |
| 도구 | Android Studio, 각 프로젝트에 Gradle Wrapper 포함 |

수업 중에 정리한 개념 노트는 [노션 앱 프로그래밍 페이지](https://www.notion.so/ebe3da9046d145d49526c84725f802ba)에 있습니다.

## 폴더 구조

```
app-programming/
├── assignments/   제출 과제 3개
├── labs/          주차별 실습 11개
└── assets/        README에서 쓰는 실행 화면 캡처
```

`labs` 아래는 Gradle Wrapper까지 들어 있는 완전한 프로젝트라 Android Studio에서 바로 열립니다. `assignments` 아래는 제출용으로 `MainActivity.java`와 레이아웃 XML만 뽑아 둔 것이라 그 자체로는 빌드되지 않습니다. 세 과제 모두 같은 코드가 아래 실습 폴더에 프로젝트 형태로 남아 있어서, 돌려 보려면 그쪽을 열면 됩니다.

## 과제

### [01. EditText 실습](assignments/01_EditText-Practice/)

아이디, 비밀번호, 전화번호를 EditText 세 개로 받아 버튼을 누르면 TextView에 한 번에 출력합니다. 실행 가능한 프로젝트는 [labs/02_2025-03-18/EditText_IPP](labs/02_2025-03-18/EditText_IPP/)입니다.

<img src="assets/EditText_IPP%20%EA%B2%B0%EA%B3%BC.png" alt="EditText 실습 실행 화면" width="300">

### [02. 계산기와 주사위 앱](assignments/02_Calcul-and-Dice-Apps/)

- `Calcul_Project`: 숫자 버튼으로 수식을 만들고 `=`를 누르면 결과를 아래 TextView에 보여 주는 계산기
- `DIce_Project`: 버튼 한 번에 주사위 두 개를 굴려 눈에 맞는 이미지를 띄우는 앱

실행 가능한 프로젝트는 [labs/04_2025-03-25](labs/04_2025-03-25/)에 있습니다.

### [03. 로그인 화면과 계산기](assignments/03_Login-and-Calculator/)

중간고사 과제입니다.

- `Login_Screen`: 로고, 아이디와 비밀번호 입력란, 로그인과 회원가입 버튼을 배치하고 입력값을 아래에 출력
- `Calcu_lator`: 두 수를 받아 사칙연산 버튼으로 계산. 빈 입력, 숫자가 아닌 입력, 0으로 나누기를 각각 다른 메시지로 처리

실행 가능한 프로젝트는 [labs/07_2025_04_10](labs/07_2025_04_10/)에 있습니다.

| 로그인 화면 실행 | 계산기 실행 |
| --- | --- |
| <img src="assets/%ED%9A%8C%EC%9B%90%EA%B0%80%EC%9E%85%EB%A1%9C%EA%B7%B8%EC%9D%B8%20%ED%99%94%EB%A9%B4.png" alt="로그인 화면 실행 결과" width="280"> | <img src="assets/%EA%B3%84%EC%82%B0%EA%B8%B0%20%ED%99%94%EB%A9%B4.png" alt="계산기 실행 결과" width="280"> |

레이아웃 에디터에서 잡은 로그인 화면의 디자인 뷰와 블루프린트 뷰입니다.

<img src="assets/login-ui-design.png" alt="로그인 화면 레이아웃 에디터" width="520">

## 주차별 실습

| 주차 | 폴더 | 프로젝트 | 내용 |
| --- | --- | --- | --- |
| 01 | [labs/01_2025-03-13](labs/01_2025-03-13/) | `Random_App` | `Random.nextInt(100)` 결과를 TextView에 출력 |
| 02 | [labs/02_2025-03-18](labs/02_2025-03-18/) | `EditText_Test`, `EditText_IPP` | 입력란 하나짜리, 아이디·비밀번호·전화번호 세 개짜리 입력 처리 |
| 03 | [labs/03_2025-03-20](labs/03_2025-03-20/) | `Image_App` | ImageView의 ScaleType 다섯 가지 순환, 45도씩 회전, 투명도 0.5와 1.0 전환 |
| 04 | [labs/04_2025-03-25](labs/04_2025-03-25/) | `Calcul_Project`, `DIce_Project` | 계산기 키패드, 주사위 두 개 |
| 05 | [labs/05_2025_04_03](labs/05_2025_04_03/) | `Layout_Test` | GridLayout으로 TextView 배치 |
| 06 | [labs/06_2025_04_08](labs/06_2025_04_08/) | `Layout_Test` | LinearLayout 중첩과 FrameLayout 겹치기 |
| 07 | [labs/07_2025_04_10](labs/07_2025_04_10/) | `Calcu_lator`, `Login_Screen` | 중간고사 과제로 제출한 두 화면 |
| 08 | [labs/08_2025_04_15](labs/08_2025_04_15/) | `CheckBox_Test`, `Event_Test` | 체크박스로 이미지 표시와 제거, 버튼 리스너 |
| 09 | [labs/09_2025_04_17](labs/09_2025_04_17/) | `Intent1_Test`, `Radio_Test` | 액티비티 두 개 사이 화면 전환, 라디오 버튼으로 배경색 변경 |
| 10 | [labs/10_2025_04_29](labs/10_2025_04_29/) | `SplashProject` | `Handler.postDelayed`로 2초 뒤 메인 화면 전환 |
| 11 | [labs/11_2025-05-01](labs/11_2025-05-01/) | `Extras_Fild_Project` | 학기 내용을 하나로 합친 앱 |

### 11주차 앱 구성

마지막 실습이 제일 컸습니다. 화면 일곱 개가 이어집니다.

- `SplashActivity`에서 2초 뒤 `MainActivity`로 넘어가고, 거기서 입력한 아이디와 비밀번호를 `LoginActivity`로 넘깁니다
- 로그인 결과는 `registerForActivityResult`로 되받고, 성공일 때만 다음 화면 버튼을 `View.VISIBLE`로 바꿉니다
- `IntentActivity`에서 명시적 인텐트(날짜·시간, 연락처, 메모)와 암시적 인텐트(`ACTION_VIEW`로 웹, `ACTION_DIAL`로 전화, `geo:`로 지도)를 한 화면에서 분기합니다
- `ContactActivity`는 `SQLiteOpenHelper`로 연락처를 저장하고 이름으로 검색하거나 전체를 조회합니다
- `MemoActivity`는 내부 저장소 파일에 메모를 쓰고 읽습니다
- `TimeDateActivity`는 `DatePickerDialog`와 `TimePickerDialog`로 날짜와 시간을 고릅니다

## 하면서 걸렸던 부분

- 첫 주에는 XML에 만든 뷰를 `findViewById`로 가져오는 구조가 잡히지 않아서, `Random_App`의 `MainActivity`에 `R.id`가 무엇을 가리키는지 주석으로 정리해 뒀습니다.
- `DIce_Project`는 처음에 주사위 눈 여섯 개를 `switch` 문으로 하나씩 분기했습니다. `R.drawable`을 배열에 담아 인덱스로 꺼내니 같은 동작이 훨씬 짧아졌고, 비교하려고 이전 `switch` 버전을 주석으로 남겨 뒀습니다.
- `Event_Test`도 버튼 리스너를 익명 클래스로 먼저 붙였다가 람다로 바꿨습니다. 두 방식을 나란히 두고 보려고 익명 클래스 쪽을 주석으로 남겨 뒀습니다.
- `Calcul_Project`의 `=` 처리는 화면에 쌓인 문자열에서 연산자를 찾아 `split` 하는 방식입니다. 그래서 음수를 넣거나 연산을 두 번 연달아 쓰면 "수식 오류"로 떨어집니다. 수업 범위를 넘어가는 부분이라 그대로 뒀지만, 계산기는 입력 파싱을 따로 두어야 한다는 걸 여기서 알았습니다.
- 11주차 앱에서는 `startActivityForResult` 대신 `registerForActivityResult`를 썼습니다. 결과를 받는 자리가 콜백으로 빠지면서 액티비티 사이 흐름이 코드에서 눈에 더 잘 보였습니다.

## 실행 방법

`labs` 아래 프로젝트 폴더 하나를 Android Studio에서 열고 Run을 누르면 됩니다. minSdk가 34라 API 34 이상 에뮬레이터나 기기가 필요합니다.
