네, 알겠습니다. 인용 표시(`[cite:...]`)를 모두 제거하고, A4 용지에 바로 옮겨 적을 수 있도록 깔끔하게 정리된 최종 완성본을 다시 보여드리겠습니다.

-----

# 📝 웹 프로그래밍 1-3장 최종 요약 (세로 3단 구성)

-----

### **[1단] HTML의 뼈대 (개념 & 기본)**

#### **1️⃣ 챕터 1: 웹 프로그래밍 핵심 개념**

  * **웹 페이지 역할 분담 (O/X 대비)**
    | 요소 | 역할 | 설명 |
    | :--- | :--- | :--- |
    | `HTML` | 뼈대 | 내용, 구조 담당 |
    | `CSS` | 디자인 | 색상, 모양 등 꾸미기 |
    | `JS` | 동작 | 클릭 효과, 상호작용 |

    > 📢 **함정**: "HTML로 글자색을 바꾼다." → **(X) `CSS` 역할\!**

  * **웹 동작 원리 (O/X 대비)**

      * **한 문장 요약**: `클라이언트`(브라우저)가 `URL`로 **요청**하면, `서버`는 `HTTP` 규칙에 따라 **응답**한다.

    > 📢 **함정**: "서버는 클라이언트 컴퓨터에 있다." → **(X)**, "HTTP는 언어다." → **(X)** HTTP는 **통신 규칙**.

  * **주소 체계 (포함 관계)**

      * `URL` (전체 주소) \> `도메인` (문자 주소) \> `IP` (숫자 주소)

    > 📢 **함정**: "URL과 도메인은 같다." → **(X)** URL이 더 큰 개념.

  * **이미지 포맷 (O/X, 빈칸 대비)**
    | 포맷 | 압축 | 특징 | 용도 |
    | :--- | :--- | :--- | :--- |
    | `JPEG` | **손실** | 1600만 색상, 용량 작음 | 사진 |
    | `PNG` | **비손실** | 화질 좋음, **투명 배경** 지원 | 로고, 아이콘 |
    | `GIF` | 비손실 | **256색 제한**, **움짤**, 투명 지원 | 단순 아이콘 |

    > 📢 **함정**: "GIF는 고화질 사진에 적합하다." → **(X)** 256색 제한 때문.

  * **주요 용어 정의 (서술형 대비)**

      * `W3C`: 웹 **표준**을 만드는 국제 단체.
      * `CGI`: 웹 서버와 외부 프로그램(DB 등)을 연결하는 **규칙(인터페이스)**.

-----

#### **2️⃣ 챕터 2: HTML5로 기본 문서 만들기**

  * **HTML 기본 문서 구조 (필수 암기)**

    ```html
    <!DOCTYPE html> <!-- HTML5 문서 선언 (단일 태그) -->
    <html>
      <head>
        <!-- 페이지 설정 정보 (화면에 안 보임) -->
        <meta charset="UTF-8"> <!-- 한글 깨짐 방지 (단일 태그) -->
        <title>브라우저 탭 제목</title>
      </head>
      <body>
        <!-- 화면에 보이는 모든 내용 -->
      </body>
    </html>
    ```

  * **주요 태그와 사용법**

      * **텍스트 관련**
          * `<h1>` \~ `<h6>`: 제목. `<h1>가장 큰 제목</h1>`
          * `<p>`: 문단. `<p>하나의 문단입니다.</p>`
          * `<br>`: 강제 줄 바꿈. **(단일 태그)**
          * `<strong>` / `<b>`: **중요/굵게**
          * `<em>` / `<i>`: *강조/기울임*
          * `<mark>`: \<mark\>형광펜 효과\</mark\>
          * 엔티티: `&lt;` (\<), `&gt;` (\>), `&nbsp;` (공백)
      * **이미지 및 링크 (필수 속성 암기)**
          * `<img>`: 이미지. **(단일 태그)**
            `<img src="./images/dog.jpg" alt="강아지 사진">`
          * `<a>`: 하이퍼링크(Anchor).
            `<a href="https://google.com" target="_blank">구글</a>`
      * **목록 (계층 구조)**
        ```html
        <!-- 순서 없는 목록 (•) -->
        <ul>
          <li>항목 1</li>
          <li>항목 2</li>
        </ul>
        <!-- 순서 있는 목록 (1.) -->
        <ol type="A" start="3">
          <li>C. 항목</li>
          <li>D. 항목</li>
        </ol>
        <!-- 정의 목록 -->
        <dl>
            <dt>용어</dt>
            <dd>설명</dd>
        </dl>
        ```
      * **멀티미디어**
        ```html
        <!-- controls 속성 필수 -->
        <video src="v.mp4" controls width="300"></video>
        <audio src="a.mp3" controls loop></audio>
        <!-- 유튜브 삽입 -->
        <iframe src="https://youtube.com/embed/영상ID"></iframe>
        ```

