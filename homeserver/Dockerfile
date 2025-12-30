FROM gradle:8-jdk21-corretto AS build
WORKDIR /app
COPY . .
RUN gradle build -x test
FROM amazoncorretto:21 AS exec
LABEL authors="Pinta"
WORKDIR /app
COPY --from=build /app/build/libs/*.jar /app
ENTRYPOINT ["java", "-jar", "homeserver-0.1.0.jar"]