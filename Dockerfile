FROM maven:3.9.9-eclipse-temurin-21-alpine as dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

FROM maven:3.9.9-eclipse-temurin-21-alpine as build
ARG SERVICE_PATH
WORKDIR /build
COPY --from=dependencies /root/.m2 /root/.m2
COPY pom.xml .
COPY shared shared
WORKDIR /build/app
COPY ${SERVICE_PATH}/pom.xml .
RUN mvn dependency:go-offline
COPY ${SERVICE_PATH}/. .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
RUN apk add curl
WORKDIR /app
COPY --from=build /build/app/target/*.jar ./app.jar
RUN curl -Lo otel-java-agent.jar https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/latest/download/opentelemetry-javaagent.jar
EXPOSE 8080
ENTRYPOINT ["java", "-javaagent:otel-java-agent.jar", "-jar", "/app/app.jar"]
