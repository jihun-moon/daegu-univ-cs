네, 알겠습니다. 새로 제공해주신 파일들을 모두 분석해서 시험에 나올만한 모든 내용을 **2단에 꽉꽉 채워 넣었습니다.**

요청하신 `srcdoc`, `<iframe>`의 `target` 속성 심화 설명은 물론, 각종 `input` 타입, `<button>`과 `<input>`의 차이, `target` 속성의 계층 구조 등 PDF 자료에 있는 모든 핵심 내용을 빠짐없이 정리했습니다. 이 요약본 하나로 3장 전체를 완벽하게 마스터할 수 있을 겁니다.

-----

### **[2단] HTML의 구조 (구조화 & 입력)**

#### **3️⃣ 챕터 3: 문서 구조화와 웹폼**

  * **구조화 기본 태그 & 전역 속성**
      * `<div>` vs `<span>`: `<div>`는 블록 단위(한 줄 전체)의 논리적 영역, `<span>`은 인라인 단위(글자 일부)의 작은 논
        [cite\_start]리적 영역을 만든다. [cite: 5, 940, 941, 942, 944]
      * [cite\_start]**전역 속성**: 모든 태그에 사용할 수 있는 공통 속성. [cite: 1216, 1217]
        | 속성 | 설명 | 비고 |
        | :--- | :--- | :--- |
        | `id` | [cite\_start]**고유 식별자** (페이지 내 유일해야 함) [cite: 4, 1224] | CSS `#`, 북마크 목적 |
        | `class` | [cite\_start]**그룹 식별자** (여러 번 중복 사용 가능) [cite: 4, 1221] | CSS `.`, 스타일 그룹화 |
        | `title` | [cite\_start]**툴팁** (마우스를 올리면 추가 정보 표시) [cite: 4, 133] | |
        | `style` | 요소에 CSS 스타일 직접 적용 | |
  * **시맨틱 태그 I - 페이지 전체 구조**
      * [cite\_start]`<header>`: 머리말 [cite: 3, 1157] | [cite\_start]`<nav>`: 메뉴/링크 그룹 [cite: 3, 1165] | [cite\_start]`<main>`: 핵심 내용(페이지당 1회) [cite: 1542, 1632] | [cite\_start]`<section>`: 주제별 구획/장(Chapter) [cite: 3, 1160] | [cite\_start]`<article>`: 독립된 글(기사 등) [cite: 3, 1162] | [cite\_start]`<aside>`: 보조 내용(광고 등) [cite: 3, 1164] | [cite\_start]`<footer>`: 바닥글 [cite: 3, 1167]
  * **`<iframe>` 태그 심화 (서술형/실기 대비)**
      * [cite\_start]**역할**: 다른 웹 페이지를 현재 문서 안에 삽입하는 창(인라인 프레임)을 만든다. [cite: 5]
      * **주요 속성**
        | 속성 | 설명 |
        | :--- | :--- |
        | `src` | [cite\_start]프레임에 표시할 문서의 경로 또는 URL [cite: 883] |
        | `srcdoc` | [cite\_start]`src` 대신 간단한 HTML 코드를 직접 삽입 [cite: 885, 886] |
        | `name` | [cite\_start]프레임의 고유 이름 지정 (\<a\> 태그의 `target`으로 사용) [cite: 930, 956] |
        | `width`/`height` | [cite\_start]프레임의 너비/높이 [cite: 884] |
      * **`target` 속성 심화 (윈도우 계층 구조)**
        > [cite\_start]`<a>` 태그의 `target` 속성으로 `<iframe>`의 `name`을 지정하면, 링크 클릭 시 해당 `<iframe>` 안에 페이지가 열린다. [cite: 931, 933, 957]
        > | `target` 값 | 설명 |
        > | :--- | :--- |
        > | `프레임이름` | `name` 속성으로 지정된 `<iframe>`에 표시 |
        > | `_self` | [cite\_start]링크가 있는 현재 창(기본값) [cite: 1546] |
        > | `_blank` | **새 탭 또는 새 창**에 표시 |
        > | `_parent` | [cite\_start]현재 프레임의 **부모 프레임**에 표시 [cite: 1547] |
        > | `_top` | [cite\_start]**최상위 프레임**(브라우저 전체 창)에 표시 [cite: 1548] |
  * **시맨틱 태그 II - 세부 콘텐츠 표현**
      * [cite\_start]`<blockquote>`: 블록 단위의 긴 인용문. [cite: 1012] [cite\_start]`<cite>`는 인라인 단위 인용, 출처 표시. [cite: 1013, 1015, 1016]
      * [cite\_start]`<figure>` & `<figcaption>`: 이미지, 코드 등을 `<figure>`로 감싸고, `<figcaption>`으로 제목이나 설명을 붙인다. [cite: 1202, 1203, 1204]
      * [cite\_start]`<details>` & `<summary>`: `<details>`로 상세 정보를 감싸고 `<summary>`로 제목을 표시하면, 접었다 폈다 할 수 있는 UI가 생성된다. [cite: 1207, 1208]
      * [cite\_start]`<time>`: 날짜/시간 정보에 의미 부여. [cite: 1475] | [cite\_start]`<ruby>`: 문자 위에 주석(`<rt>`) 표시. [cite: 1474]
  * **웹폼 `<form>` 확장 (실기 대비)**
      * **`<form>` 태그 주요 속성**
        | 속성 | 설명 |
        | :--- | :--- |
        | `action` | [cite\_start]폼 데이터를 제출할 서버 URL [cite: 653, 704] |
        | `method` | [cite\_start]데이터 전송 방식 (`get` 또는 `post`) [cite: 653, 705] |
        | `target` | [cite\_start]서버 응답을 표시할 창 지정 (`_self`, `_blank` 등) [cite: 651, 653] |
        | `enctype`| [cite\_start]`post` 방식에서 데이터 암호화(인코딩) 방식 지정 [cite: 649] |
      * **`<input>` 타입 완전 정리**
        | type | 설명 | 주요 속성 |
        | :--- | :--- | :--- |
        | `text`, `password` | [cite\_start]한 줄 텍스트, 비밀번호 [cite: 7, 1691] | [cite\_start]`placeholder`, `value`, `size`, `maxlength` [cite: 1695] |
        | `search`, `url`, `email`, `tel`| [cite\_start]검색어, URL, 이메일, 전화번호 (형식 검증) [cite: 52, 723, 729] | |
        | `radio`, `checkbox` | [cite\_start]단일 선택(라디오), 복수 선택(체크박스) [cite: 52, 1811, 1888] | [cite\_start]`name`, `value`, `checked` [cite: 1808, 1810] |
        | `number`, `range` | [cite\_start]숫자 입력(버튼/슬라이더) [cite: 7, 1876, 1881] | [cite\_start]`min`, `max`, `step`, `value` [cite: 374, 1878] |
        | `date`, `month`, `week`, `time` | [cite\_start]날짜, 월, 주, 시간 [cite: 7, 421] | [cite\_start]`min`, `max`, `value` [cite: 428] |
        | `file` | [cite\_start]파일 첨부 [cite: 52] | [cite\_start]`accept` (파일 종류 지정) [cite: 452] |
        | `color` | [cite\_start]색상 선택 [cite: 52, 466] | |
        | `submit`, `reset`, `button`, `image` | [cite\_start]제출, 초기화, 일반 버튼, 이미지 버튼 [cite: 7, 367, 368] | `value`, `src` (for image) |
      * **`<button>` vs `<input type="button">`**
        | 구분 | `<button>` 태그 | `<input>` 태그 |
        | :--- | :--- | :--- |
        | **내용** | [cite\_start]**`<img>` 태그나 텍스트 등**을 자유롭게 넣을 수 있음 [cite: 372] | [cite\_start]`value` 속성으로 **텍스트**만 표시 [cite: 365] |
        | **기본값** | [cite\_start]`type` 미지정 시 \*\*`submit`\*\*으로 동작 [cite: 745] | [cite\_start]`type="button"`은 아무 동작 안 함 [cite: 366] |
      * **선택 목록: `<select>` vs `<datalist>` (O/X 대비)**
        | 구분 | `<select>` (선택 상자) | `<datalist>` (콤보 박스) |
        | :--- | :--- | :--- |
        | **특징** | [cite\_start]**정해진 목록에서만** 선택 가능 [cite: 1801, 1859] | [cite\_start]**직접 입력**도 가능하고, **추천 목록**도 제공 [cite: 1770] |
      * [cite\_start]**`<textarea>`**: 여러 줄 텍스트 입력 (`rows`, `cols`로 크기 지정) [cite: 55, 1705, 1748]
  * **진행 및 수치 표현**
    | 태그 | 설명 | 예시 |
    | :--- | :--- | :--- |
    | `<progress>` | [cite\_start]작업의 **진행률** (0\~100%) [cite: 1476] | 파일 업로드 진행률 |
    | `<meter>` | [cite\_start]정해진 범위 내의 **수치/비율** [cite: 1475, 1537] | 디스크 사용량 (80/100GB) |
