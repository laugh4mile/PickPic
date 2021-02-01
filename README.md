# Welcome to PickPic

> 픽픽 (PickPic) 웹사이트 

<br>

## 팀원소개

*팀장*
<br>
**👦김영록**


*팀원*
<br>
**👦곽지원**

**👩윤현수**

**👦이석원**

**👦주정훈**

<br>

## ✨ 프로젝트 개요

- **진행 기간**: 2021.01.11 ~ 2021.01.29
- **목표**
  - 연인에게 사진 못 찍는다고 구받 받는 사람,
  - 프로필용 사진을 찍었는데 어떤게 제일 나은지 모르는 사람,
  - 찍은 사진을 다른 사람들과 공유하고 싶은 사람
  - :star: 이 모든 사람들을 위한 웹사이트 만들기

<br>

## 🔧 Tech Stack

![image](/uploads/fddbf3c3d4e179810be2e2653dfe4364/image.png)

<details>
    <summary>Front 자세히 살펴보기</summary>
    <ul>
        <li>Vue: 2.6.11</li>
        <li>Vue CLI: 4.3.1</li>
        <li>Vuex: 3.2.0</li>
        <li>Vue-router: 3.1.6</li>
        <li>Vuetify: 2.2.23</li>
        <li>axios: 0.21.1</li>
        <li>vue-infinite-loading: 2.4.5</li>
        <li>bootstrap: 4.6.0</li>
        <li>bootstrap-vue: 2.21.2</li>
    </ul>
</details>

<details>
    <summary>Back 자세히 살펴보기</summary>
    <ul>
        <li>Swagger: 2.9.2</li>
        <li>MyBatis: 2.1.3</li>
        <li>Spring Boot</li>
        <li>spring-boot-starter-mail</li>
        <li>commons-lang3</li>
        <li>commons-io</li>
        <li>commons-fileupload</li>
        <li>MySQL: 8.0.22</li>
    </ul>
</details>

<br>

## ERD

![image](/uploads/8fd0584fe8460f72bd6a09b27271a737/image.png)

<br>

## WireFrame

![image](/uploads/291e2834350c9e0ec1f091a22b50687e/image.png)

## ⭐️ 구현 항목

### 회원가입

### 이메일 인증

### 로그인

### 로그아웃

### 회원정보 조회

### 프로필 수정

### 회원 탈퇴

### 404페이지

### 에러페이지

### 투표게시판

### 게시글 보기, 작성, 삭제, 수정

### 게시글 임시저장

### 게시글 댓글 리스트, 작성, 삭제, 수정

<br>

## 상세 기능 소개

#### 회원가입

* 입력폼의 유효성 검사 추가
![회원가입_유효성_검사](/uploads/a0babc98a7b26798a3eccdebe4580e9c/회원가입_유효성_검사.PNG)
  * 이름은 10글자 이하로 제한
  * 이메일은 (example@email.com) 의 형식으로 제한
  * 비밀번호는 8글자 이상, 문자 와 숫자 1개씩 이상 포함
  * 비밀번호 확인은 비밀번호와 일치 여부 조사
  * 동의 여부를 checkbox로 마지막 체크

* 이메일 인증을 구현
  ![이메일_인증_alert](/uploads/1028a72628d7411ed761d32245ed05d5/이메일_인증_alert.PNG)

  * 작성된 이메일로 인증 코드를 전송
  
  ![인증코드_확인](/uploads/33256b2a3e614ebb3300f0d2a5cbfb92/인증코드_확인.PNG)

  * 인증코드가 정상적으로 전송된 모습
  
  ![이메일_인증_완료](/uploads/e657f92b128ff8a4ac6a254455b56f0f/이메일_인증_완료.PNG)

  * 해당 인증코드로 인증을 완료

#### 이메일 인증

* SMTP를 사용 구글 이메일을 통해 인증번호를 포함한 Email 전달

![이메일_인증_코드](/uploads/cd513369eaad8d0769d1744bff907975/이메일_인증_코드.PNG)

* 인증코드를 첨부하여 메일 전송

#### 로그인

* 로그인 모달창 구현

![image](/uploads/9a2ac40817b95ed3e32717eb4fa31fdb/image.png)

* 로그인 후 메인페이지 변화

![image](/uploads/8e7242b485ad8e4ecf587ea73c84e2b4/image.png)

* 로그인, 회원가입이 사라지고 로그아웃, 마이페이지 버튼이 생김

#### 로그아웃

* 로그아웃 버튼을 누르면 다시 회원가입, 로그인 버튼 생김, 로그인 토큰을 없앰

#### 회원정보 조회

* 서버에서 회원의 정보를 조회
![image](/uploads/e03b3fdc789cb8ee9ae7b40bf459f906/image.png)

* MyPage에서 회원 탈퇴 및 수정, 프로필이미지 설정 가능

#### 회원 탈퇴

* 탈퇴 전 모달창으로 다시 한번 확인

![회원탈퇴](/uploads/65b6a116c3f2590bdbc2e323e102cd5f/회원탈퇴.PNG)

* 해당 메세지를 동일하게 입력 시 탈퇴 진행

#### 404 페이지

```
    {
      path:'/404',
      name: 'notFound',
      component: notFound,
    },
    {
      path: '*',
      redirect: "/404"
  },
```
* 없는 페이지에 도달하면 모두 /404 페이지로 전환

![404페이지](/uploads/4e5c2ce433087891092ee3ec27e6be10/404페이지.PNG) 

#### 에러 페이지

* 에러가 발생하면 /Error 페이지로 이동

![에러페이지](/uploads/f2cdcc7b7341331138eb89432a0a964a/에러페이지.PNG)


### 투표게시판
![image](/uploads/e919770222e1093acb672ecb22799522/image.png)

### 게시글 보기, 작성, 삭제, 수정

* 게시글 보기

![image](/uploads/ea63cdecd988c57670161c81f312783c/image.png)

* 게시글 수정

![image](/uploads/57d9406d4b3b1b0816f335b93675d2bd/image.png)

### 게시글 임시저장

* 작성하던 게시글을 임시저장하면 리스트에서 확인 가능

![image](/uploads/adb2b63668c08bfa32aa5cb09ff53013/image.png)

### 게시글 댓글 리스트, 작성, 삭제, 수정
![image](/uploads/4172a5c673df3e2cd8e070318b8161de/image.png)
