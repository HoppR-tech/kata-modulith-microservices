FROM maven:3.9.9-eclipse-temurin-21-alpine as DEPENDENCIES
COPY pom.xml .
RUN mvn dependency:go-offline

FROM maven:3.9.9-eclipse-temurin-21-alpine as build
ARG SERVICE_PATH
WORKDIR /build
COPY --from=DEPENDENCIES /root/.m2 /root/.m2
COPY pom.xml .
COPY shared shared
WORKDIR /build/app
COPY ${SERVICE_PATH}/pom.xml .
RUN mvn dependency:go-offline
COPY ${SERVICE_PATH}/. .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /build/app/target/*.jar ./app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
