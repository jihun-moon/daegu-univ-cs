# System Software Practice (시스템 SW 실무)

C, Assembly, Linux, Docker를 중심으로 커널–유저 공간 경계 이해부터 가상화, 서버 운영 자동화, 컨테이너 배포까지 실습했습니다. 재현 가능한 개발·운영 환경을 구축하고 운영 절차를 코드화했습니다.

- OS/Virtualization: Ubuntu VM, VT‑x/AMD‑V, Hypervisor basics
- Linux Ops: Bash, systemd, 네트워킹, 권한 관리
- Containerization: Dockerfile, Multi-stage build, Docker Compose

## 🎥 Demo
![Ubuntu VM Usage](assets/ubuntu-vm-usage.gif)

assets 경로/파일명은 실제 레포에 맞게 수정하세요.

## What I Built
- Reproducible Ubuntu VM 템플릿과 스냅샷 워크플로
- Bash 기반 데이터 처리·로그 수집 파이프라인
- Dockerfile + Compose로 웹/DB 등 멀티 서비스 로컬 오케스트레이션

## Key Concepts
- 링 보호 모델, 커널/유저 공간, 시스템 콜 경계
- systemd(Unit/Timer/Journal), 권한(UGO, sudoers), 네트워크(브리지/NAT)
- 이미지 최적화(멀티스테이지, 캐시/레이어 관리), .dockerignore
- Compose 환경 분리(.env), 의존성, Healthcheck

## Examples

systemd 타이머로 주기 작업 자동화
```ini
# /etc/systemd/system/logrotate-job.service
[Unit]
Description=Rotate logs

[Service]
Type=oneshot
ExecStart=/usr/sbin/logrotate /etc/logrotate.conf
```

```ini
# /etc/systemd/system/logrotate-job.timer
[Unit]
Description=Run logrotate hourly

[Timer]
OnCalendar=hourly
Persistent=true

[Install]
WantedBy=timers.target
```

Docker 멀티스테이지 빌드
```dockerfile
# syntax=docker/dockerfile:1
FROM golang:1.22 AS build
WORKDIR /app
COPY . .
RUN CGO_ENABLED=0 go build -o app

FROM gcr.io/distroless/base-debian12
COPY --from=build /app/app /usr/local/bin/app
USER nonroot
ENTRYPOINT ["/usr/local/bin/app"]
```

Compose 예시
```yaml
version: "3.9"
services:
  api:
    build: .
    ports: ["8080:8080"]
    env_file: .env
    healthcheck:
      test: ["CMD", "curl", "-f", "http://localhost:8080/health"]
      interval: 10s
      timeout: 2s
      retries: 3
  db:
    image: mysql:8.4
    environment:
      MYSQL_ROOT_PASSWORD: ${MYSQL_ROOT_PASSWORD}
    volumes:
      - db_data:/var/lib/mysql
volumes:
  db_data:
```

## Troubleshooting
- VM 네트워크 통신 불가 → 브리지/NAT 구분 적용, 게이트웨이/서브넷 확인
- 이미지 과체중 → 멀티스테이지 빌드, 패키지 캐시 삭제, distroless/ubi‑micro 사용
- 주기 작업 실패 → systemd timer + journalctl ‑u로 원인 추적, Restart 정책 설정

## Checklist
- [ ] 최소 권한 원칙(사용자/그룹/권한/ sudoers) 적용
- [ ] systemd Unit에 ExecStartPre·Restart·Timeout 정의
- [ ] Dockerfile 고정 태그/healthcheck 및 .dockerignore 설정
- [ ] Compose .env로 환경 분리, 네트워크/볼륨 명시
- [ ] 보안 업데이트·로그 로테이션 스케줄 적용

## Folder Structure
```
/assets/                  # README 데모(gif)
/vm/                      # VM 세팅 스크립트/문서(있다면)
/scripts/                 # Bash 자동화 스크립트
/docker/                  # Dockerfile, compose.yaml
README.md
```

## How to Reproduce
1. VM 준비  
   - Ubuntu 설치 → 브리지/NAT 설정 → 스냅샷 생성
2. 시스템 자동화  
   - scripts/ 하위 스크립트 배치, systemd Unit/Timer 등록
3. 컨테이너 실행  
   - docker build -t app .  
   - docker compose up -d

## Roadmap
- [ ] CI/CD 파이프라인 연결(빌드/보안 스캔/서명)
- [ ] SBOM 생성 및 취약점 스캔(Grype/Trivy)
- [ ] 로그/메트릭 수집(Stack: Prometheus + Loki/Grafana)

## Links
- Notion: 시스템SW실무 페이지
- 실습 레포/문서 링크 추가

## License
MIT (또는 팀/과목 정책에 맞게 명시)
