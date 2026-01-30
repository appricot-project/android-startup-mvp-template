# StartupHub Project Guide

This file contains code style guidelines and project conventions for working on the StartupHub Android project.

## Project Overview

- **Type**: Android application (Kotlin + Jetpack Compose)
- **Architecture**: Clean Architecture with multi-module setup
- **DI**: Hilt
- **Networking**: Apollo GraphQL + OkHttp
- **Build System**: Gradle with Kotlin DSL
- **Package Structure**: `ru.appricot.startuphub` (app), `ru.appricot.designsystem` (design system)

## Code Style Guidelines

### Import Organization
- Group imports in this order:
  1. Android/AndroidX imports
  2. Kotlin standard library
  3. Third-party libraries (Compose, Hilt, etc.)
  4. Project imports (same package first, then other modules)
- Use alphabetical ordering within groups
- No unused imports

### Formatting Conventions
- **Indentation**: 4 spaces (no tabs)
- **Line length**: Maximum 120 characters
- **Braces**: K&R style - opening brace on same line
- **Semicolons**: Never use semicolons
- **Spacing**: Single space around operators, after commas

### Naming Conventions
- **Classes**: PascalCase (e.g., `HomeViewModel`, `StartupRepository`)
- **Functions/Properties**: camelCase (e.g., `getStartups()`, `startupId`)
- **Constants**: UPPER_SNAKE_CASE (e.g., `DEFAULT_TIMEOUT`)
- **Composable Functions**: PascalCase (e.g., `BasicButton`, `HomeScreen`)
- **Private Properties**: camelCase with underscore prefix if needed (e.g., `_errors`)

### Type Guidelines
- **Explicit Returns**: Always specify return types for public functions
- **Nullable Types**: Use `?` for nullable, prefer non-null when possible
- **Sealed Classes**: Use for state management and restricted hierarchies
- **Data Classes**: Use for models with `equals()`, `hashCode()`, `toString()`
- **Type Aliases**: Use for complex function types or frequently used types

### Architecture Patterns
- **MVVM**: Use ViewModels with StateFlow for UI state
- **Repository Pattern**: Single source of truth for data
- **Dependency Injection**: Use Hilt with constructor injection
- **Module Structure**: Follow feature-based module organization

### Compose Guidelines
- **Parameters**: Order composables as `onClick`, `modifier`, then other params
- **Defaults**: Provide sensible defaults for optional parameters
- **Previews**: Include comprehensive previews for all composables
- **Modifiers**: Chain modifiers, start with required ones
- **State**: Prefer stateless composables, hoist state when needed

### Error Handling
- **Results**: Use `Result<T>` for operations that can fail
- **Exceptions**: Handle with try-catch blocks, prefer specific exception types
- **UI State**: Use sealed classes for different UI states (Loading, Data, Error)
- **Logging**: Use Timber for logging, avoid logging sensitive data

## Module Structure

```
app/                    # Main application module
├── core/
│   ├── navigation/     # Navigation logic
│   ├── designsystem/   # UI components and theme
│   ├── ui/            # Common UI utilities
│   └── network/       # Network configuration
├── feature/
│   ├── home/          # Home feature implementation
│   ├── home_api/      # Home feature API
│   ├── profile/       # Profile feature implementation
│   └── profile_api/   # Profile feature API
└── data/
    ├── schema/        # GraphQL schema
    └── startups/      # Startup data layer
```

## Dependencies Management

- **Version Catalog**: All dependencies managed via `gradle/libs.versions.toml`
- **Convention Plugins**: Build logic in `build-logic/convention/`
- **Module Dependencies**: Use `project(":module:name")` for inter-module dependencies

## Firebase Integration

- **Services**: Analytics, Crashlytics, Performance Monitoring
- **Configuration**: Use `google-services.json` in app module
- **Build Types**: Different configurations for debug/release

## GraphQL Integration

- **Client**: Apollo Kotlin
- **Schema**: Auto-generated from GraphQL schema
- **Queries**: Use generated types for type safety

### GraphQL Commands
```bash
# Generate GraphQL classes from schema
./gradlew :data:schema:generateApolloSources

# Download GraphQL schema
./gradlew :data:schema:downloadApolloSchemaFromIntrospection
```

## Build Configuration

- **Compile SDK**: 36
- **Min SDK**: 26
- **Target SDK**: 36
- **Kotlin Version**: 2.3.0
- **Compose Compiler**: Integrated with Kotlin plugin

## Quality Assurance

- **Lint**: Run `./gradlew lint` before commits
- **Tests**: Ensure all critical paths are tested
- **Code Review**: Follow established patterns and conventions
- **Documentation**: Update relevant documentation when adding features

## Navigation

### Navigation Architecture

The project uses a modular navigation system with the following structure:

