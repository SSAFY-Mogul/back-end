# 📗 웹툰 독자를 위한 큐레이팅 & 커뮤니티 서비스

## Project Wiki
😀 자세한 내용은 위키에 정리되어있습니다 !

위키에는 다음과 같은 내용이 있습니다

- 컨벤션
- API 명세
- 기능명세
- 백엔드 개발 규칙
- 회고 

👉 [WIKI](https://github.com/SSAFY-Mogul/back-end/wiki)

## 프로젝트 진행 기간

2024-01-08 ~ 2024-02-16

----

## 프로젝트 기여자

|[신온유]([https://github.com/Spring-Trash/lmw](https://github.com/tlsdhsdb))|[김시은]([https://github.com/Spring-Trash/lmw](https://github.com/marie043))|
|---|---|
|<img style="width:200px" src = "https://avatars.githubusercontent.com/tlsdhsdb"/>|<img style="width:200px" src = "https://avatars.githubusercontent.com/marie043"/>|
|유저,게시판,인프라|웹툰,리뷰,채팅|


## 기획 배경

현재 웹툰 시장에는 많은 웹툰 플랫폼이 존재합니다. 이에 웹툰을 좋아하는 사람들은 플랫폼을 옮겨다니며 봐야한다는 불편함이 있습니다. 또한 특정 웹툰에 대해 소통할 수 있는 커뮤니티 또한 제한적이거나 산재되어 있습니다.
**저희는 이러한 불편함을 개선하기 위해 내가 좋아하는 웹툰을 다른 사람들과 함께 이야기를 나눌 수 있는 공간을 제공하고자 합니다**


### 캐릭터 설명

웹툰을 좋아하는 사람을 떠올렸을 때 이미지가 두더지를 닮았다고 생각했습니다. 두더지는 땅을 파고 생활을 하는데 그것이 마치 자신이 좋아하는 것을 깊이 파는 사람과 닮았기 때문입니다. 그렇게 우리 서비스의 사용자들을 두더지라고 정의 하고 우리 서비스를 두더지들이 사는 두더지 굴로 만들고자 했습니다. 또한 모든 두더지들의 굴이 되고싶은 포부를 담아 ‘모두의 굴’ 줄여서 mogul로 서비스 명을 정하였습니다.

----

## 주요 기능소개

***mogul은 웹툰 큐레이팅 & 커뮤니티 웹 서비스입니다***



**(1) 웹툰 큐레이팅 기능**

- 회원이 작성한 리뷰들을 바탕으로 웹툰을 추천하는 기능입니다.

- Elastic Search를 활용한 추천 기능입니다.

- 웹툰 추천을 위한 데이터는 Flask에서 임베딩 합니다.

- 웹툰 추천을 받으려면 리뷰는 최소 5개를 작성해야 합니다.



**(2) 웹툰 정보 검색 기능**

- Elastic Search를 활용한 맞춘 검색 기능입니다.

- 검색 시 관련 제목, 장르, 내용의 웹툰들을 조회할 수 있습니다.



**(3) 웹툰별 실시간 채팅 기능**

- 웹소켓을 통해 연결된 실시간 채팅 기능입니다.

- 웹툰별로 채팅 기능이 있습니다.

- 입장 시 랜덤 아이디를 부여받아 채팅을 진행합니다.



**(4) 서재 기능**

- 서재 단위로 웹툰들을 모아 놓을 수 있는 기능입니다.

- 플랫폼에 구애받지 않고 좋아하는 웹툰들을 모아 놓을 수 있습니다.


---

## 주요 기술

**Backend**

-  Spring Boot

-  MariaDB

-  ELK STACK

-  Flask

-  Redis

-  Websocket



**Frontend**

-  Next.js

-  React.js

-  TailwindCSS

-  JavaScript ES6↑

  

**CI/CD**

- Docker
- Jenkins
- Nginx



---
## 설계 문서


### ERD
![mogul ERD (3)](https://github.com/SSAFY-Mogul/back-end/assets/42714724/68832a67-80c6-49df-afc2-7aed22b29e33)



### 웹 아키텍쳐

![architecture drawio (4)](https://github.com/SSAFY-Mogul/back-end/assets/42714724/3b8ed31a-d488-42b9-96c9-897dbffa93a7)

----

## 협업 툴

- Git
- Notion
- JIRA
- MatterMost





