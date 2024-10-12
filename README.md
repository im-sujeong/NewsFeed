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


### 화면 구성
#### 스타일
![뉴스앱_스타일](https://github.com/user-attachments/assets/f806baba-c00a-4fc2-b669-a1f40cbf61bf)
#### 라이트 테마
![뉴스앱_라이트](https://github.com/user-attachments/assets/da365bb0-b228-4e0f-85ab-11c96b32c6a0)
#### 다크 테마
![뉴스앱_다크](https://github.com/user-attachments/assets/c93fedcd-9288-4aca-af72-8460944f6778)