- **API Modules**: Define navigation destinations and routes (`*_api` modules)
- **Implementation Modules**: Contain actual screens and navigation logic
- **Core Navigation**: Centralized navigation configuration and utilities

### Adding New Features with Navigation

#### 1. Create API Module
```kotlin
// feature/new_feature_api/src/main/java/ru/appricot/startuphub/newfeatureapi/Destinations.kt
object NewFeature: NavKey()
//In case if navigation route requires login
class NewFeatureSecondScreen(val id: String): ConditionalNavKey {
  override fun requiresLogin(): Boolean = true
}
```

#### 2. Create Implementation Module
- Use the entryProvider DSL
```kotlin
// feature/new_feature/src/main/java/ru/appricot/startuphub/newfeature/NewFeatureModule.kt
@EntryPoint
@InstallIn(ActivityRetainedComponent::class)
class NewFeatureModule {
  @IntoSet
  @Provides
  fun provideEntryProviderInstaller(): EntryProviderInstaller = { navigator ->
    entry<NewFeature> {
      NewFeatureScreen(
        onNextClick = { navigator.navigate(NewFeatureSecondScreen("5")) },
      )
    }
    entry<NewFeatureSecondScreen> {
      NewFeatureSecondScreen(
        onBackClick = { navigator.goBack() }
      )
    }
  }
}
```

#### 3. Add Navigation Dependencies
```kotlin
// feature/new_feature/build.gradle.kts
dependencies {
    implementation(project(":feature:new_feature_api"))
    implementation(project(":core:designsystem"))
}
```

#### 4. Update Project Configuration
```kotlin
// settings.gradle.kts
include(":feature:new_feature_api")
include(":feature:new_feature")

// app/build.gradle.kts
implementation(project(":feature:new_feature_api"))
implementation(project(":feature:new_feature"))
```

### Navigation Best Practices

#### Route Naming Conventions
- Use descriptive, PascalCase names
- Include feature prefix: `FeatureNameScreenName`
- Group related routes: `ProfileMain`, `ProfileEdit`, `ProfileSettings`

#### Parameter Handling
```kotlin
// Define routes with parameters
@Serializable
data class StartupDetails(val id: String) : NavKey

// Navigate with parameters
onDetailsClick = { navigator.navigate(StartupDetails(it)) }

//Handle parameter in viewModel via @AssistedInject and @AssistedFactory
@HiltViewModel(assistedFactory = StartupDetailsViewModel.Factory::class)
class StartupDetailsViewModel @AssistedInject constructor(
  @Assisted val id: String, 
  private val repository: StartupRepository
) : ViewModel() {
    @AssistedFactory
    interface Factory {
     fun create(id: String): StartupDetailsViewModel
    }
  }
```

#### Navigation Module Structure
```
feature/
├── auth_api/
│   └── src/main/java/ru/appricot/startuphub/authapi/
│       └── Destinations.kt
├── auth/
│   └── src/main/java/ru/appricot/startuphub/auth/
│       ├── main/
│       │   ├── AuthChoiceScreen.kt
│       │   ├── SignInScreen.kt
│       │   └── SignUpScreen.kt
│       └── AuthModule.kt
```

### Common Navigation Patterns

#### Feature Entry Points
- Each feature should have a main entry route defined in its API module
- Secondary screens should be nested under the feature route
- Consider deep linking requirements when defining routes

#### Navigation Between Features
- Use destination routes from feature API modules
- Pass data between features using navigation arguments or shared ViewModels

#### Navigation State Management
- Use ViewModels for navigation-related state
- Handle back navigation explicitly when needed
- Consider using Compose Navigation for type-safe navigation

## Clean Architecture Screen Creation Guide

This section describes the complete process of creating a new screen following Clean Architecture principles in the StartupHub project.

### Architecture Layers

#### 1. Data Layer
**DataSource** - Raw data access (network, database, cache)
```kotlin
// data/startups/src/main/java/ru/appricot/startuphub/startups/data/remote/StartupRemoteDataSource.kt
@Singleton
class StartupRemoteDataSource @Inject constructor(
    private val apolloClient: ApolloClient
) {
    suspend fun getStartups(): List<StartupDto> {
       val response = apolloClient.query(GetStartupsQuery()).execute()
       return response.data?.startups?.map { it.toDto() }.orEmpty()
    }
}
```

#### 2. Repository Layer
**Repository** - Single source of truth, data transformation and caching
```kotlin
// data/startups/src/main/java/ru/appricot/startuphub/startups/domain/StartupRepository.kt
@Singleton
class StartupRepository @Inject constructor(
    private val remoteDataSource: StartupRemoteDataSource,
    private val localDataSource: StartupLocalDataSource
) {
    suspend fun getStartups(forceRefresh: Boolean = false): List<StartupDto> {
        if (forceRefresh) {
            val result = remoteDataSource.getStartups()
            result?.let { localDataSource.cacheStartups(it) }
        }
        
        return localDataSource.getStartups()
    }
}
```

