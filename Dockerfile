# Use OpenJDK 17 as the base image
FROM openjdk:17-jdk-alpine
MAINTAINER ralu
COPY target/porfolio-0.0.1-SNAPSHOT.jar porfolio.jar
ENTRYPOINT ["java","-jar","/porfolio.jar"]