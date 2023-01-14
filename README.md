# toyProject-Smart

### 목표
간단한 형태의 쇼핑몰 웹 서비스를 구현하며, 구현시 발생한 문제나 고민한 생각에 대해 정리하는 학습형 토이프로젝트를 진행

### 개발 기간
2023.01.01 ~ 2023.01.31

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
3. 물품 등록 및 조회
4. 결제

### REST API

|설명| API            |비고|
|---|----------------|---|
|분류 전체 조회| Get : category ||

|설명| API                                              | 비고                     |
|---|--------------------------------------------------|------------------------|
|물품 단일 조회| Get : product/{productId}                        ||
|물품 전체 조회| Get : product                                    ||
|물품 등록 양식| Get : product/create                             ||
|물품 수정 양식| Get : product/{productId}/update                 ||
|물품 필터 조| Get : product/filter?code={}&option={}&gender={} | code:분류번호, option:필터조건 |
|물품 등록| Post : product                                   ||
|물품 수정| Post : product/update                            ||
|물품 삭제| Delete : product/{productId}                     ||

|설명| API                             |비고|
|---|---------------------------------|---|
|회원 단일 조회| Get : member/{memberId}         ||
|회원 전체 조회| Get : member                    ||
|회원 가입 양식| Get : member/create             ||
|회원 수정 양식| Get : member/update/{memberId}  ||
|회원 등록| Post : member                   ||
|회원 수정| Post : member/update            ||
|회원 삭제| Post : member/delete/{memberId} ||

### 구조
<img width="949" alt="스크린샷 2023-01-13 오후 9 54 06" src="https://user-images.githubusercontent.com/48250370/212324851-7bf4691d-61a6-408a-96ba-4491b5c92928.png">

### 생각&고민