#### 3. Domain Layer
**UseCase** - Business logic implementation
```kotlin
// feature/home/src/main/java/ru/appricot/startuphub/home/domain/GetStartupsUseCase.kt
@Singleton
class GetStartupsUseCase @Inject constructor(
    private val repository: StartupRepository
) {
    suspend operator fun invoke(forceRefresh: Boolean = false): Result<List<Startup>> {
        return try {
            val startups = repository.getStartups(forceRefresh).map { it.toDomain() }
            Result.success(startups)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
```

#### 4. Presentation Layer
**ViewModel** - UI state management and business logic coordination
```kotlin
// feature/home/src/main/java/ru/appricot/startuphub/home/presentation/HomeViewModel.kt
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getStartupsUseCase: GetStartupsUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()
    
    init {
        loadStartups()
    }
    
    fun loadStartups(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            _uiState.update { HomeUiState.Loading }
            getStartupsUseCase(forceRefresh)
                .onSuccess { startups ->
                    _uiState.update { HomeUiState.Success(startups) }
                }
                .onFailure { error ->
                    _uiState.update { HomeUiState.Error(error.message.orEmpty()) }
                }
        }
    }
}

sealed class HomeUiState {
    object Loading : HomeUiState()
    data class Success(val startups: List<Startup>) : HomeUiState()
    data class Error(val message: String) : HomeUiState()
}
```

**Composable** - UI implementation
```kotlin
// feature/home/src/main/java/ru/appricot/startuphub/home/presentation/HomeScreen.kt
@Composable
fun HomeScreen(
    onStartupClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    when (val state = uiState) {
        is HomeUiState.Loading -> {
            LoadingIndicator(modifier = modifier)
        }
        is HomeUiState.Success -> {
            StartupList(
                startups = state.startups,
                onStartupClick = onStartupClick,
                onRefresh = { viewModel.loadStartups(forceRefresh = true) },
                modifier = modifier
            )
        }
        is HomeUiState.Error -> {
            ErrorMessage(
                message = state.message,
                onRetry = { viewModel.loadStartups() },
                modifier = modifier
            )
        }
    }
}
```

### Model Mapping

#### Data Transfer Objects (DTOs)
```kotlin
// data/startups/src/main/java/ru/appricot/startuphub/startups/data/dto/StartupDto.kt
data class StartupDto(
    val id: String,
    val name: String,
    val description: String,
    val imageUrl: String?
)

// Extension for GraphQL to DTO conversion
fun GetStartupsQuery.Startup.toDto(): StartupDto = StartupDto(
    id = id,
    name = name,
    description = description,
    imageUrl = imageUrl
)
```

#### Domain Models
```kotlin
// feature/home/src/main/java/ru/appricot/startuphub/home/domain/model/Startup.kt
data class Startup(
    val id: String,
    val name: String,
    val description: String,
    val imageUrl: String?
)

// Extension for DTO to Domain conversion
fun StartupDto.toDomain(): Startup = Startup(
    id = id,
    name = name,
    description = description,
    imageUrl = imageUrl
)
```

### Module Structure for New Screen

```
feature/
├── screen_api/                    # Navigation destinations
│   └── src/main/java/ru/appricot/startuphub/screenapi/
│       └── Destinations.kt
├── screen/                        # Screen implementation
│   └── src/main/java/ru/appricot/startuphub/screen/
│       ├── domain/
│       │   ├── model/
│       │   │   └── ScreenModel.kt
│       │   └── usecase/
│       │       └── GetScreenDataUseCase.kt
│       ├── presentation/
│       │   ├── ScreenViewModel.kt
│       │   └── ScreenComposable.kt
│       └── ScreenModule.kt
└── data/
    └── screen/
        ├── src/main/java/ru/appricot/startuphub/screen/
        │   ├── data/
        │   │   ├── dto/
        │   │   │   └── ScreenDto.kt
        │   │   ├── local/
        │   │   │   └── ScreenLocalDataSource.kt
        │   │   ├── remote/
        │   │   │   └── ScreenRemoteDataSource.kt
        │   │   └── repository/
        │   │       └── ScreenRepository.kt
```

### Key Principles

1. **Separation of Concerns**: Each layer has single responsibility
2. **Dependency Inversion**: High-level modules don't depend on low-level modules
3. **Single Source of Truth**: Repository is the only source for data
4. **Error Handling**: Use Result<T> for operations that can fail
5. **State Management**: ViewModels expose StateFlow for UI state and SharedFlow for errors
6. **Dependency Injection**: Use Hilt for constructor injection
