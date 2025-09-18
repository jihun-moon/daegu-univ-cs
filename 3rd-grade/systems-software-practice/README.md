# 💻 시스템 소프트웨어, 쉘, 가상화

**[과목 정보]**
- **수강:** 3학년 1학기
- **핵심 기술:** `C`, `Assembly`, `Linux Shell`, `Docker`, `VMware`
- **핵심 역량:** `저수준 시스템 이해`, `가상화 환경 구축`, `컨테이너 기반 배포`

---

## 📖 학습 개요 (Overview)
이 문서는 시스템 소프트웨어의 foundational concepts부터 리눅스 명령어, Bash 쉘 프로그래밍, 그리고 현대적인 가상화 기술과 Docker 컨테이너에 이르기까지, **MLOps 엔지니어의 기반이 되는 핵심 시스템 기술**에 대한 종합적인 학습 내용을 담고 있습니다.

---

## 1. 시스템 소프트웨어 실무 (Systems Software Practice)

### 📖 학습 내용
운영체제(OS)와 하드웨어 사이에서 동작하는 시스템 소프트웨어의 원리를 깊이 있게 탐구했습니다. 컴파일러, 링커, 로더가 소스 코드를 실행 파일로 만들고 메모리에 적재하는 전체 과정을 저수준(low-level) 관점에서 학습하며, **프로세스와 메모리 관리**에 대한 근본적인 이해를 다졌습니다.

### 🚀 적용 실습: 가상 머신(VM) 환경 구축 및 Ubuntu 서버 설치
배운 시스템 지식을 바탕으로, **격리된 개발 및 테스트 환경을 직접 구축**하는 실습을 진행했습니다. VMware Workstation Pro를 사용하여 가상 머신을 생성하고, 대표적인 서버 OS인 Ubuntu Server를 설치하여 MLOps 및 백엔드 개발의 기반이 되는 환경을 마련했습니다.

- **실습 목표:** 가상화 기술의 원리를 이해하고, 재현 가능한(Reproducible) 리눅스 서버 환경을 직접 구축하는 역량을 확보합니다.
- **사용 도구:** `VMware Workstation Pro`, `Ubuntu Server 22.04 LTS`

**[Demo]**
<p align="left">
  <img src="./assets/ubuntu-vm-setup.gif" alt="Ubuntu VM 설치 과정" width="700"/>
  <br/>
  <i>VMware Workstation Pro를 이용해 가상 머신을 생성하고, Ubuntu Server 22.04 LTS 이미지를 설치하여 초기 설정을 완료하는 과정</i>
</p>

### 🌱 성장 및 핵심 경험
-   OS가 하드웨어(물리적 또는 가상)와 어떻게 상호작용하며 부팅되고 설치되는지 그 과정을 직접 경험하며 시스템에 대한 이해를 심화했습니다.
-   격리된 환경의 중요성을 체감하며, 향후 Docker 컨테이너와 같은 더 가볍고 효율적인 가상화 기술의 필요성을 명확히 인지하는 계기가 되었습니다.

---

## 2. 리눅스 명령어 및 BASH 쉘 프로그래밍

### 📖 학습 내용
리눅스 CLI 환경에 익숙해지고, **Bash 쉘 스크립트**를 활용하여 반복적인 작업을 자동화하고 서버 관리 효율성을 극대화하는 능력을 길렀습니다.

### 🚀 MLOps 파이프라인 자동화 적용 예시
아래 예제와 같은 쉘 스크립트 작성 능력은 **ML 모델 학습 과정을 자동화**하거나, **서버의 로그 파일을 주기적으로 백업 및 분석**하는 등 MLOps 파이프라인의 핵심적인 자동화 작업을 수행하는 데 직접적으로 활용될 수 있습니다.

```bash
#!/bin/bash

# 오늘의 날짜로 모델 버전 설정
MODEL_VERSION=$(date +"%Y-%m-%d")
echo "Starting training for model version: $MODEL_VERSION"

# 1. 데이터 전처리 스크립트 실행
python preprocess.py --output_dir "/data/processed"

# 2. 모델 학습 스크립트 실행
python train.py --data_dir "/data/processed" --model_version "$MODEL_VERSION"

# 3. 학습 완료 후 결과 저장
if [ $? -eq 0 ]; then
    echo "Training completed. Model saved to /models/$MODEL_VERSION"
else
    echo "Training failed."
fi
````

-----

## 3\. 가상화 기술 및 Docker

### 📖 학습 내용

가상 머신(VM)과 컨테이너의 차이점을 이해하고, Docker를 활용하여 애플리케이션을 **신속하고 일관성 있게 배포**하는 방법을 익혔습니다.

### 🚀 Python 웹 애플리케이션 Dockerize 적용 예시

간단한 Python Flask 웹 애플리케이션을 **Docker 컨테이너로 패키징**하고, `Docker Compose`를 이용해 DB와 함께 실행하는 가상 시나리오를 통해 학습 내용을 적용했습니다.

**1. `Dockerfile` 작성**

```dockerfile
# Python 3.9를 베이스 이미지로 사용
FROM python:3.9-slim

# 작업 디렉토리 설정
WORKDIR /app

# 의존성 설치
COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

# 소스코드 복사
COPY . .

# 5000번 포트 노출
EXPOSE 5000

# 앱 실행
CMD ["python", "app.py"]
```

**2. `docker-compose.yml` 작성 (Web + Redis DB)**

```yaml
version: '3.8'
services:
  web:
    build: .
    ports:
      - "5000:5000"
  redis:
    image: "redis:alpine"
```

### 🌱 성장 및 핵심 경험

  - **개발-운영 환경 일치**: Docker를 통해 **"내 컴퓨터에선 되는데..." 문제를 원천적으로 해결**하는 경험을 했습니다.
  - **마이크로서비스 아키텍처(MSA) 이해**: 웹 서버와 데이터베이스를 독립적인 컨테이너로 분리하고 `Docker Compose`로 함께 관리하며 MSA의 기반 기술을 이해했습니다.
