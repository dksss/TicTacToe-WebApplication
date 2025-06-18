FROM gradle:8-jdk21 AS build

LABEL maintainer="jodyvole"
LABEL version="1.0"

WORKDIR /home/gradle/project

COPY build.gradle.kts settings.gradle.kts gradlew ./
COPY gradle gradle
COPY src src

RUN ./gradlew clean bootJar -x test --no-daemon

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=build /home/gradle/project/build/libs/*.jar tictactoe.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "tictactoe.jar"]