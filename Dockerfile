FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17-alpine

MAINTAINER ekenevics@gmail.com

COPY --from=build target/service-url_shortener-0.0.1-SNAPSHOT.jar url-shortener.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","url-shortener.jar"]
