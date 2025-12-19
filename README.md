This is a Yet Another KMP Product List project, targeting Android, iOS. Both Android & iOS apps can be launched.

## Architecture & Tech Stack

This project follows **Clean Architecture** principles and utilizes **Kotlin Multiplatform (KMP)** to share business logic, data handling, and UI across Android and iOS.

### Key Technologies
*   **Kotlin Multiplatform**: Core technology for code sharing.
*   **Jetpack Compose Multiplatform**: Declarative UI framework shared between Android and iOS.
*   **Koin**: Dependency Injection framework.
*   **Ktor**: Asynchronous HTTP client for networking.
*   **Room**: Local database for persisting data (e.g., Favorites).
*   **Coroutines & Flow**: Asynchronous programming and reactive state management.
*   **Mokkery**: Mocking library for unit testing.

### Module Structure
The project is organized into the following layers within `commonMain`:
*   **Presentation** (`ui` package): Contains ViewModels and Composable screens (`ProductListScreen`, `ProductDetailScreen`, `FavoritesScreen`).
*   **Domain** (`domain` and `model` packages): Contains pure business logic, Use Cases (`GetProductsUseCase`, `SetFavoriteUseCase`), and Repository interfaces.
*   **Data** (`db` and `network` packages): Contains Repository implementations, Data Sources (API, Database), and Entity mappings.

### Build and Run Android Application

To build and run the development version of the Android app, use the run configuration from the run widget
in your IDE’s toolbar or build it directly from the terminal:
- on macOS/Linux
  ```shell
  ./gradlew :composeApp:assembleDebug
  ```
- on Windows
  ```shell
  .\gradlew.bat :composeApp:assembleDebug
  ```

### Build and Run iOS Application

To build and run the development version of the iOS app, use the run configuration from the run widget
in your IDE’s toolbar or open the [/iosApp](./iosApp) directory in Xcode and run it from there.

### Test Coverage

This project uses **Kover** to generate test coverage reports.

To run the tests and generate the report, execute:

```shell
./gradlew test -Pkover koverHtmlReport
```

The report will be available at: `build/reports/kover/html/index.html`.

## Further improvements

Obviously, the UI should be improved by:
  - displaying Product images via e.g. Coil;
  - displaying more Product details on, well, Product Details screen;
  - etc

This simple test app sets the foundation upon which further improvements can be made. 
