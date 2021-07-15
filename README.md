# CoinStats App
A sample android app that shows popular crypto currencies and able to search by keywords within local database

![Demo](/media/ffmpeg_out.gif)

### Build Requrements
- Android Studio 4.2.2

### Limitations
- public [CoinStats api](https://documenter.getpostman.com/view/5734027/RzZ6Hzr3) does not support search by keywords, only [private api](https://api.coin-stats.com/v4/coins?&limit=5&keyword=doge) does, but it is obfuscated
- so in this app it possible to search within cached data only

### Configuration
- API url can be set in [core/build.gradle](/core/build.gradle)
- there is two build variants, "debug" and "release", that can be configured independently

### Modules
App consist of 3 modules
- [app](/app) - constains crypto currencies list feature
- [core](/core) - contains specific for this project code, like Retrofit setup
- [common](/common) - contains utilites and helpers, that can be used in eny project

### Implemented by Clean Architecture
The following diagram shows the structure of this project with 3 layers:

- [Presentation](/app/src/main/java/com/coinstats/app/presentation)
  - [CoinsActivity](/app/src/main/java/com/coinstats/app/presentation/coins/CoinsActivity.kt)
- [Domain](/app/src/main/java/com/coinstats/app/domain)
  - [GetCoinsUseCase](/app/src/main/java/com/coinstats/app/domain/usecase/GetCoinsUseCase.kt) 
- [Data](/app/src/main/java/com/coinstats/app/data)
  - [CoinsRepositoryImpl](/app/src/main/java/com/coinstats/app/data/repository/CoinsRepositoryImpl.kt) 

![Diagramm](/media/diagram_figma_out.jpg)
  
### Communication between layers

1. UI calls method from ViewModel.
1. ViewModel executes Use case.
1. Use case obtains data from Repository
1. Repository returns data from a Data Source.
1. Information flows back to the UI where we display the list of coins.

### Data Source

1. [Android Paging Library](https://developer.android.com/jetpack/androidx/releases/paging#paging-*-3.0.0) is used for paging and caching
1. Local Database is a single source of truth
1. [RemoteMediator](/app/src/main/java/com/coinstats/app/data/repository/paging/PageKeyedRemoteMediator.kt) loads data to Database, when app has run out of cached data
1. Database will be invalidated, when user refreshes the list
1. search by keywords occur only within PagingSource
1. PagingSource is implemented with Room


### Frameworks

1. [Paging library 3](https://developer.android.com/jetpack/androidx/releases/paging#paging-*-3.0.0)
1. [AndroidX](https://developer.android.com/jetpack/androidx)
1. [Hilt](https://dagger.dev/hilt/)
1. [RxJava](https://github.com/ReactiveX/RxJava)
1. [Retrofit](https://square.github.io/retrofit/)

### Tests
- for testing is used [recommended](https://developer.android.com/training/data-storage/room/testing-db) approact to write a JUnit test that runs on an Android device, so Adnroid Studio is requred for runnning tests
- there is [GetCoinsUseCaseTests](/app/src/androidTest/java/com/coinstats/app/GetCoinsUseCaseTests.kt) created to cover domain logick
- network and database modules are [mocked](/app/src/androidTest/java/com/coinstats/app/mock/) with Hilt

### TODO
- creat CI pipeline for building and uploading release to Github
  - use github actions and fastlane
  - use [git-crypt](https://github.com/AGWA/git-crypt) for keeping signing data
- 100% test and documetaion coverage
- split presentaion utils into reusable sub-modules, connected with Hilt