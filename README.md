<p align="center">
  <img src= "https://user-images.githubusercontent.com/32264819/144703687-1d8efee2-104d-47b3-8d9f-340d7c233740.gif" alt="Logo width="200" height="200">
</p>
<h2 align="center">👶Kin - In 🧒<br><br> 똑똑한 유치원/어린이집 관리 앱, 키드인 </h2>                                                                                                                                         

                                                                                                                                         
###  📑 프로젝트 소개

#### Team Name :  ToolC
##### Project Period : 21.10 ~ 21.11

### 🙋‍♀️ 팀원 소개


|이름|<h3> 박지수 </h3> |<h3> 오승재 </h3> |
|:-----:|:------:|:------:|
| |<img src="https://avatars.githubusercontent.com/u/32264819?v=4" width="200" height="200">| <img src="https://avatars.githubusercontent.com/u/33858991?v=4" alt="profile" width="200" height="200">|
|GitHub|[JisooPark27](https://github.com/JisooPark27)|[Seungjae](https://github.com/oh980225)|

---

### 🐟 아키텍쳐
![image](https://user-images.githubusercontent.com/32264819/144704752-a45b41eb-cb91-4c5f-a44c-8f2d1ff7b9bd.png)

### 🐟 Stack
- Backend
  - Java, JavaScript
  - Spring Boot, Express
  - Jpa
- DevOps
  - AWS EC2
  - PM2, Nginx
  - AWS RDS, AWS S3, MySQL
  - Expo Notification
  - Pubnub
- Tools
  - GitHub
  - Notion, Slack
  - Figma

### 🐟 패키지 구조
```javaScript
-authentication
  -config
  -exception
  -filter
  -handler
  -time
-config
  -s3
-controller
  -member
-domain
  -connection
  -group
  -member
  -message
-exception
-fcm
-mapper
-repository
  -interface
  -springdatajpa
-service
  -fcm
  -member
-util
-vo
```

---
### 🔖서비스 핵심 기능
#### 🌏 원장 등 각 사용자 별 회원가입
   - 원장 회원가입 화면
   - 가입 시 유치원 정보 입력
   - 관리자 승인 시 유치원 개설

![image](https://user-images.githubusercontent.com/32264819/144704397-b8120ca2-da8d-4777-9536-8bf17aeab560.png)

#### 🌏 유치원 공지, 담임 선생님과의 1대1 메신저 등 유치원 관리 기능
   - 유치원 공지 게시판
   - 담임 선생님, 학부모와 1대1 메신저 연결

![image](https://user-images.githubusercontent.com/32264819/144704544-7abaabd6-d59b-426a-baf4-b5d4f0eed908.png)

#### 🌏 셔틀 버스 위치 제공, 유치원내 전화번호부 등 관리, 편의 기능
   - 아이가 오고 있는 셔틀 버스 위치 확인 가능
   - 원내 전화번호부 관리
   - 학부모의 아이 반 등록 요청
 
 ![image](https://user-images.githubusercontent.com/32264819/144704471-3fd26f04-fd82-4365-b954-e45ce4ced562.png)

