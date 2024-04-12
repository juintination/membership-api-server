# Membership API Server
Implementation of Membership API Server with added signup logic by referring to [book(코드로 배우는 React with 스프링 부트 API 서버)](https://www.yes24.com/Product/Goods/123363647) and [Github(구멍가게 코딩단)](https://zk202308a.github.io/reactbookcodes/).
This project uses the [Udacity Nanoderee Style](https://udacity.github.io/git-styleguide/) as a git commit message rule.

## Technical Stack

- Language
  - JAVA 17
- Framework
  - Spring boot 3.2.4
- Security
  - Spring Security
- Database
  - MariaDB
- ORM
  - JPA
- Test
  - JUnit

## About this Study

Spring Security로 로그인 관련 API를 작성한 코드 부분을 참고하여 회원가입 기능을 추가하였습니다.

참고한 코드에는 MemberController가 따로 존재하지 않았으며, MemberService에 register(join)와 같은 내용 또한 따로 작성되지 않았습니다.

마찬가지로 `thymeleaf`를 사용하지 않고 화면 구성 없이 작성하였으며, `Postman`을 이용해서 작성된 코드의 결과를 확인하는 방식으로 구현하였습니다.

추후에 Querydsl을 사용한 회원 검색 기능 구현 및 실제로 연동되는 리액트 애플리케이션 작성 예정입니다.
