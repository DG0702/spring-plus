# ▶️ SPRING PLUS

<hr>

## 🖥️ 개발 프로세스 환경

### ⛏️IDE :
- `Intellij`

### 📌 Java :
- **JDK 17버전 사용**

### 📌 Springboot :
- **Springboot 3.3.3 사용**

### 🔁 Version Control :
- **`Git`**

### 🥏 데이터베이스 :
- **MySQL 사용**

### 📍 AWS EC2 활용 :
- **EC2 인스턴스 생성**

  - **`Java jdk17`, `docker`, `docker → MySQL`**


- **탄력적 IP 생성,할당**

 
- **healthCheckAPI 확인**

<hr>

## 🔶 주요기능

- **회원가입 , 로그인 (JWT 토큰 기반)**


- **TODO 생성, 조회, 검색**


- **댓글 생성, 조회**


- **AWS EC2 인스턴스 연걸(Health Check API)**

## 📜 주요 API 명세서

### 인증 API
- `POST /auth/signup` - 회원가입


- `POST /auth/signin` - 로그인

### Todo API
- `POST /todos` - Todo 생성


- `GET /todos` - Todo 목록 조회


- `GET /todos/{todoId}` - Todo 상세 조회


- `GET /todosSearch` - Todo 검색

### 댓글 API
- `POST /todos/{todoId}/comments` - 댓글 작성


- `GET /todos/{todoId}/comments` - 댓글 목록 조회


### 매니저 API
- `POST /todos/{todoId}/managers` - 매니저 할당


### HealthCheckAPI

- `GET /health` -HealthCheckAPI

<hr>

## AWS 서비스 설정 내역

<hr>

### EC2(Elastic Compute Cloud)

![img.png](img.png)

- **인스턴스 생성, 실행**
  

- **탄력적 IP 생성, 할당**


- **SSH에서 애플리케이션 실행**

