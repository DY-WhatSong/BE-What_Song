# BE-What-s_Song
What's Song(왓송) 서비스 백엔드 레포지토리

## 프로젝트 아키텍쳐

![image](https://github.com/DY-WhatSong/BE-What_Song/assets/39437170/7b4ba815-c0b7-4ecd-92e3-8dfc8401af1b)

## ERD

![image](https://github.com/DY-WhatSong/BE-What_Song/assets/39437170/79e35ee2-6c24-449b-b007-02b979209e05)


# 0. 개발 환경, 언어, 도구

- Intellij
- SpringBoot
- Java (11 JDK) or Kotlin
- MariaDB (DB)
- Redis (Cache DB)
- 배포 및 파이프라인 구축 미정
- PostMan (API Test)

# 1. 코딩 컨벤션

## Entity Class

- [x] 필드명은 전부 `camelCase` 로 작성
- [x] Class Name은 `PascalCase` 로 작성

## Package Naming

- `Domain` 별로 패키지를 분리한다.
  - ex.) `회원`, `채팅방`이 존재한다면 `member`,`chat`등의 큰 단위의 Domain으로 분리한다.
- Controller는 `api`,Service는 `application`,Repository는 `repo`로 Naming하여 기능별로 분리
  - Service의 경우 `impl`과 `service`로 나누어 `DIP` 준수
- DTO와 Entity는 별도의 패키지에서 관리하며, DTO내부는 Request와 Response 용도에 따라 패키지를 따로 두어 분리한다.
  - DTO는 `Inner Class` 방식을 사용한다.
  - Naming의 경우 `MemberDTO`의 경우, 회원가입과 정보조회 DTO는 다음과 같이 `SignUp`,`CheckInfo`로 정정한다.
- **ex)**
```markdown
root
 |
 |---member
 |     |--- api
 |     |--- application
 |     |     |
 |     |     |--- service
 |     |     |--- impl
 |     |--- repo
 |     |--- dto
 |     |     |--- request
 |     |     |--- response
 |     |--- entity
 |     |--- config
 |________________

```
  
## File Naming

- YML파일은 `Kebab Case` 로 Naming한다.
- DB 설정 및  프로젝트에 필요한 설정들은 `application.yml` 에 저장하여준다.
- jwt,aws,mail등을 별도의 yml파일을 만들어 관리한다.

## API 요청 URl

- 동사를 사용하지 않는다.
- /api/버전명/Entity Name 을 사용한다.
- ex.) api/v1/member

## DB & CI/CD
- 배포환경 및 DB 연결 방법 미정

# 2. 레파지토리 관리 기법

Organization에서 BackEnd 별도의 레파지토리에서 관리합니다.

### **⚙️** 브랜치 관리 전략

### **⚙️ Git-flow**
![trunked_base_development](https://www.optimizely.com/contentassets/569ac3ee0b124da19a5ac9ea2e8b2b4d/trunk-based-development.png)

**⚙️ 브랜치 구성**
- `feat` 브랜치는 하나의 기능을 개발하기 위한 브랜치입니다. 부모는`develop`이며, 개발이 완료되면`develop`에 merge합니다. 브랜치 이름은 보통`feature/*`이 됩니다.
- `deve` 브랜치는 개발을 위한 브랜치입니다. 여러`feature`들이 merge되는 장소이며, 아직 release되지 않은 기능들이 모여 있게 됩니다.
- `main`브랜치는 실제 운영 중인 서비스의 브랜치입니다.
- `hotfix`브랜치는 서비스에 문제가 발생했을 때 핫픽스에 해당하는 브랜치입니다. 기능 개발(`feature`) 등과 달리 빠르게 대처해야 할 필요가 있기 때문에,`master`브랜치에 직접 merge하는 전략을 취합니다.`dev`과의 차이가 발생하기 때문에, 나중에 차이를 merge할 필요가 있습니다.

## 🛠 Git Branch workflow
![image](https://github.com/DY-WhatSong/BE-What_Song/assets/39437170/70a022bc-ff52-48cd-b8a5-994d6b2b15f6)

- `Squash and Merge` 방법을 차용하여 `Issue`,`Feature` 단위 커밋을 통해 Merge시 진행 사항 구조 손쉽게 파악하는 것을 목적으로한다.
- 각 branch PR시 `이슈화`하여 코드에 대한 피드백 및 토론 후 상대방이 `Merge`

### 브랜치 네이밍

**⚙️ 네이밍 패턴**

`브랜치 종류/이슈번호-간단한 설명`

**Ex)** 이슈번호가 67인 '로그인 기능' 이슈를 구현하는 브랜치를 생성하는 경우, 브랜치 이름을`feature/67-login`로 작성한다.

### 커밋 메시지

**⚙ 메시지 구조**

`Type : 제목 #이슈번호`

`본문`

**Ex)**이슈번호가 67인 이슈의 기능을 구현한 뒤 커밋을 하는 상황이라면 커밋 메시지의 제목을`feat : A기능 구현 #67`으로 작성한다.

**⚙ Type**

- `feat` : 새로운 기능에 대한 커밋
- `fix`	: 버그 수정에 대한 커밋
- `ci/cd` : 배포 커밋
- `docs` : 문서 수정에 대한 커밋
- `style` : 코드 스타일 혹은 포맷 등에 관한 커밋
- `refactor` : 코드 리팩토링에 대한 커밋
- `test` : 테스트 코드 수정에 대한 커밋
- `chore` : 패키지 관련 및 빌드코드 수정
# 3. 라이브러리

- lombok
- JPA
- Spring Security
- JDBC
- Log4j2
- SockJS , STOMP
- JSON ObjectMapper
- JWT
- Aws Secret Manager
- Querydsl
- Swagger
- Oauth2.0
- Spring Rest docs
- RestAPI
- Redis
- Youtube API

# 개발기간

2023 05 02 ~ ing

