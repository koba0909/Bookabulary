# Bookabulary - 나만의 서적

- 영화 개봉 정보 어플 `뭅` 클론 코딩을 목표로 하지만 주제는 책으로 변경

## 🧾 요구사항

- 베스트셀러, 추천도서, 신간도서 리스트를 보여주고 나만의 서적 저장 기능을 제공
- 리스트에 카테고리 필터 기능 제공 ( optional )
- 도서 상세페이지에는 도서명, 이미지,  저자, 설명, 가격 등등 표시

## 🏢 아키텍처

### Clean Architecture (presenter - domain - data 구조)

![Clean Architecture](.image/clean-architecture.png)

### MVI Architecture (Intent, State, Effect, Side Effect 구성)

![mvi](.image/mvi.png)

## 🛠 기술 스택

### Presenter

- ComposeUI - view render
- Compose Navigation - connect screen
- Coil - image load
- Android Jetpack - android framework

### Data

- Kotlin Serialization - json parsing
- Retrofit - nertwork
- Interpark api - book info (베스트셀러, 추천도서, 신간도서, 책검색 제공)
- [http://book.interpark.com/bookPark/html/bookpinion/api_main.html](http://book.interpark.com/bookPark/html/bookpinion/api_main.html)
- Room - DataBase

### DI

- Hilt

### ETC

- Coroutine - asynchronous
- KtLint - code convention
- Git Flow
- Material Design 3
- Multi Module
- Github Action - CI

## 🚀 Multi Module 구성

### App

- MainActivity
- Screen Navigation
- 멀티모듈에서 네비게이션이 가능한지 체크

### Core

- Base Component (ex BaseViewModel, Base MVI Elements 등)
- Extensions

### Widget - 제거

- Custom UI
- Design Resource

### Presenter

- Compose Screen
- ViewModel
- MVI Elements

### Domain

- UseCase
- Model
- Mapper
- Repository Interface

### Data

- Retrofit Service
- Repository Implement
- DTO (Data Transfer Object)
- DAO (Data Access Object)

### DI