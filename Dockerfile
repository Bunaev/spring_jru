
FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src/
RUN mvn clean package -DskipTests

# Этап запуска
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
COPY wait-for.sh /wait-for.sh
RUN chmod +x /wait-for.sh
COPY src/main/resources/db ./liquibase/
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]