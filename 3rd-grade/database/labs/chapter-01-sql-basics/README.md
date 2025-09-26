# **데이터에 관심을 가져야 하는 이유**

## **폭발적으로 증가하는 데이터**
<mark>정보 기술의 발전과 디지털화로 인해 막대한 양의 데이터가 생성되고 있다.</mark> 또한, 데이터 기술과 클라우드 컴퓨팅의 발전 덕분에 데이터를 저장하고 처리하는 비용이 감소하면서 데이터 증가율은 더욱 커지고 있다.

그렇다면 얼마나 많은 데이터가 생성되고 있을까? 미국 시장조사기관 IDC에 따르면 2020년을 기준으로 <mark>**전 세계 디지털 정보량은 60제타바이트(ZB, $10^{21}$ Bytes)**</mark> 수준이라고 한다. 이는 지구상의 모든 모래알 수의 약 1,282배에 달하는 양이다. 2025년에는 **175제타바이트**에 이를 것으로 예상되고 있다. 이러한 데이터의 폭발적 증가에 따라, 데이터를 다양한 도구와 모델을 이용해 분석하고 새로운 가치를 도출할 수 있는 데이터 활용 능력은 데이터 경제 시대에서 살아남기 위한 기업과 개인에게 필수적인 요소가 되었다.

---

## **데이터의 형태**
데이터는 크게 **정형 데이터(Structured Data)**, **반정형 데이터(Semi-Structured Data)**, **비정형 데이터(Unstructured Data)** 로 나뉜다.

### **1. 정형 데이터(Structured Data)**
정형 데이터는 <mark>**미리 정해진 구조에 따라 저장된 데이터**</mark>를 의미한다. 정형 데이터는 <mark>**엑셀 스프레드시트, 관계형 데이터베이스(RDBMS)의 테이블**</mark>과 같이 행(Row)과 열(Column)로 구성되며, <mark>데이터 검색, 삽입, 수정, 삭제가 용이하다.</mark> 주로 정형화된 업무나 서비스에 사용된다.

<p align="left">
<img src="https://github.com/user-attachments/assets/af58179f-5758-445c-b721-5db14d6533e6" width="250">
</p>

### **2. 반정형 데이터(Semi-Structured Data)**
반정형 데이터는 <mark>**정형 데이터처럼 일정한 구조를 가지고 있지는 않지만, 데이터 내부에 구조를 설명하는 메타데이터를 포함하는 데이터**</mark>이다. 예를 들어, <mark>JSON, XML, HTML 파일</mark>과 같이 특정한 형식을 따르지만 자유롭게 데이터를 포함할 수 있다.

<p align="left">
<img src="https://github.com/user-attachments/assets/fa5b3ffa-df52-48a2-ab53-b898680c8ea2" width="250">
</p>

### **3. 비정형 데이터(Unstructured Data)**
비정형 데이터는 <mark>**정해진 구조나 규칙이 없고, 연산에 직접 사용하기 어려운 데이터**</mark>이다. 대표적으로 <mark>멀티미디어 데이터(이미지, 영상, 음성 파일), 텍스트 데이터(PDF, 워드 문서), 소셜 미디어 데이터(트위터, 페이스북 게시글)</mark> 등이 포함된다.

<p align="left">
<img src="https://github.com/user-attachments/assets/f8863a52-5dc4-4d2e-8abc-3e74d9d9f16d" width="250">
</p>

---

## **데이터베이스와 데이터베이스 관리 시스템(DBMS)**

<mark>데이터베이스(Database)는 여러 사람이 공유하고 운영할 목적으로 관리되는 통합적인 정보의 집합으로 컴퓨터 시스템에 전자적으로 저장되는 구조화된 데이터 모음이다. 데이터베이스는 데이터베이스 관리 시스템(DataBase Management System, DBMS)에 의해 관리된다.</mark>

<mark>DBMS는 최종 사용자 또는 응용 프로그램이 데이터베이스에 접근할 수 있는 인터페이스 역할을 하며, 저장된 데이터를 보다 체계적으로 관리하고 이용할 수 있게 해주는 소프트웨어.</mark>

---
## **관계형 데이터베이스**
<mark>관계형 데이터베이스(Relational Database)는 관계형 데이터 모델을 기반으로 한 데이터베이스.</mark>

### **테이블(Table)**
- <mark>릴레이션(Relation)이라고도 하며 동일한 주제의 데이터 집합으로 이루어짐.</mark>
- <mark>1개 이상의 열과 0개 이상의 행으로 이루어진 데이터 집합.</mark>
- 데이터베이스 내에서 유일한 이름을 가져야 함.

### **열(Column)**
- <mark>속성(Attribute), 필드(Field)라고도 함.</mark>
- <mark>의미가 더 이상 분리되지 않는 최소의 데이터 단위.</mark>
- 한 테이블 내에서 유일한 열 이름을 가져야 하며 문자, 숫자, 날짜 등 자신만의 데이터타입을 가지고 있음.

