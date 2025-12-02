# Link Shortener Service

REST-сервис для сокращения ссылок. Принимает длинные URL и возвращает короткие коды с автоматическим редиректом на оригинальный адрес.

## Возможности

**Базовое решение:**
- REST API для создания коротких ссылок
- Редирект по короткой ссылке на оригинальный URL
- Инструкция по сборке и запуску

**Продвинутое решение:**
- Docker и docker-compose для запуска
- Логирование через SLF4J
- Конфигурируемая длина коротких кодов
- Персистентность данных (переживает рестарт)

## Технологии

- Java 21, Spring Boot 4.0.0
- Spring Data JPA, PostrgeSQL
- Maven, Docker & Docker Compose
- Swagger/OpenAPI
- Lombok, SLF4J

## Быстрый старт

### Сборка и запуск

```bash
docker-compose up --build
```

Сервис доступен: **http://localhost:8080**

Swagger UI: **http://localhost:8080/swagger-ui/index.html**

### Остановка

```bash
docker-compose down
```

## API Эндпоинты

### Создание короткой ссылки

**POST** `/api/v1/link`

```bash
curl -X POST http://localhost:8080/api/v1/link \
  -H "Content-Type: application/json" \
  -d '{"link": "https://example.com/very/long/url"}'
```

**Ответ (201):** `abc123XyZ456`

### Редирект по короткой ссылке

**GET** `/api/v1/{shortCode}`

```bash
curl -L http://localhost:8080/api/v1/abc123XyZ456
```

**Ответ:** HTTP 302 редирект на оригинальный URL

## Тест-кейсы

### 1. Создание короткой ссылки

```bash
curl -X POST http://localhost:8080/api/v1/link \
  -H "Content-Type: application/json" \
  -d '{"link": "https://github.com/spring-projects/spring-boot"}'
```

**Ожидается:**
- Статус: `201 Created`
- Тело: короткий код (например, `aB3xY9zK2mN5pQ7r`)

---

### 2. Редирект по короткой ссылке

```bash
curl -i http://localhost:8080/api/v1/aB3xY9zK2mN5pQ7r
```

**Ожидается:**
- Статус: `302 Found`
- Заголовок: `Location: https://github.com/spring-projects/spring-boot`

---

### 3. Несуществующая ссылка

```bash
curl -i http://localhost:8080/api/v1/nonexistent123
```

**Ожидается:**
- Статус: `404 Not Found`

## Возможные улучшения
- Сейчас длина короткой ссылки `SHORTEN_LINK_LENGTH` настраивается в `LinkService`, по-хорошему вынести это на уровень повыше
- Все секреты, переменные для запуска и тот же `SHORTEN_LINK_LENGTH` вынести отдельно в .env
- Сейчас генерация коротких ссылок достаточно примитивна, думаю этот момент тоже можно улучшить
