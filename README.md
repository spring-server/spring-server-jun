# 🍃 Spring 서버 만들기

해당 레포지토리는 [자바 웹 프로그래밍 Next Step](https://m.yes24.com/Goods/Detail/31869154) 및 [해당 레포지토리](https://github.com/next-step)를
참조해 진행합니다.

> html/css는 [해당 레포지토리](https://github.com/Origogi/DreamCoding-FE-Portfolio-Clone)를 참조했습니다.

<br>
<br>
<br>
<br>

## 💻 프로그램 실행

app 모듈 application.yml 파일/설정 추가 후 데이터베이스 설정 값 등록. test 디렉토리에도 추가.

````yaml
 spring:
   datasource:
     driver-class-name: ${DRIVER_CLASS_NAME}
     url: ${URL}
     username: ${USERNAME}
     password: ${PASSWORD}
````

<br>
<br>
<br>
<br>

데이터베이스 스키마 생성. app 모듈의 resource 패키지 참조.

```sql
CREATE TABLE user
(
    id               BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT COMMENT 'PK',
    username         VARCHAR(40)        NOT NULL COMMENT '사용자 이름',
    password         VARCHAR(255)       NOT NULL COMMENT '패스워드',
    created_at       TIMESTAMP          NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
    last_modified_at TIMESTAMP          NULL     DEFAULT NULL COMMENT '최종 수정일',
    deleted          VARCHAR(10)        NOT NULL COMMENT '삭제 유무'
) engine 'InnoDB';
```

<br>
<br>
<br>
<br>

빌드 후 프로그램을 실행합니다. 

```shell
./gradlew build
./gradlew bootJar
```

<br>
<br>
<br>
<br>

## 📝 공통 요구사항

1. 전체 미션은 4단계로 나뉘어져 있으며, 각 Step에는 `필수/선택 구현 사항`, `학습 목표`가 주어집니다.
    - 다음 단계로 넘어가기 위해서는 최소 1명 이상의 Approve가 필요합니다.
    - 필수 구현사항은 반드시 구현해야 하며, 선택 구현 사항은 구현하지 않아도 됩니다.
        - 선택 구현사항에는 [선택] 이 명시 돼 있으며, 없다면 필수 구현사항입니다.
        - 현재 진행중인 Step이 완료되지 않으면, 다음 단계로 넘어갈 수 없습니다.

2. 각 Step이 끝나면 학습한 내용을 블로그에 정리하고 Jira에 링크를 공유합니다.
3. 프로젝트에는 최소한의 의존성만 주입하며, ORM은 사용하지 않습니다.
4. 매 주 주말에 자신이 구현한 내용과 학습한 지식에 대해 다른 사람들과 공유하고 의도를 설명합니다.
5. 각자 진행하는 스텝이 달라도 상관없습니다. 자신이 할 수 있는 만큼 구현하고 제출합니다.

<br>
<br>
<br>
<br>

## [Step1] 사용자 정보를 저장한다.

네트워크로 부터 전송된 데이터를 파싱해 사용자 정보를 저장한다.

- [x] 정적 페이지를 화면에 띄운다.
- [x] 프론트 컨트롤러 패턴을 적용한다. 
- [x] 데이터베이스는 애플리케이션 내부 인메모리 데이터베이스를 사용한다.
- [x] 어떤 정보를 저장할 지는 자유롭게 정의한다.

<br>

### 학습 목표
1. 네트워크로 부터 애플리케이션까지 데이터가 어떻게 전송되는지 학습한다.
2. DispatcherServlet, 프론트 컨트롤러 패턴의 개념과 동작 원리를 학습한다.

<br>
<br>
<br>
<br>

## [Step2] 로그인 기능을 구현한다.

사용자 정보를 바탕으로 로그인 기능을 구현한다.

- [x] 로그인 기능을 구현한다.
  - 세션을 이용해 구현한다.
  - 세션은 애플리케이션 내부에 저장/관리한다.
  - 세션 유지 시간을 제한 한다.
  - [선택] 최근 로그인 기록과 아이피를 식별할 수 있도록 한다.

- [x] 개인 정보 상세 조회 기능을 개발한다.

<br>

### 학습 목표

1. HTTP 특징에 대해 학습한다.
   - 쿠키/세션에 대해 학습한다.

2. 세션 관리 방법에 대해 학습한다.

<br>
<br>
<br>
<br>

## [Step3] 데이터베이스를 교체한다.

애플리케이션 내부에 저장하던 데이터를 외부 데이터베이스에 저장한다.

1. 데이터베이스 종류는 자유롭게 선택 한다.
    - RDB, Redis 등
2. JDBC 템플릿을 구현한다.

<br>

### 학습 목표

1. 추상화에 대해 학습한다.
2. 데이터베이스 통신 과정에 대해 이해한다.
3. 각 데이터베이스의 특징에 대해 이해한다.
4. 트랜잭션에 대해 학습한다.

<br>
<br>
<br>
<br>

## [Step4] 데이터 전송 방식을 일부 변경한다.

매 번 정적 리소스를 가져오는 것은 비효율적이기 때문에, 한 페이지에서 일부 데이터만 변경할 수 있도록, 데이터 전송 방식을 변경한다.

1. 모든 API에 적용할 필요 없으며, 개인정보 수정만 적용한다.
2. [선택] 코드를 리팩토링한다.

<br>

### 학습 목표

1. 각 데이터 전송 방식에 대해 학습한다.
2. 정적 리소스를 가져오는 비용을 최적화 하는 방법에 대해 학습한다.
