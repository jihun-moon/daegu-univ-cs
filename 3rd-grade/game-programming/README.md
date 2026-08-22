# 게임 프로그래밍

3학년 게임 프로그래밍 수업에서 언리얼 엔진 5로 만든 프로젝트 두 개입니다. 하나는 아파트 단지를 짓고 시네마틱 영상으로 뽑는 작업이고, 하나는 우주선으로 드론을 쏘는 슈팅 게임입니다. 둘 다 C++ 없이 블루프린트로만 만들었습니다.

작업하면서 남긴 기록은 [노션 게임 프로그래밍 페이지](https://www.notion.so/5558f838665140ef8b9d802782a91472)에 있습니다.

## 폴더 구조

```
game-programming/
├── apartment-cinematic/    아파트 시네마틱 (Apartment_Cinematic.uproject)
├── shooting-project/       슈팅 게임 (Shooting_Project.uproject)
└── assets/                 README에서 쓰는 캡처와 데모 영상
    ├── apartment-cinematic-demo.gif
    ├── 01-initial-stage/       평면도 참고와 그레이박싱
    ├── 02-intermediate-stage/  모델링과 마감재
    └── 03-final-stage/         조명과 외부 환경
```

`.uasset`, `.umap`, `.fbx`, 이미지는 용량이 커서 Git LFS로 관리합니다. 클론한 뒤 `git lfs install`과 `git lfs pull`을 해야 실제 파일이 내려옵니다. 아파트 프로젝트에는 `.gitignore`를 두어 `Binaries/`, `Build/`, `Intermediate/`, `Saved/`, `DerivedDataCache/`를 뺐습니다.

## 1. Apartment Cinematic

폴더: [apartment-cinematic/](apartment-cinematic/)

아파트 평면도를 참고해 세대 내부를 세우고, 마감재와 가구를 올린 다음, 단지 외부와 앞 도로까지 만들어 시네마틱 영상으로 뽑았습니다. 메인 레벨은 `Content/Levels/` 아래에 있고 카메라는 레벨 시퀀스로 움직였습니다.

가구는 `Content/Fab/`에 받아 둔 에셋과 Megascans를 섞어 쓰고, 나무와 화단은 식생 팩(BlackAlder, NorwayMaple, Chestnuts_Pack, Field_Plants_Pack, PN_MeadowFlowers)을 썼습니다. 인물은 메타휴먼을 넣었습니다. 대신 옷장, 냉장고 문, 도로 차선, 창틀 프레임, 화장실 대리석처럼 받은 에셋으로 안 되는 부분은 `Content/Materials/`에 머티리얼을 따로 만들어 붙였고 53개가 들어 있습니다. 렌더링은 언리얼 5 기본값인 Lumen과 버추얼 섀도 맵을 그대로 썼습니다.

### 1단계. 평면도 참고와 그레이박싱

재질을 올리기 전에 체크 머티리얼 박스만으로 벽, 문, 창 위치를 먼저 잡았습니다. 이 단계에서 방 크기와 동선을 정해 두지 않으면 나중에 가구를 넣을 때 전부 다시 옮겨야 해서, 평면도를 옆에 띄워 놓고 치수를 맞췄습니다.

| 참고한 아파트 평면도 | 그레이박싱한 전체 배치 |
| --- | --- |
| <img src="assets/01-initial-stage/apartment-floor-plan.jpg" alt="참고한 아파트 평면도" width="400"> | <img src="assets/01-initial-stage/level-layout-design.png" alt="그레이박싱한 전체 배치" width="400"> |
| **주방** | **거실** |
| <img src="assets/01-initial-stage/blocking-kitchen.png" alt="그레이박싱 단계 주방" width="400"> | <img src="assets/01-initial-stage/blocking-living-room.png" alt="그레이박싱 단계 거실" width="400"> |

현관과 전체 조망 컷은 [assets/01-initial-stage/](assets/01-initial-stage/)에 함께 있습니다.

### 2단계. 모델링과 마감재

창호, 붙박이장, 주방 가구를 넣고 바닥과 벽 재질을 바꿨습니다. 세대 하나만 따로 떼어 외부에서 보면서 창문 위치와 층고를 확인했습니다.

| 거실 마감 | 가구를 넣은 거실 |
| --- | --- |
| <img src="assets/02-intermediate-stage/modeling-living-room-01.png" alt="마감재를 올린 거실" width="400"> | <img src="assets/02-intermediate-stage/living-room-view-01.png" alt="가구를 배치한 거실" width="400"> |
| **주방** | **세대 외부에서 본 모습** |
| <img src="assets/02-intermediate-stage/modeling-kitchen-view.png" alt="마감재를 올린 주방" width="400"> | <img src="assets/02-intermediate-stage/modeling-exterior-view.png" alt="세대 외부" width="400"> |

복도, 방, 세탁실 컷은 [assets/02-intermediate-stage/](assets/02-intermediate-stage/)에 있습니다.

### 3단계. 외부 환경과 조명

세대 내부만으로는 영상이 짧아 동 전체와 앞 도로, 주차장, 가로수까지 만들었습니다. 마지막에 해질녘 라이팅과 안개, 가로등을 넣었는데 조명 하나 바꿀 때마다 분위기가 크게 달라져서 시간이 제일 많이 든 단계였습니다.

| 단지 전경 | 도로에서 본 컷 |
| --- | --- |
| <img src="assets/03-final-stage/final-street-sunset-view-02.png" alt="해질녘 단지 전경" width="400"> | <img src="assets/03-final-stage/final-street-sunset-view-03.png" alt="도로에서 본 해질녘 컷" width="400"> |
| **동 외관** | **옥상에서 내려다본 배치** |
| <img src="assets/03-final-stage/final-building-exterior-view.png" alt="동 외관" width="400"> | <img src="assets/03-final-stage/final-rooftop-view.png" alt="옥상에서 본 단지 배치" width="400"> |

같은 폴더의 `final-street-day-view.png`는 렌더 결과가 아니라 도로와 화단 배치를 잡을 때 참고한 실제 로드뷰 사진입니다.

### 데모 영상

<img src="assets/apartment-cinematic-demo.gif" alt="아파트 시네마틱 데모" width="600"/>

GIF 용량이 90MB가 넘어서 페이지에서 재생되기까지 시간이 걸립니다.

## 2. Shooting Project

폴더: [shooting-project/](shooting-project/)

우주선을 상하좌우로 움직이며 드론을 쏘는 슈팅 게임입니다. 시작 맵은 `Content/Levels/MainMap`이고 기본 게임 모드는 `BP_GameModeBase`로 설정해 뒀습니다.

| 구성 | 위치 |
| --- | --- |
| 블루프린트 | `Content/Blueprints/`의 `BP_Player`, `BP_Bullet`, `BP_Enemy`, `BP_EnemyFactory`, `BP_GameModeBase` |
| 입력 | `Content/inputs/`의 `IMC_PlayerInput`과 `IA_Horizontal`, `IA_Vertical`, `IA_Fire` (Enhanced Input) |
| UI | `Content/UI/BP_MainWidget` |
| 모델 | `Content/Modelings/`의 우주선 `Spaceship_ARA.fbx`, 적기 `Drone_low.FBX`와 각각의 텍스처 |
| 배경 | `Content/Material/M_Background`와 `Content/Resources/`의 우주 배경 이미지 |
| 사운드 | `Content/Audio/bullet.wav` |

입력은 Enhanced Input으로 잡았습니다. 좌우 이동, 상하 이동, 발사를 각각 `IA_Horizontal`, `IA_Vertical`, `IA_Fire`로 나누고 키 매핑은 `IMC_PlayerInput` 한 곳에 모아 두어서, 조작을 바꿀 때 블루프린트를 열지 않아도 됐습니다. 적기도 레벨에 직접 배치하지 않고 `BP_EnemyFactory`로 분리했습니다.

## 실행 방법

각 폴더의 `.uproject` 파일을 언리얼 엔진 5에서 열면 됩니다. 아파트 프로젝트는 시작 맵이 템플릿 맵으로 잡혀 있어서, 열린 뒤에 `Content/Levels/` 아래 레벨을 직접 열어야 작업한 단지가 보입니다.
