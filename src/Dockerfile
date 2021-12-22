# syntax=docker/dockerfile:1

FROM registry.access.redhat.com/ubi8/ubi:8.1
RUN yum -y reinstall "*" && yum clean all

FROM openjdk:16-alpine3.13

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline

COPY src ./src

CMD ["./mvnw", "spring-boot:run"]

LABEL org.opencontainers.image.source https://github.com/sundarbabuk/spring-petclinic