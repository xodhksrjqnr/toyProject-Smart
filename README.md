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

#### category
|설명| API            |비고|
|---|----------------|---|
|카테고리 전체 조회| Get : category ||

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
| 회원 가입    | Post : members/create ||
| 회원 수정    | Post : members/update ||
| 회원 삭제    | Post : members/delete ||
| 회원 로그인    | Post : members/login ||
| 회원 로그아웃    | Post : members/logout ||
| 회원 이메일 인증    | Post : members/certificate/email ||
| 회원 아이디 찾기    | Post : members/certificate/nickname ||
| 회원 비번 찾기    | Post : members/certificate/password ||


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
