아래를 README.md로 복붙하면 됩니다. 파일명은 assets 폴더 이미지 기준으로 맞춰두었습니다.[[1]](https://github.com/jihun-moon/daegu-univ-cs/tree/main/2nd-grade/discrete-mathematics)

# Discrete Mathematics Notes

논리와 증명, 집합·함수·관계, 수열·점화식, 그래프 이론의 핵심을 면접용으로 간단 명료하게 정리했습니다. 알고리즘 정확성·복잡도 분석으로 자연스럽게 연결됩니다.

- 학년: 2학년
- 과목: 이산수학
- 레포 목적: 과제 풀이 스캔본과 핵심 요약 아카이브

## 폴더 구조

- assets
  - discrete-math-assignment-1.jpg
  - discrete-math-assignment-2.jpg
  - discrete-math-assignment-3.jpg
  - discrete-math-assignment-4.jpg
- README.md

## 3줄 요약

- 명제 논리와 증명 기법으로 문제를 형식화하고 타당성을 검증
- 집합·함수·관계, 수열·점화식, 그래프 이론 기초를 정확한 표기법으로 정리
- 알고리즘 정확성 증명과 시간복잡도 분석의 기반 확립

## 핵심 개념 5개

1) 명제 논리  
(p → q) ≡ (¬p ∨ q)  
// 언제/왜: 조건문 변형, 대우 증명, 스펙 모호성 제거

2) 집합 · 함수 · 관계  
전단사 f: A→B ⇔ 역함수 f⁻¹ 존재  
// 언제/왜: 데이터 모델링, 등가관계·부분순서 설계

3) 증명 기법  
귀납법: (기저) P(1), (귀납) P(k)→P(k+1) ⇒ ∀n P(n)  
// 언제/왜: 점화식 해, 알고리즘 정확성 증명

4) 수열 · 점화식  
aₙ = r·aₙ₋₁, a₀=c ⇒ aₙ = c·rⁿ  
// 언제/왜: 재귀 알고리즘 T(n) 추정의 기초

5) 그래프 이론 기초  
무향 그래프: Σ deg(v) = 2|E|  
// 언제/왜: 경로·연결성·트리 성질 분석, DFS/BFS로 연결

## 실습 메모

- 대우와 역 혼동 → 진리표로 (¬q→¬p)만 동치 확인 → 조건문 해석 정확도 향상  
- 귀납 증명에서 가정 미사용 → 전개식에 P(k) 대입 → 증명 구조 체득  
- 그래프 용어 혼선 → 정의 표 비교 정리 → 문제 번역 능력 강화

## 제출 전 체크리스트

- [ ] 정의·정리·기호를 정확히 인용하고 일관되게 사용했다  
- [ ] 증명에 가정·목표·전개·결론·반례 검토가 포함됐다  
- [ ] 점화식 해 또는 진리표/등가변형의 근거를 명시했다  
- [ ] 그래프 입력 모델(V, E, 방향성, 가중치)을 먼저 선언했다  
- [ ] 풀이의 시간/공간 복잡도를 수식으로 표기했다

## 스크린샷

- 과제 1  
  <image source="assets/discrete-math-assignment-1.jpg">assignment-1</image>
- 과제 2  
  <image source="assets/discrete-math-assignment-2.jpg">assignment-2</image>
- 과제 3  
  <image source="assets/discrete-math-assignment-3.jpg">assignment-3</image>
- 과제 4  
  <image source="assets/discrete-math-assignment-4.jpg">assignment-4</image>

## 참고

- 강의 자료 요약 링크와 과제 스캔은 레포 내 docs 또는 위 이미지 참조  
- 그래프 이론 기초와 표기법 레퍼런스 추가 가능

필요하면 깃허브 레포에 맞춰 배지, 목차, 라이선스 섹션도 추가해 드릴게요.
