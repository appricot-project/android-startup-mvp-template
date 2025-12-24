# Apollo GraphQL Setup

Этот модуль содержит настройку Apollo GraphQL для работы с GraphQL API.

## Структура

- `src/main/graphql/` - GraphQL файлы (схемы и запросы)
  - `schema.graphqls` - GraphQL схема API
  - `*.graphql` - GraphQL запросы и мутации

## Настройка

1. **Обновите GraphQL endpoint** в `GraphQLModule.kt`:
   ```kotlin
   val graphqlUrl = "https://startuphub.appricot.ru/graphql"
   ```

2. **Обновите схему** в `src/main/graphql/schema.graphqls` согласно вашему API

3. **Создайте GraphQL запросы** в формате `.graphql` файлов

## Использование

Apollo автоматически генерирует Kotlin код из GraphQL файлов при сборке проекта.

Пример использования в репозитории:
```kotlin
@Inject constructor(
    private val apolloClient: ApolloClient
) {
    suspend fun getStartups() {
        val response = apolloClient.query(GetStartupsQuery()).execute()
        // обработка ответа
    }
}
```

## Генерация кода

Код генерируется автоматически при сборке. Сгенерированные классы находятся в пакете:
`ru.appricot.startuphub.startups.graphql`



