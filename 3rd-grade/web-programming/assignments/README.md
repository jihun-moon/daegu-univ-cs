### **[1단] HTML의 뼈대 (개념 & 기본)**

#### **1️⃣ 챕터 1: HTML5와 웹 프로그래밍**

  * **웹 페이지 3요소 역할 분담 (O/X 대비)**
    | 요소 | 역할 | 상세 설명 |
    | :--- | :--- | :--- |
    | `HTML` | 구조 | 페이지의 내용과 뼈대를 구성한다. |
    | `CSS` | 디자인 | 색상, 글꼴 등 시각적 스타일을 담당한다. |
    | `JavaScript` | 동작 | 사용자와의 상호작용, 동적 기능을 구현한다. |

    > 📢 **함정**: HTML은 구조 담당. "HTML로 디자인한다" → **(X)**

  * **웹 동작 원리 (클라이언트-서버)**

      * **흐름**: `클라이언트`(브라우저)가 `서버`에게 **HTTP**로 페이지를 \*\*요청(Request)\*\*하면, 서버가 해당 `HTML` 문서를 \*\*응답(Response)\*\*해준다.

    > 📢 **함정**: 클라이언트는 **요청**하는 컴퓨터, 서버는 **응답**하는 컴퓨터. `HTTP`는 통신 \*\*규약(Protocol)\*\*이지 언어가 아니다.

  * **이미지 포맷 비교 (빈칸/O/X 대비)**
    | 포맷 | 핵심 특징 | 주 용도 |
    | :--- | :--- | :--- |
    | `JPEG` | **손실 압축**, 고용량 사진을 저용량으로 표현. | 사진 이미지 |
    | `PNG` | **비손실 압축**, **투명 배경** 지원, 화질 우수. | 로고, 아이콘 |
    | `GIF` | **256색 제한**, **움직이는 이미지(애니메이션)** 지원. | 간단한 아이콘, 움짤 |

  * **핵심 용어 정의 (서술형 대비)**

      * `W3C`: 웹 표준을 제정하는 국제 컨소시엄.
      * `URL`: 웹 자원의 전체 주소. `프로토콜://도메인/경로` 형식.
      * `Domain`: 숫자로 된 IP 주소를 사람이 외우기 쉽게 만든 문자 주소.

-----

#### **2️⃣ 챕터 2: HTML5 기본 문서**

  * **HTML5 기본 구조 (암기 필수)**

    ```html
    <!DOCTYPE html> <html>
      <head>
        <meta charset="utf-8"> <title>문서 제목</title> </head>
      <body>
        </body>
    </html>
    ```

  * **핵심 태그 & 기본 예시 (코드 빈칸 대비)**

      * **텍스트**

          * `<h1>`\~`<h6>`: 제목. `<h1>가장 큰 제목</h1>`
          * `<p>`: 문단. `<p>문단을 나눕니다.</p>`
          * `<br>`: 강제 줄바꿈. **(단일 태그)**
          * `<strong>`: **중요한 텍스트 (굵게)**
          * 위<sup>첨자</sup>와 아래<sub>첨자</sub>
          * `<em>`: *강조하는 텍스트 (기울임)*
          * `<hr>`: 주제 변경을 위한 수평선. **(단일 태그)**
          * `&nbsp;`: 특수문자(Entity) 공백.

      * **이미지와 링크 (필수 속성)**

          * `<img>`: 이미지 삽입. **(단일 태그)**
            ```html
            <img src="경로/이미지.jpg" alt="이미지 설명">
            ```
          * `<a>`: 하이퍼링크.
            ```html
            <a href="https://google.com" target="_blank">구글</a>
            ```
            > 📢 `target="_blank"`는 새 탭에서 링크를 연다.

      * **목록 (계층 구조)**

          * `<ul>` (Unordered List): 순서 없는 목록
            ```html
            <ul>
              <li>사과</li>
              <li>바나나</li>
            </ul>
            ```
          * `<ol>` (Ordered List): 순서 있는 목록
            ```html
            <ol type="a"> <li>첫째</li>
              <li>둘째</li>
            </ol>
            ```
          * `<dl>` (Definition List): 용어-설명 목록
            ```html
            <dl>
              <dt>HTML</dt>
              <dd>웹 페이지의 구조를 정의한다.</dd>
            </dl>
            ```

