# banking-server


<div align="center">
<img width="600" alt="스크린샷 2023-03-02 오후 3 06 02" src="https://user-images.githubusercontent.com/62919440/222344883-beba68c3-6e6d-42c7-92bd-762db0aaa323.png">

</div>

## Banking Server v1.0
> **개발기간: 2023.02 ~ 2023.3**

## 프로젝트 소개

은행 서버의 API들을 구현해본 토이 프로젝트입니다.
- 회원가입
- 친구 등록
- 친구 조회
- 계좌 조회
- 계좌 이체

---

## Stacks 🐈

### Environment
![Visual Studio Code](https://img.shields.io/badge/Visual%20Studio%20Code-007ACC?style=for-the-badge&logo=Visual%20Studio%20Code&logoColor=white)
![Git](https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=Git&logoColor=white)
![Github](https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=GitHub&logoColor=white)             

### Development
![Java](https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white)
![MySQL](https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![Spring Boot](https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Spring Security](https://img.shields.io/badge/springsecurity-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white)
![Spring Data JPA](https://img.shields.io/badge/springdatajpa-6DB33F?style=for-the-badge&logo=springdatajpa&logoColor=white)

### Tool
![Notion](https://img.shields.io/badge/Notion-000000?style=for-the-badge&logo=Notion&logoColor=white)

---
## 주요 기능 📦

### ⭐️ 회원가입

### ⭐️ 친구추가, 친구조회

### ⭐️ 계좌조회, 계좌이체
- 친구 사이에만 계좌이체 가능
- 멀티쓰레드 환경에서 계좌 이체시 동시성 문제 해결

---
## 문서

### API 명세서
### ERD
### 테스트 문서
### 프로젝트 설계 문서


---
## 아키텍쳐

### 디렉토리 구조(계층형 패키지 구조)
- 큰 프로젝트가 아니므로 도메인 패키지 구조를 사용하게 되면 각 패키지 내에 한개의 파일만 존재할 가능성이 커 패키지가 불필요할 수 있겠다고 생각했다. 따라서 계층형 패키지 구조로 결정했다.

```
.
├── HELP.md
├── build
├── build.gradle
├── gradle
│   └── wrapper
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── gradlew
├── gradlew.bat
├── settings.gradle
└── src
    ├── main
    │   ├── java
    │   │   └── com
    │   │       └── example
    │   │           └── bankingserver
    │   │               ├── BankingServerApplication.java
    │   │               ├── config/auth : SecurityConfig 
    │   │               ├── domain : Entity / Repository
    │   │               ├── exception : 예외클래스
    │   │               ├── service
    │   │               └── web : 컨트롤러
    │   │                   └── dto
    │   └── resources
    │       ├── application.yml
    │       ├── static
    │       └── templates
    └── test
        └── java
            └── com
                └── example
                    └── bankingserver
                        ├── service : 서비스 메서드 테스트
                        └── web : 컨트롤러 API 테스트

```