-----

### **[2단] HTML의 구조 (구조화 & 입력)**

#### **3️⃣ 챕터 3: 문서 구조화와 웹폼**

  * **구조 규칙: 블록 vs 인라인 (⭐가장 중요)**
    | 구분 | 블록(Block) 🧱 | 인라인(Inline) ✨ |
    | :--- | :--- | :--- |
    | **특징** | 한 줄 전체 차지 | 자기 크기만 차지 |
    | **역할** | 레이아웃, 다른 요소 담는 그릇 | 텍스트 일부, 콘텐츠 자체 |
    | **포함** | **블록, 인라인 모두 포함 가능** | **인라인만 포함 가능** |
    | **태그**| `<div>`, `<p>`, `<h1>`, `<ul>`... | `<span>`, `<a>`, `<img>`, `<strong>`... |

  * **영역과 식별**

      * `<div>`: 블록 단위 범용 컨테이너 (레이아웃용)
      * `<span>`: 인라인 단위 그룹 (글자 일부 스타일)
      * `id="이름"`: 고유 ID (페이지에 **단 한 번** 사용), CSS에서 `#`으로 선택
      * `class="이름"`: 그룹 이름 (**여러 번** 사용 가능), CSS에서 `.`으로 선택

  * **시맨틱 태그 (의미있는 구조)**

      * `<header>`: 머리말 (로고, 제목)
      * `<nav>`: 내비게이션 메뉴
      * `<main>`: 핵심 주요 내용 (**한 번만 사용**)
      * `<section>`: 주제별 구획
      * `<article>`: 독립된 글 (기사, 포스트)
      * `<aside>`: 보조 내용 (사이드바, 광고)
      * `<footer>`: 바닥글

  * **테이블(Table) 심화**

    > 📢 **`colspan`(열 병합), `rowspan`(행 병합) 암기 필수\!**

    ```html
    <table border="1">
      <caption>표 제목</caption>
      <thead>
        <tr>
          <th>제목1</th> <th>제목2</th> <th>제목3</th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td rowspan="2">A (2줄 합치기)</td>
          <td>B</td>
          <td>C</td>
        </tr>
        <tr>
          <td colspan="2">D (2칸 합치기)</td>
        </tr>
      </tbody>
    </table>
    ```

  * **웹폼(Form) 태그**

      * **`GET` vs `POST`**: `GET`은 URL에 데이터 노출(가벼움), `POST`는 숨겨서 전송(보안, 대용량)

    <!-- end list -->

    ```html
    <form action="처리할페이지.jsp" method="post">
      <label for="user-id">아이디:</label>
      <input type="text" id="user-id" name="id">

      <p>성별:</p>
      <input type="radio" id="male" name="gender" value="M">
      <label for="male">남</label>
      <input type="radio" id="female" name="gender" value="F">
      <label for="female">여</label>

      <p>취미:</p>
      <input type="checkbox" id="game" name="hobby" value="game">
      <label for="game">게임</label>
      <input type="checkbox" id="read" name="hobby" value="read">
      <label for="read">독서</label>

      <label for="job">직업:</label>
      <select id="job" name="job">
        <option value="student">학생</option>
        <option value="teacher">교사</option>
      </select>

      <textarea name="intro" rows="5"></textarea>
      <button type="submit">제출</button>
    </form>
    ```

-----

### **[3단] 웹 페이지 꾸미기 (CSS) - 미학습**

#### **4️⃣ 챕터 4: CSS3로 웹 페이지 꾸미기**

> 📖 이 부분은 4장 학습 후 채워주세요.
>
>   * **CSS 기본 문법**
>       * `선택자 { 속성: 값; }`
>   * **CSS 적용 방식 3가지**
>       * 인라인 스타일: `<p style="color: blue;">`
>       * 내부 스타일 시트: `<head>` 안에 `<style>` 태그 사용
>       * 외부 스타일 시트: `<link rel="stylesheet" href="style.css">`
>   * **핵심 선택자**
>       * `*`, `태그`, `.클래스`, `#아이디`
>   * **CSS 박스 모델 (⭐가장 중요)**
>       * (여기에 박스 모델 그림 그릴 공간 확보)
>       * `margin`: 테두리 바깥 여백
>       * `border`: 테두리
>       * `padding`: 테두리 안쪽 여백
>       * `content`: 실제 내용
>   * **주요 속성 (예정)**
>       * 글자: `color`, `font-size`, `font-weight`, `text-align`
>       * 배경: `background-color`, `background-image`
>       * 크기/위치: `width`, `height`, `display`, `position`

-----
