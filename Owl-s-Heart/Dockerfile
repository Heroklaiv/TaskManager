FROM maven:3-eclipse-temurin-21 AS build
WORKDIR /build
COPY pom.xml /build
RUN mvn -B dependency:resolve dependency:resolve-plugins
COPY src /build/src
RUN mvn package -DskipTests

FROM openjdk:21-slim
COPY --from=build /build/target/*.jar /app/app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]