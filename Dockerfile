# syntax=docker/dockerfile:1

FROM openjdk:16-alpine3.13

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline

COPY src ./src

FROM gcr.io/distroless/java11-debian11
COPY --from=build-env /app /app
WORKDIR /app

CMD ["./mvnw", "spring-boot:run"]

LABEL org.opencontainers.image.source https://github.com/sundarbabuk/spring-petclinic