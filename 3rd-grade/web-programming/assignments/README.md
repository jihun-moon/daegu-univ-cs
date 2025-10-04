앗, 정말 죄송합니다. 제가 요청하신 추가 내용을 반영하면서 기존의 중요한 내용 일부를 실수로 누락했네요. 바로 지적해주셔서 감사합니다.

`div` vs `span`, `id` vs `class`, 그리고 `<label>`에 대한 설명을 모두 복원하고, 추가했던 내용과 합쳐서 **가장 완전한 버전의 2단 요약본**을 다시 만들었습니다.

-----

### **[2단] HTML의 구조 (구조화 & 입력)**

#### **3️⃣ 챕터 3: 문서 구조화와 웹폼**

  * **영역 나누기: `div` vs `span`**
    | 구분 | `<div>` (Block) | `<span>` (Inline) |
    | :--- | :--- | :--- |
    | **특징** | 한 줄 전체 차지, 블록/인라인 포함 가능 | 콘텐츠 크기만 차지, 인라인만 포함 가능 |
    | **용도** | 레이아웃 분할, 여러 요소 묶기 | 텍스트 일부에 스타일 적용 |
  * **영역 식별: `id` vs `class` (⭐중요)**
    | 속성 | `id` (신분증) | `class` (반 스티커) |
    | :--- | :--- | :--- |
    | **중복** | **불가능 (페이지에 단 한 번)** | **가능 (여러 번 사용)** |
    | **용도** | 특정 요소에 고유 이름 부여 | 여러 요소를 그룹으로 묶기 |
    | **CSS** | `#id이름` | `.class이름` |
  * **시맨틱 태그 I - 페이지 전체 구조**
      * `<div>` 대신 의미에 맞는 태그를 사용해 검색 엔진 최적화(SEO) 및 코드 가독성을 높인다.
      * `<header>`: 머리말 | `<nav>`: 메뉴 | `<main>`: 핵심 내용(1회만) | `<section>`: 주제별 구획 | `<article>`: 독립된 글 | `<aside>`: 보조 내용 | `<footer>`: 바닥글
      * **시맨틱 계층 구조 예시**
        ```html
        <body>
          <header> <nav>...</nav>
          </header>
          <main> <section>
              <article>...</article>
              <article>...</article>
            </section>
            <aside>...</aside> </main>
          <footer>...</footer> </body>
        ```
  * **시맨틱 태그 II - 세부 콘텐츠 표현**
      * `<blockquote>`: 긴 인용문 (블록). `<q>`는 짧은 인용문(인라인).
      * `<figure>`: 이미지, 코드 등을 감싸는 단위. `<figcaption>`으로 설명을 붙임.
        ```html
        <figure>
          <img src="photo.jpg" alt="사진">
          <figcaption>사진 설명</figcaption>
        </figure>
        ```
      * `<details>`: 접었다 폈다 할 수 있는 상세 정보. `<summary>`로 제목을 표시.
      * `<iframe>`: **다른 웹 페이지를 현재 문서 안에 삽입하는 창(인라인 프레임)을 만듭니다.** (서술형 대비)
  * **정보 표현 태그**
      * `<time>`: 날짜(`2025-10-04`)나 시간(`19:00`) 정보에 의미 부여.
      * `<progress>`: 작업의 **진행률** 표시. (ex: 다운로드 진행 바)
      * `<meter>`: 정해진 범위 내의 **수치**(비율) 표시. (ex: 디스크 사용량 80%)
      * `<ruby>`: 동아시아 문자 위에 주석(읽는 법)을 표시. `<rt>`로 주석 내용을, `<rp>`로 미지원 브라우저용 괄호를 감쌈.
  * **웹폼 `<form>` 확장 (실기 대비)**
      * **`GET` vs `POST`**: `GET`은 URL에 데이터 노출(가벼움), `POST`는 숨겨서 전송(보안).
      * **`<label>` 사용법 2가지 (실기 대비)**
        1.  **`for` 속성으로 연결 (명시적)**
            ```html
            <label for="user-id">아이디:</label>
            <input type="text" id="user-id">
            ```
        2.  **입력 요소를 감싸기 (암시적)**
            ```html
            <label>아이디: <input type="text"></label>
            ```
      * **`<input>` 타입 완전 정리**
        | type | 설명 | 주요 속성 |
        | :--- | :--- | :--- |
        | `text` | 일반 텍스트 | `placeholder`, `maxlength` |
        | `password` | 비밀번호 | `placeholder`, `maxlength` |
        | `radio` | 라디오 버튼 (하나만) | `name`, `value`, `checked` |
        | `checkbox`| 체크박스 (여러 개) | `name`, `value`, `checked` |
        | `file` | 파일 첨부 | **`accept`** (파일 종류 지정) |
        | `date` | 날짜 선택 | `min`, `max` |
        | `number` | 숫자 (증감 버튼) | `min`, `max`, `step` |
        | `range` | 숫자 범위 슬라이더 | `min`, `max`, `step` |
        | `email` | 이메일 형식 검증 | `required` |
        | `submit`| **폼 제출 버튼** | `value` (버튼 텍스트) |
        | `reset` | **폼 초기화 버튼** | `value` |
        | `button` | 일반 버튼 (JS와 연동) | |
        | `hidden` | 숨겨진 데이터 | `name`, `value` |
        > 📢 **파일 업로드 `accept` 속성 예시**: `<input type="file" accept="image/*">` (모든 이미지), `<input type="file" accept=".pdf, .docx">` (특정 확장자)
      * **선택 목록: `<select>` vs `<datalist>` (O/X 대비)**
        | 구분 | `<select>` (선택 상자) | `<datalist>` (콤보 박스) |
        | :--- | :--- | :--- |
        | **특징** | **정해진 목록에서만** 선택 | **직접 입력 + 추천 목록** 제공 |
        | **용도** | 카테고리, 년/월/일 등 | 검색창 자동 완성 |
        ```html
        <input list="browsers">
        <datalist id="browsers">
          <option value="Edge">
          <option value="Chrome">
        </datalist>
        ```