### **행(Row)**
- <mark>튜플(Tuple), 레코드(Record)라고도 함.</mark>
- <mark>관련 있는 열의 묶음.</mark>
- <mark>한 테이블에 저장된 모든 행은 동일한 수의 열을 가짐.</mark>

### **관계(Relationship)**
- <mark>두 테이블 간에 서로 연결되는 방식을 의미, 데이터의 종속성을 표현</mark>.

### **기본키(Primary key)**
- <mark>주된 식별자.</mark>
- <mark>한 테이블 내에서 행을 구별할 수 있는 열 또는 열의 묶음.</mark>

<p align="left">
<img src="https://github.com/user-attachments/assets/a0bc04d8-6911-42dc-bd44-63fb8c73e2a3" width="850">
</p>

---

## **MySQL**
- MySQL은 <mark>1995년에 발표된 오픈 소스 RDBMS</mark>입니다.
- <mark>웹 애플리케이션용으로 설계 및 최적화되었기 때문에 모든 플랫폼에서 실행할 수 있음.</mark> 현재 <mark>MySQL은 Oracle, MS-SQL, PostgreSQL 등과 함께 4대 RDBMS</mark>로서 자리를 차지.

---
# **SQL의 개요**

## **SQL을 배워야 하는 이유**
- SQL은 <mark>데이터에 접근하기 위한 가장 보편적인 수단.</mark>
- <mark>아마존 레드시프트 ML, 구글 빅쿼리 ML, 스노플레이크, 오라클 클라우드 인프라 데이터 사이언스, 마이크로소프트 SQL 서버 머신러닝 서비스 등 클라우드 기반의 머신러닝 플랫폼에서도 SQL이 사용.</mark>

## **SQL의 개념과 역할**
SQL(Structured Query Language)은 <mark>**IBM이 1970년대에 개발한 SEQUEL을 기반으로 만들어진 언어**</mark>로, 관계형 데이터베이스 관리 시스템(RDBMS)에서 데이터를 관리하고 다양한 데이터 동작을 수행하는 표준 프로그래밍 언어이다.

<mark>SQL은 데이터베이스 및 테이블 생성, 데이터 검색, 추가, 수정, 삭제 및 트랜잭션 처리, 보안 및 권한 제어 등 데이터베이스의 모든 측면을 관리하는 데 사용.</mark>


## **SQL의 특징**
- <mark>**배우기 쉽고 사용하기 용이**</mark>: 영어 문장과 비슷한 구문을 가지므로 쉽게 학습 가능.
- <mark>**절차적이지 않음**</mark>: 연산을 절차 없이 집합 단위로 처리.
- <mark>**사용자 편의 중심 언어**</mark>: 사용자가 원하는 데이터를 얻는 것에 집중.
- <mark>**미국표준협회(ANSI)**</mark>: 표준에 맞게 SQL을 작성한다면 상용 RDBMS 간에 호환과 전환이 용이.

---

## **SQL문의 종류**
### **1. 데이터 정의어(DDL, Data Definition Language)**
<mark>데이터베이스의 **구조를 정의**할 때 사용되는 언어.</mark>

**주요 DDL 명령어**:
- `CREATE` : <mark>데이터베이스 객체(테이블, 뷰, 인덱스 등) 생성</mark>
- `ALTER` : 기존 객체의 구조 <mark>변경</mark>
- `DROP` : 객체 <mark>삭제</mark>
- `TRUNCATE` : 테이블 내 모든 데이터 삭제

### **2. 데이터 조작어(DML, Data Manipulation Language)**
<mark>데이터를 관리하는 언어로, 데이터 추가, 삭제, 변경을 수행.</mark>

**주요 DML 명령어**:
- <mark>`SELECT` : 조건에 맞는 데이터 검색</mark> -> <mark> 데이터 조작어에 포함하기도 하고 데이터 질의어(Data Query Language, DQL)로 분리하기도 함.</mark>
- `INSERT` : 새로운 레코드 삽입
- `UPDATE` : 데이터 값 수정
- `DELETE` : 데이터 삭제

### **3. 데이터 제어어(DCL, Data Control Language)**
<mark>데이터 접근 권한을 제어하는 언어.</mark>

**주요 DCL 명령어**:
- `GRANT` : 특정 사용자에게 권한 부여
- `REVOKE` : 기존 권한 철회

### **4. 트랜잭션 제어어(TCL, Transaction Control Language)**
<mark>데이터 변경 사항을 관리하는 언어.</mark>

**주요 TCL 명령어**:
- `COMMIT` : 변경된 내용을 영구 저장
- `ROLLBACK` : 변경 사항을 취소

---
