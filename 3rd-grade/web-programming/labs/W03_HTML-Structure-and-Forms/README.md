# W03: 시맨틱 태그로 온라인 명함 만들기

HTML5 시맨틱 태그를 써서 자기소개 페이지를 만드는 과제입니다.
어차피 만드는 김에 이력서 형태로 잡고 About, Projects, Skills, Career, Location 다섯 구획으로 나눴습니다.

폴더 이름은 교재 3장 제목(문서 구조화와 웹 폼)을 그대로 가져왔지만, 이번 페이지에는 폼이 들어가지 않았습니다.
폼 입력 타입은 [W04](../W04_Web-Forms)에서 따로 다뤘습니다.

## 구현한 것

- `header`, `nav`, `main`, `section`, `article`, `footer`로 문서 뼈대를 잡았습니다. 프로젝트 카드 세 개는 각각 독립된 내용이라 `<article>`로 감쌌습니다.
- `figure`와 `figcaption`으로 프로필 사진과 설명을 묶었습니다.
- `blockquote`로 좌우명을 인용 처리했습니다.
- `details`와 `summary`로 기술 스택을 접어 뒀다가 클릭하면 펼쳐지게 했습니다. 그 안에 `<progress value="80" max="100">`으로 학습 진행률을 표시했습니다.
- `ruby`와 `rt`로 `大邱大學校` 위에 '대구대학교' 발음을 붙였습니다.
- `time` 태그에 `datetime` 속성을 넣어 재학 기간과 최종 수정일을 기계가 읽을 수 있는 형식으로 적었습니다.
- `iframe`으로 Google Maps 임베드를 넣어 대구대학교 위치(35.9024, 128.8491)를 표시했습니다. `loading="lazy"`를 붙여 스크롤이 내려올 때 불러오게 했습니다.

## JavaScript로 붙인 것

- `nav`의 앵커 링크에 `preventDefault`를 걸고 `scrollIntoView({ behavior: 'smooth' })`로 부드럽게 이동하게 했습니다.
- `IntersectionObserver`로 `.fade-in` 섹션이 화면에 10% 이상 들어오면 `.visible` 클래스를 붙여 나타나게 했습니다. 한 번 나타난 뒤에는 `unobserve`로 관찰을 끊습니다.

## 알게 된 것

`section`과 `article` 중 뭘 쓸지가 계속 헷갈렸습니다.
잘라내서 다른 데 붙여도 말이 되면 `article`, 페이지의 한 부분이면 `section`으로 정리하고 프로젝트 카드만 `article`로 갔습니다.
`iframe` 안에서는 바깥 CSS가 먹지 않아서 지도 크기는 `width`, `height` 속성으로 직접 줬습니다.

## 파일

| 파일 | 내용 |
| --- | --- |
| `index.html` | 시맨틱 구조와 콘텐츠 |
| `css/styles.css` | 레이아웃, 카드, fade-in 스타일 |
| `js/script.js` | 부드러운 스크롤, IntersectionObserver |
| `assets/` | 커버 이미지와 프로젝트 카드 이미지 |
