# iTunesSearch

## 사용기술

- Http client : OkHttp3, Retrofit2
- UI : Jetpack Compose, MVVM, UDF
- 검색결과 Paging : Paging3 Compose
- 이미지 로딩 : Coil
- Favorites 저장 : Jetpack Room Database
- 비동기 프로그래밍 : Coroutines
- Reactive 프로그래밍 : Flow

## 패키지 구조

### data

- remote - iTunes 서버에 트랙 검색 결과를 요청
- local - Room Database에 사용자의 관심 트랙을 저장
- repositories - domain의 repository(search/favorites)를 구현

### domain
- SearchRepository 인터페이스 - 트랙 검색 결과의 한 페이지를 요청하는 인터페이스
- FavoritesRepository 인터페이스 - favorite 목록의 입출력을 담당하는 인터페이스
- SearchPagingManager - SearchViewModel과 SearchRepository, FavoritesRepository의 사이를 매개.
트랙 검색 결과를 favorites과 매핑하여 페이지 단위로 제공

### di

AppModule - HttpClient, ApiService, Database, SearchRepository, FavorietesRepository의 구현체를 싱글톤으로 제공

### ui

- components - 공통으로 사용되는 컴포저블 등
- navigation - Destination 정의 및 NavGraph, NavigationBar 등 컴포저블
- screens
  - search - Search 탭 관련 컴포저블, SearchViewModel
  - favorites - Favorites 탭 관련 컴포저블, FavoritesViewModel
- theme
- ITunesSearchApp.kt - 최상위 컴포저블

## pagination 구현

이전에 Paging Compose 라이브러리를 사용하였을 때, 각 아이템의 상태를 변경하는데 어려움이 있었기 때문에 직접 구현하였다.
요구사항으로는 
- 페이지가 로드될 때 favorites 목록과 매핑되어야 한다.
- 이전에 로드된 페이지 데이터가 유지되어야 한다.
- favorites 목록을 실시간으로 유지하고, 변동이 있을 경우 모든 페이지 데이터에 반영되어야 한다.
- 새로운 term으로 검색이 요청되면, 페이지 데이터 및 페이징 상태가 초기화 되어야 한다./
를 만족하도록 구현하였고, 
추가적으로 특정 페이지에서 에러 발생시 사용자가 Retry 버튼을 통해 해당 페이지를 다시 요청할 수 있도록 하였다.

## 멀티 모듈 변경 시나리오
현재 모듈이 아닌 패키지 단위로만 분리되어 있지만, ui &rarr; domain 과 data &rarr; domain 의 의존 방향을 지켜 구현하였다.
앱이 확장되어 멀티 모듈을 적용하게 된다면 다음의 모듈화 시나리오를 고려할 수 있다.
- app (리소스, navigation, MainActivity, Application)
- core 
  - ui (여러 feature에서 공통적으로 사용하는 컴포넌트)
  - domain (Repository 인터페이스, SearchPagingManager 및 추가적인 UseCase)
  - data (Repository 구현체)
  - network (data/remote)
  - local (data/local)
-feature
  - search
  - favorites

모듈 간의 의존 방향은 다음과 같다.
- app &rarr; feature:search, feature:favorites
- feature:search, feature:favorites &rarr; core:domain, core:ui
- core:data &rarr; core:domain
- core:data &rarr; core:network, core:local

