# 🌐 Computer Networks – ARP Packet Analysis & IPv4 vs IPv6

> ### 3줄 요약
>
>   - **실습 분석**: Wireshark를 사용하여 ARP Request/Reply 패킷을 직접 캡처하고, 각 헤더 필드를 상세히 분석합니다.
>   - **이론 비교**: IPv4와 IPv6의 주소 체계, 헤더 구조, 핵심 기능을 표로 비교하고 전환 전략을 정리합니다.
>   - **결과물**: 실제 캡처 스냅샷과 비교 분석표를 포함한 기술 리포트 형식의 문서입니다.

-----

## 📂 리포트 구조

```
computer-networks/
├── README.md
└── assets/
    ├── wireshark-arp-analysis.png   # ARP 캡처 및 분석 이미지
    └── ipv6-report.png              # IPv4/IPv6 비교 다이어그램
```

-----

## 1\. ARP 패킷 분석 | Wireshark

### 목적

IP 주소와 MAC 주소 간의 매핑 과정(주소 결정 프로토콜)을 실제 네트워크 트래픽 캡처를 통해 검증하고, ARP 헤더의 각 필드가 갖는 의미를 이해합니다.

### 실험 환경

  - **도구**: Wireshark 최신 버전
  - **네트워크**: 동일 LAN 환경
  - **호스트**: (여기에 OS, NIC 모델 등 간단한 정보 기재)

### 캡처 절차

1.  Wireshark에서 분석할 네트워크 인터페이스를 선택하고 캡처를 시작합니다.
2.  디스플레이 필터에 `arp`를 입력하여 ARP 패킷만 필터링합니다.
3.  터미널에서 `arp -d *` 명령어로 ARP 캐시를 초기화한 후, 동일 네트워크 내 다른 호스트로 `ping`을 전송하여 ARP 트래픽을 유도합니다.
4.  Request(브로드캐스트)와 Reply(유니캐스트) 프레임이 모두 캡처되었는지 확인합니다.

### 분석 항목

| 구분 | 내용 | 확인 포인트 |
| :--- | :--- | :--- |
| **Ethernet 헤더** | Destination 주소 | Request 시 `ff:ff:ff:ff:ff:ff`, Reply 시 특정 MAC 주소 |
| **ARP 헤더** | `Opcode` 필드 | `1` (Request) 또는 `2` (Reply) |
| | `Sender/Target` 주소 | 각 요청과 응답에 따른 MAC 및 IP 주소 값 |
| **통신 흐름** | Request / Reply | "Who has [Target IP]? Tell [Sender IP]" / "[Target IP] is at [Target MAC]" |

#### 관찰 포인트

  - **Request는 브로드캐스트, Reply는 유니캐스트**: ARP 요청은 동일 네트워크 내 모든 호스트에게 전달되고, 응답은 요청한 호스트에게만 전달됩니다.
  - **동일 서브넷 내 동작**: ARP는 기본적으로 라우터를 넘어가지 않으며, 각 네트워크 홉(Hop) 별로 독립적으로 수행됩니다.

#### 스냅샷

\<img src="assets/wireshark-arp-analysis.png" alt="ARP Analysis" width="800"/\>

-----

## 2\. IPv4 · IPv6 비교 분석

### 목적

IPv4 주소 고갈 문제의 배경을 이해하고, 차세대 인터넷 프로토콜인 IPv6의 도입 필요성을 주소 체계, 헤더 구조, 주요 기능 관점에서 비교하고 정리합니다.

### 핵심 비교표

| 항목 | IPv4 | IPv6 | 비고 |
| :--- | :--- | :--- | :--- |
| **주소 길이** | 32비트 | 128비트 | 주소 공간의 폭발적 증가 |
| **표기법** | 10진수와 점 (e.g., `192.0.2.1`) | 16진수와 콜론 (e.g., `2001:db8::1`) | 연속된 0 그룹 축약 지원 |
| **헤더 크기** | 가변 (20 바이트 + 옵션) | 고정 (40 바이트) | 라우터 처리 성능 향상 (단순화) |
| **주소 설정**| 수동 / DHCP | **SLAAC** (자동), DHCPv6 | 플러그 앤 플레이 강화 |
| **보안** | IPsec (선택 사항) | **IPsec** (기본 내장) | 종단 간 보안 표준화 |
| **브로드캐스트**| 지원 | **미지원** | 멀티캐스트/애니캐스트로 대체 |
| **QoS** | ToS / DiffServ | **Flow Label** | 특정 트래픽 흐름 식별 용이 |
| **NAT 의존도** | 높음 | 낮음 (거의 불필요) | 진정한 종단 간(End-to-End) 원칙 회복 |

### 전환 시 고려사항

  - **듀얼 스택 (Dual Stack)**: 하나의 장비에서 IPv4와 IPv6를 모두 운영
  - **터널링 (Tunneling)**: IPv4 네트워크를 통해 IPv6 패킷을 캡슐화하여 전송 (e.g., 6to4)
  - **주소 변환 (Translation)**: NAT64/DNS64 등을 이용해 두 프로토콜 간 통신 지원

#### 다이어그램

\<img src="assets/ipv6-report.png" alt="IPv4 vs IPv6" width="800"/\>

-----

## 📜 변경 이력

  - **v1.0.0**: 최초 작성. ARP 패킷 분석, IPv4/IPv6 비교, 관련 이미지 2종 포함.
