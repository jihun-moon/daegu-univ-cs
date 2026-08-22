# W01: 기본 태그와 DOM 제어

첫 주차 실습입니다. HTML 제목 태그를 전부 써 보고, CSS로 색과 크기를 준 뒤 JavaScript로 요소를 움직여 봤습니다.

## 구현한 것

- `<h1>`부터 `<h6>`까지 여섯 개를 모두 놓고 같은 '환영합니다!' 텍스트를 출력했습니다. CSS에서 태그별로 다른 색을 줘서 크기 차이가 눈에 들어오게 했습니다.
- `#box`에 400x200px 크기와 `lightcoral` 배경색을 지정했습니다.
- `requestAnimationFrame`으로 `#box`의 `left` 값을 1px씩 150px까지 늘려 오른쪽으로 밀리는 애니메이션을 만들었습니다.
- `<img>`로 사진을 넣고 `max-width: 200px`, `height: auto`를 줘서 원본 비율을 유지한 채 줄였습니다.
- `<script>`에 `defer`를 붙여 `<head>`에서 불러오게 했습니다. HTML 파싱이 끝난 뒤 실행되므로 `getElementById`가 `null`을 반환하지 않습니다.

## 알게 된 것

`#box`에 `position: relative`를 안 주면 JavaScript로 `left`를 아무리 바꿔도 화면이 그대로입니다.
`static` 상태에서는 `left`가 무시되기 때문입니다.
이걸 한참 헤매서 CSS 주석에 "기준점 설정. 필수!"라고 적어 뒀습니다.

## 파일

| 파일 | 내용 |
| --- | --- |
| `index.html` | 제목 태그 6개, `#box`, 이미지 |
| `css/styles1.css` | 제목별 색상, `#box` 크기와 위치 기준, 이미지 크기 제한 |
| `js/script1.js` | `requestAnimationFrame` 애니메이션 |
| `images/my-picture.jpg` | 페이지에 넣은 사진 |
