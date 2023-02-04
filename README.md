# toyProject-Smart

### 목표
간단한 형태의 쇼핑몰 웹 서비스를 구현하며, 구현시 발생한 문제나 고민한 생각에 대해 정리하는 학습형 토이프로젝트를 진행

### 개발 기간
2023.01.01 ~ 2023.02.28

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
1. 로그인 & 로그아웃
2. 회원가입 및 탈퇴
3. 물품 등록 및 조회 (완료)
4. 결제

### REST API

#### category
|설명| API            |비고|
|---|----------------|---|
|분류 전체 조회| Get : category ||

#### products
| 설명       | API                                                             | 비고                         |
|----------|-----------------------------------------------------------------|----------------------------|
| 제품 단일 조회 | Get : products/{productId}                                      ||
| 제품 전체 조회 | Get : products                                                  ||
| 제품 필터 조회 | Get : products/filter?code={}&search={}&page={}&size={}&sort={} | code:분류번호, search:검색어(제품명) |
| 제품 등록    | Post : products                                                 ||
| 제품 수정    | Post : products/update                                          ||
| 제품 삭제    | Post : products/{productId}/delete                              ||


#### members
| 설명       | API           |비고|
|----------|---------------|---|
| 회원 단일 조회 | Get : members ||
| 회원 가입    | Post : members ||
| 회원 수정    | Post : members/update ||
| 회원 삭제    | Post : members/delete ||


#### orders
| 설명        | API                  |비고|
|-----------|----------------------|---|
| 회원 주문 조회  | Get : orders         ||
| 주문 등록     | Post : orders        ||
| 주문 취소     | Post : orders/cancel ||
| 주문 반품(환불) | Post : orders/refund ||

### 구조
<img width="949" alt="스크린샷 2023-01-13 오후 9 54 06" src="https://user-images.githubusercontent.com/48250370/212324851-7bf4691d-61a6-408a-96ba-4491b5c92928.png">

### 생각&고민
