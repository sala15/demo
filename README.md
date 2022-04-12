### 실행방법
1. postgresql 설치 후 application.yml에 접속정보를 수정(DB 및 서버 포트 등)
2. 데이터베이스에 resources/ddl 폴더의 sign_up.sql, user.sql로 DB생성
3. gradle bootJar(gradle 실행)로 서버 실행
4. http://localhost:8080 에 접속하여 사이트에 접속
### 사용 기술
SpringBoot 2.6.6
SpringSecurity
ThymeLeaf
JPA
PostgreSql

### 구현 스펙
1. 회원 가입 기능 
- /signUp/sendAuthCode(인증번호 발송)
> Input
>> signupSessionId(String)
>> phoneNumber(String)
- /signUp/verifyAuthCode(인증번호 확인)
> Input
>> signupSessionId(String)
>> authCode(String)
- /signUp/signUp(회원가입)
> Input
>> email(String)
>> pass(String)
>> name(String)
>> nickname(String)
>> phoneNumber(String)
>> authCode(String)
>> signupSessionId(String)

2. 로그인 기능
- /signIn/authorize(로그인)
> Input
>> username(String)
>> password(String)

3. 내 정보 보기 기능
 - /member/personalInfo(내 정보 확인)
> Input
>> Nothing
> Output
>> email(String)
>> nickname(String)
>> name(String)
>> phoneNumber(String)

4. 비밀번호 찾기 (재설정) 기능
 - /signIn/changePassword/sendAuthCode(인증번호 발송)
> Input
>> signupSessionId(String)
>> phoneNumber(String)
 - /signIn/changePassword/verifyAuthCode(인증번호 확인)
> Input
>> signupSessionId(String)
>> authCode(String)
 - /signIn/changePassword(비밀번호 재설정)
> Input
>> email(String)
>> authCode(String)
>> password(String)
>> signupSessionId(String)
    
### 최종 구현 된 범위
1. 회원 가입 기능
- /signUp/sendAuthCode(인증번호 발송)
- /signUp/verifyAuthCode(인증번호 확인)
- /signUp/signUp(회원가입)
2. 로그인 기능
- /signIn/authorize(로그인)
3. 내 정보 보기 기능
 - /member/personalInfo(내 정보 확인)
4. 비밀번호 찾기 (재설정) 기능
 - /signIn/changePassword/sendAuthCode(인증번호 발송)
 - /signIn/changePassword/verifyAuthCode(인증번호 확인)
 - /signIn/changePassword(비밀번호 재설정)

### 특별히 신경 쓴 부분
 - Spring Security를 활용하여 확장성이 용이하도록 개발하였습니다.
 - CustomControllerAdvice를 작성하여 오류 발생시 응답값을 관리하였습니다.
 - Exception Handling을 하여 특정 Exception에 해당하는 Error Code를 정의 하였습니다.
 - 회원가입/비밀번호 변경시 별도 세션ID(화면에서 생성)를 사용하여 관리될 수 있도록 하였습니다.