FROM openjdk:11.0.12-jre-buster AS build
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
COPY cred.json cred.json
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]