# 📱 앱 프로그래밍 (Android)

> ### 3줄 요약
>
>   - **핵심 이론**: 안드로이드 앱의 기본 구성요소와 생명주기를 이해하고, UI, 이벤트 처리, 데이터 관리 방법을 실습합니다.
>   - **기술 스택**: Java와 XML을 기반으로 지도(Google Maps), 로컬 데이터베이스(SQLite), 백그라운드 작업 등 실무 기술로 확장합니다.
>   - **최종 목표**: 팀 프로젝트를 통해 실제 사용자 시나리오를 반영한 안드로이드 앱을 직접 설계하고 구현합니다.

-----

## 🎯 과목 목표

  - **안드로이드 4대 구성요소**(`Activity`, `Service`, `BroadcastReceiver`, `ContentProvider`)의 역할과 생명주기를 이해합니다.
  - `XML` 레이아웃 설계, `이벤트 리스너`, `Intent`를 활용한 화면 간 데이터 전달 패턴을 숙달합니다.
  - 안드로이드의 스레드 모델(**메인/백그라운드**)을 이해하고, `SQLite`와 `SharedPreferences`를 이용한 데이터 영속성 처리 방법을 학습합니다.

-----

## 🧭 학습 범위

| 카테고리 | 주요 학습 내용 |
| :--- | :--- |
| **개발 환경** | `Android Studio`, `Gradle`, `AndroidManifest.xml`, 리소스 구조 |
| **UI** | XML 레이아웃 (`LinearLayout`, `ConstraintLayout`), `RecyclerView` + `Adapter` |
| **생명주기** | `Activity` 및 `Service`의 생명주기(Lifecycle)와 상태 보존 |
| **데이터 전달** | `Intent`, `Bundle`, `Parcelable` |
| **백그라운드 작업**| `Thread` + `Handler`, `Executor`, `WorkManager` (선택) |
| **저장소** | `SQLite` (Room 라이브러리 권장), `SharedPreferences` |
| **권한 및 보안** | 런타임 권한 요청, 네트워크 보안 설정 (`cleartextTrafficPermitted`) |

-----

## 🧪 실습 가이드 (핵심 요약)

### 1\. 인텐트: 명시적 vs 암시적

  - **명시적 인텐트**: 앱 내부의 특정 컴포넌트(액티비티)를 직접 지정하여 실행합니다.
    ```java
    Intent intent = new Intent(this, DetailActivity.class);
    intent.putExtra("itemId", 42);
    startActivity(intent);
    ```
  - **암시적 인텐트**: 특정 액션(웹 페이지 열기, 전화 걸기 등)을 요청하면, 해당 액션을 처리할 수 있는 앱을 안드로이드 시스템이 찾아 실행합니다.
    ```java
    Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://example.com"));
    startActivity(webIntent);
    ```

### 2\. 메인 스레드에서 긴 작업 금지 (ANR 회피)

  - **이유**: 네트워크 통신, 대용량 파일 처리 등 긴 작업을 메인 스레드(UI 스레드)에서 수행하면 앱이 멈추는 **ANR(Application Not Responding)** 현상이 발생합니다.
  - **해결**: `Executor`, `Thread`와 `Handler` 조합, `WorkManager` 등 비동기 처리 방식을 사용해야 합니다.

### 3\. 로컬 저장소 예시 (`SQLite`)

```sql
CREATE TABLE visit_history (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    visit_date TEXT NOT NULL,
    diagnosis TEXT,
    cost INTEGER
);
```

-----

## 🗺️ 예시 프로젝트

  - **[Mobile Doctor | Android](https://github.com/jihun-moon/mobile-doctor-app))**
      - 본 과목에서 배운 내용을 총망라한 팀 프로젝트입니다.
      - 주요 기능: 지도 기반 병원/약국 정보, 증상 검색, SQLite 기록 관리, 접근성 기능 등
      - 프로젝트 `README`를 통해 실무적인 앱 구조와 개발 과정을 학습할 수 있습니다.

#### 데모

\<img src="assets/demo.gif" alt="App Demo GIF"/\>

-----

## 📚 참고 자료

  - 강의 교안
  - **공식 문서**: [Android Developers](https://developer.android.com/)
  - **테스트 전략**: 단위 테스트(`JUnit`), UI 테스트(`Espresso`)