-----

### **[2단] HTML의 구조 (구조화 & 입력)**

#### **3️⃣ 챕터 3: 문서 구조화와 웹폼**

  * **영역 나누기: `div` vs `span`**
    | 구분 | `<div>` (Block) | `<span>` (Inline) |
    | :--- | :--- | :--- |
    | **특징** | 한 줄 전체 차지, **블록/인라인 포함 가능** | 콘텐츠 크기만 차지, **인라인만 포함 가능** |
    | **용도** | 레이아웃 분할, 여러 요소 묶기 | 텍스트 일부에 스타일 적용 |

  * **시맨틱 태그 (Semantic Tags) (특징 나열 대비)**

    > 📢 `div` 대신 **의미에 맞는 태그**를 사용해 검색 엔진 최적화(SEO) 및 코드 가독성을 높인다.

      * `<header>`: 페이지나 섹션의 머리글 (로고, 제목, 메뉴)
      * `<nav>`: 주 메뉴, 내비게이션 링크 영역.
      * `<main>`: 문서의 핵심 콘텐츠. **(페이지당 1회만 사용)**
      * `<section>`: 주제별로 연관 있는 콘텐츠 그룹.
      * `<article>`: 독립적으로 배포 가능한 글 (뉴스 기사, 블로그 포스트).
      * `<aside>`: 본문과 간접적으로 연관된 보조 콘텐츠 (사이드바, 광고).
      * `<footer>`: 페이지나 섹션의 바닥글 (저작권, 연락처 정보).

  * **테이블 `<table>` 구조 (코드 해석 대비)**

    > 📢 \*\*`colspan`(열 병합), `rowspan`(행 병합)\*\*은 시험 단골\!

    ```html
    <table border="1">
      <caption>메뉴판</caption> <thead> <tr>
          <th>메뉴</th> <th>가격</th>
        </tr>
      </thead>
      <tbody> <tr>
          <td>아메리카노</td>
          <td rowspan="2">4000원</td> </tr>
        <tr>
          <td>카페라떼</td>
        </tr>
        <tr>
          <td colspan="2">총액: 8000원</td> </tr>
      </tbody>
    </table>
    ```

  * **웹폼 `<form>` (사용자 입력)**

      * **데이터 전송 방식: `GET` vs `POST` (서술형/O/X 대비)**
        | 방식 | `GET` | `POST` |
        | :--- | :--- | :--- |
        | **전송** | URL에 데이터를 붙여서 전송 (노출됨). | HTTP Body에 데이터를 담아서 전송 (숨겨짐). |
        | **용도** | 검색, 간단한 데이터 조회. | 로그인, 회원가입 등 민감 정보. |
        | **보안** | 낮음 | 높음 |

      * **주요 입력 요소 `<input>` (코드 빈칸 대비)**

        > 📢 **`name` 속성**이 같아야 그룹으로 묶인다 (특히 라디오 버튼).

        ```html
        <form action="/login" method="post">
          <label for="id">ID: </label>
          <input type="text" id="id" name="userId">

          <input type="password" name="userPw">

          <input type="radio" name="gender" value="male"> 남자
          <input type="radio" name="gender" value="female"> 여자

          <input type="checkbox" name="hobby" value="game"> 게임
          <input type="checkbox" name="hobby" value="music"> 음악

          <select name="country">
            <option value="ko">한국</option>
            <option value="us">미국</option>
          </select>

          <textarea name="intro" rows="5"></textarea>

          <button type="submit">제출</button>
          <input type="reset" value="초기화">
        </form>
        ```

-----

### **[3단] 웹 페이지 꾸미기 (CSS) - 미학습**

#### **4️⃣ 챕터 4: CSS3로 웹 페이지 꾸미기**
