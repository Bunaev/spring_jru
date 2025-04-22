# Этап сборки
FROM maven:3.9.6-eclipse-temurin-21-alpine AS builder
VOLUME /tmp
WORKDIR /app
COPY pom.xml .
# Скачиваем зависимости (кешируем этот слой)
RUN mvn dependency:go-offline
COPY src ./src/
# Собираем JAR (пропускаем тесты для ускорения)
RUN mvn package -DskipTests

# Финальный образ
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
# Копируем собранный JAR из этапа builder
COPY --from=builder /app/target/JRU_SpringProject-1.0-SNAPSHOT.jar app.jar
# Открываем порт приложения
EXPOSE 8080
# Команда запуска (с параметрами для лучшей работы в контейнере)
ENTRYPOINT ["java", "-jar", "app.jar"]