# 웹 프로그래밍 정리 노트: HTML 구조와 폼

교재 3장(문서 구조화와 웹 폼) 범위를 시험 대비로 정리한 개인 노트입니다.
개념만 적어 두니 막상 코드를 쓸 때 손이 안 움직여서, 항목마다 최소한의 코드를 같이 붙였습니다.

관련 코드는 [lectures/chap_03](../lectures/chap_03), [labs/W03_HTML-Structure-and-Forms](../labs/W03_HTML-Structure-and-Forms), [labs/W04_Web-Forms](../labs/W04_Web-Forms)에 있습니다.

## 1. 구조화 기본 태그와 전역 속성

`<div>`는 한 줄 전체를 차지하는 블록 상자, `<span>`은 글자 일부만 감싸는 인라인 상자입니다.

```html
<div>블록</div> <span>인라인</span>
```

전역 속성은 모든 태그에 공통으로 쓸 수 있습니다.

| 속성 | 설명 |
| :--- | :--- |
| `id` | 고유 식별자. 페이지 안에서 하나만 |
| `class` | 그룹 식별자. 여러 요소에 중복 사용 가능 |

```html
<p id="intro" class="main-text">...</p>
```

## 2. 시맨틱 태그: 페이지 전체 구조

| 태그 | 역할 |
| :--- | :--- |
| `<header>` | 머리말 |
| `<nav>` | 메뉴 |
| `<main>` | 핵심 내용. 문서당 한 번만 |
| `<section>` | 주제별 구획 |
| `<article>` | 그 자체로 독립되는 글 |
| `<aside>` | 보조 내용 |
| `<footer>` | 바닥글 |

```html
<body>
  <header>...</header>
  <main>
    <section>...</section>
  </main>
  <footer>...</footer>
</body>
```

## 3. iframe

다른 웹 페이지를 현재 문서 안에 끼워 넣는 인라인 프레임입니다.
주요 속성은 `src`(경로), `srcdoc`(HTML을 직접 삽입), `name`(프레임 이름)입니다.

```html
<iframe src="page.html" name="myFrame"></iframe>
```

## 4. 시맨틱 태그: 세부 콘텐츠 표현

인용은 세 가지로 나뉩니다. `<blockquote>`는 블록 인용문, `<q>`는 인라인 인용문, `<cite>`는 출처입니다.

```html
<blockquote cite="https://example.com"><p>인용 내용</p></blockquote>
```

`<figure>`로 이미지를 감싸고 `<figcaption>`으로 설명을 붙입니다.
`<details>`와 `<summary>`는 접었다 펼 수 있는 영역을 만듭니다.

```html
<details><summary>제목</summary>상세 내용</details>
```

`<time>`은 날짜와 시간, `<ruby>`는 글자 위 주석(`<rt>`)을 표시합니다.

```html
<time datetime="2025-10-04">10월 4일</time>
<ruby>韓<rt>한</rt></ruby>
```

## 5. 웹 폼

`<form>`에는 `action`(보낼 서버 주소)과 `method`(`get` 또는 `post`)를 지정합니다.

```html
<form action="server.php" method="post">...</form>
```

### 폼 요소 공통 속성

| 속성 | 설명 |
| :--- | :--- |
| `name` | 서버로 전송될 데이터의 이름 |
| `value` | 초기값 또는 전송될 값 |
| `placeholder` | 입력 예시 안내 문구 |
| `required` | 필수 입력 항목으로 지정 |
| `disabled` | 요소를 비활성화 |

```html
<input type="text" name="id" placeholder="ID 입력" required>
```

### input 타입

| type | 설명 | 주요 속성 |
| :--- | :--- | :--- |
| `text`, `password` | 한 줄 텍스트, 비밀번호 | `placeholder`, `maxlength` |
| `radio`, `checkbox` | 단일 선택, 복수 선택 | `name`, `value`, `checked` |
| `file` | 파일 첨부 | `accept` |
| `date`, `number`, `range` | 날짜, 숫자, 슬라이더 | `min`, `max`, `step` |
| `email`, `url`, `tel` | 이메일, URL, 전화번호. 형식을 자동 검증 | `required` |
| `submit`, `reset` | 폼 제출, 초기화 버튼 | `value`(버튼 텍스트) |

날짜 계열은 `month`, `week`, `date`, `datetime-local`, `time`으로 나뉘고 값 형식이 각각 다릅니다.
[W04 실습](../labs/W04_Web-Forms)에서 다섯 개를 한 페이지에 놓고 JS로 현재 시각을 채워 봤는데, `week` 타입만 `2025-W41` 형식이라 주차를 직접 계산해야 했습니다.

### button 과 input type="button"

| 구분 | `<button>` | `<input>` |
| :--- | :--- | :--- |
| 내용 | 이미지, 텍스트 등을 자유롭게 넣음 | `value` 속성의 텍스트만 표시 |
| 코드 | `<button><img src="icon.png"></button>` | `<input type="button" value="클릭">` |

### select 와 datalist

| 구분 | `<select>` | `<datalist>` |
| :--- | :--- | :--- |
| 특징 | 정해진 목록에서만 선택 | 직접 입력하면서 추천 목록도 제공 |
| 코드 | `<select><option>A</option></select>` | `<input list="d"><datalist id="d">...</datalist>` |

### textarea

여러 줄 텍스트를 입력받습니다.

```html
<textarea rows="3" cols="20" placeholder="내용 입력"></textarea>
```

## 6. 진행률과 수치 표현

| 태그 | 설명 | 코드 |
| :--- | :--- | :--- |
| `<progress>` | 작업 진행률 | `<progress value="70" max="100"></progress>` |
| `<meter>` | 정해진 범위 안의 수치나 비율 | `<meter value="0.8">80%</meter>` |
