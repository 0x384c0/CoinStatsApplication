# CoinStats App
A sample android app that shows 20 most popular crypto currencies and able to search by keywords al local database

### Limitations
- public [CoinStats api](https://documenter.getpostman.com/view/5734027/RzZ6Hzr3) does not support search by keywords, only [private api](https://api.coin-stats.com/v4/coins?&limit=5&keyword=doge) does, but it is obfuscated
- so in this app it possible to search within cached data only (20 most popular crypto currencies)

### Implemented by Clean Architecture
The following diagram shows the structure of this project with 3 layers:
- [Presentation](/app/src/main/java/com/coinstats/app/presentation)
  - [CoinsActivity](app/src/main/java/com/coinstats/app/presentation/coins/CoinsActivity.kt)
- [Domain](/app/src/main/java/com/coinstats/app/domain)
  - [GetCoinsUseCase](app/src/main/java/com/coinstats/app/domain/usecase/GetCoinsUseCase.kt) 
- [Data](/app/src/main/java/com/coinstats/app/data)
  - [CoinsRepositoryImpl](app/src/main/java/com/coinstats/app/data/repository/CoinsRepositoryImpl.kt) 
  
### Communication between layers

1. UI calls method from ViewModel.
1. ViewModel executes Use case.
1. Use case obtains data from Repository
1. Repository returns data from a Data Source (Cached or Remote).
1. Information flows back to the UI where we display the list of posts.


### Tests
- there is [GetCoinsUseCaseTests](app/src/androidTest/java/com/coinstats/app/GetCoinsUseCaseTests.kt) created to cover domain
- network and database modules is [mocked](app/src/androidTest/java/com/coinstats/app/mock/) with Hilt