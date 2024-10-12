### ‘NewsFeed’ 앱?

- [https://newsapi.org/](https://newsapi.org/) 의 ‘TopHeadline’ API 를 사용하여 간단한 뉴스 목록 제공
- 클릭 시 웹뷰를 통해 뉴스 상세 페이지 노출
- **Paging3-RemoreMediator**와 **Room**을 사용하여 **로컬 캐싱** 기능 구현
    - **이미지 캐싱은 Glide 기본 캐싱** (`DiskCacheStrategy.AUTOMATIC`)을 사용

### 사용 언어

- Kotlin

### 사용 기술

- Clean Architecture, MVI
- Hilt, Coroutine, Room, Retrofit, Moshi, Glide, Paging3
