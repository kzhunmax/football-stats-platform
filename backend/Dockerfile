# Build stage
FROM gradle:8.14.3-jdk21-alpine AS builder

WORKDIR /app
COPY . .

RUN ./gradlew clean build -x test

# Final stage
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=builder /app/build/libs/app.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]