# toyProject-Smart

### 목표
간단한 형태의 쇼핑몰 웹 서비스를 구현하며, 구현시 발생한 문제나 고민한 생각에 대해 정리하는 학습형 토이프로젝트를 진행

### 개발 기간
2023.01.01 ~ 2023.02.12

### 개발 인원
- Frontend : 1명
- Backend : 1명

### 개발 환경
- Spring Boot 2.7.2
- Java 11
- MySQL
- Spring Data JPA
- Lombok
- ReactJS

### 구현 기능
1. 로그인 & 로그아웃 (with JWT)
2. 회원가입 (+이메일 인증)
3. 제품 등록 및 조회(+필터)
4. 장바구니
5. 제품 주문 취소, 환불

### 구현 화면
|메인 페이지, 제품 상세 페이지|
|:----------|
|<img width="600px" src="https://user-images.githubusercontent.com/115006670/218263886-71272601-e45f-4077-b076-fb8a5880cea6.gif">|

|카테고리 제품|
|:----------|
|<img width="600px" src="https://user-images.githubusercontent.com/115006670/218262576-8507bfd1-6d2a-4aba-99ef-7dcec374374d.gif">|

|제품 검색|
|:----------|
|<img width="600px" src="https://user-images.githubusercontent.com/115006670/218263799-29d52a9d-27d2-4d0c-b3bd-f3a1a89a37ce.gif">|

|회원가입, 로그인, 로그아웃|
|:----------|
|<img width="600px" src="https://user-images.githubusercontent.com/115006670/218263146-d9569278-1f7a-4e6c-9aaa-1c12fd76f82d.gif">|

|회원 찾기(ID, PW)|
|:----------|
|<img width="600px" src="https://user-images.githubusercontent.com/115006670/218262385-3898a52a-c65a-46e1-835c-1b34c0a9f083.gif">|

|장바구니|
|:----------|
|<img width="600px" src="https://user-images.githubusercontent.com/115006670/218262513-5b21913f-5035-4575-99b8-8a218c935f74.gif">|

|주문 취소, 환불|
|:----------|
|<img width="600px" src="https://user-images.githubusercontent.com/115006670/218263582-a849e9f3-237e-4336-8854-939534fe5a6f.gif">|


|관리자 페이지|
|:----------|
|<img width="600px" src="https://user-images.githubusercontent.com/115006670/218262169-b75d4f93-29b4-4d4c-9b79-187d8a3c2d3d.gif">|


### REST API

[API 명세서](https://github.com/xodhksrjqnr/toyProject-Smart/wiki/API-%EB%AA%85%EC%84%B8)

#### Category
| URL               | Descriptions            |
|-------------------|----------------|
| **GET** /category |카테고리 전체 조회|

#### Products
| URL                                                         | Descriptions            |
|-------------------------------------------------------------|----------------|
| **GET** /products/{productId}                               |제품 단일 조회|
| **GET** /products?page={}&size={}&sort={}                   |제품 전체 조회|
| **GET** /products?page={}&size={}&sort={}&search={}&code={} |제품 리스트 필터 조회|
| **POST** /products                                          |제품 등록|
| **POST** /products                                          |제품 수정|
| **POST** /products/{productId}/delete                       |제품 삭제|


#### Members
| URL                                    |Description|
|----------------------------------------|---|
| **POST** /members/create               |회원가입|
| **POST** /members/login                |로그인|
| **POST** /members/logout               |로그아웃|
| **POST** /members/certificate/email    |이메일 인증|
| **POST** /members/certificate/nickname |아이디 찾기|
| **POST** /members/certificate/password |비밀번호 찾기|
| **POST** /members/refresh              |토큰 재발행|


#### Orders
|URL          | Description |
|--------------|-------------|
|**GET** /orders| 주문 목록 조회    |
|**POST** /orders| 주문 등록       |
|**POST** /orders/cancel| 주문 취소       |
|**POST** /orders/refund| 주문 환불       |

### ERD
<img width="985" alt="ERD" src="https://user-images.githubusercontent.com/48250370/228131323-4c28bec6-93ec-4a00-b3b6-3616ce115ebc.png">

### 구조
<img width="949" alt="스크린샷 2023-01-13 오후 9 54 06" src="https://user-images.githubusercontent.com/48250370/212324851-7bf4691d-61a6-408a-96ba-4491b5c92928.png">

### 생각&고민
토이 프로젝트 종료후 코드를 다시 살펴보는 과정에서 구현에만 치중했다는 점이 확연히 드러났다. 따라서 코드 전체를 리팩토링하는 과정을 진행하게 되었으며 주제에 따라 나누어서 정리할 계획이다.

- [Entity 리팩토링](https://github.com/xodhksrjqnr/toyProject-Smart/wiki/Entity-%EC%9D%91%EC%A7%91%EC%84%B1-%ED%96%A5%EC%83%81)
