# 게임 프로그래밍 — 수업 정리

UE5 기반으로 게임 루프, 입력 처리, 렌더링을 통합하고 온라인 멀티플레이 구조(Replication, RPC)를 실습으로 익혔습니다. 레벨 디자인과 시네마틱 제작, 빌드·배포 파이프라인, 디버깅 역량을 강화했습니다.

## 데모
![apartment_cinematic-demo](./assets/apartment_cinematic-demo.gif)

## 핵심 개념 요약
- 변수·자료형: 명시적 상태 관리로 디버깅과 동기화 신뢰도 향상
- 조건·반복: 게임 루프의 상태 전이와 종료 조건 분리
- 함수 분해: 입력 처리, 물리 업데이트, 렌더 준비 모듈화
- 배열·문자열: 버퍼 처리와 컬렉션 순회 최적화
- 디버깅: 로그 채널, 시각 디버그 드로우로 원인 격리

```cpp
// 입력-시뮬 분리 예시
void APlayerPawn::Tick(float dt){
  Super::Tick(dt);
  HandleInput(dt);       // 클라이언트 예측의 기반
  SimulatePhysics(dt);   // 재현성 확보
}
void APlayerPawn::HandleInput(float dt){ MoveInput = GetAxis("Move"); }
void APlayerPawn::SimulatePhysics(float dt){ Position += Velocity * dt; }
// 서버 권위 상태 동기화
UPROPERTY(ReplicatedUsing=OnRep_State) FPlayerStateNet NetState;
```

## 실습 메모
- 이동 튐 현상 → 클라 예측 + 서버 보정, 랙 보정 윈도우 도입 → 일관성 향상
- 몽타주 전이 끊김 → Anim Notifies로 전이 타이밍 조정 → 타격감 개선
- 라이트 과다로 프레임 드랍 → Lumen·라이트 채널 정리 → 목표 FPS 회복

## 실행 방법
1. Unreal Engine 5.x 설치
2. 프로젝트 열기 후 다음 옵션 확인
   - Editor Preference: Use Less CPU when in Background 비활성화
   - Project Settings → Maps & Modes: Default Map 설정
3. PIE 또는 Standalone Game 실행

## 폴더 구조
```
/Config
/Content
/Source              # C++ (있는 경우)
/Saved
assets/              # README 데모 리소스 (gif 등)
README.md
```

## 체크리스트
- [ ] 입력 검증·예외 경로 로깅 적용
- [ ] 입력/시뮬/렌더 준비 함수 분리
- [ ] 반복문 불변식·탈출 조건 명시
- [ ] clang-format·cpplint 통과
- [ ] O(1)/O(n) 등 복잡도 주석화

## 문제 해결 팁
- 입력 후 개행 잔류로 스캔 문제: getline 대체, 트림 처리
- 네트워크 지연: 클라 예측 + 서버 스냅샷 보정
- 성능: 라이트 수·그림자 품질·후처리 단계 점진적 완화

## 기술 스택
UE5, C++, Blueprint

## 라이선스
MIT 또는 프로젝트에 맞는 라이선스 명시
