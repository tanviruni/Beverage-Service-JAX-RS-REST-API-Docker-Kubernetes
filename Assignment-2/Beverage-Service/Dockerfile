FROM openjdk:jre-alpine
FROM gradle:4.3.1-jdk8-alpine

WORKDIR /opt
ADD --chown=gradle:gradle build /opt/Beverage-Service
ADD --chown=gradle:gradle src /opt/Beverage-Service/src
ADD --chown=gradle:gradle build.gradle /opt/Beverage-Service
ADD --chown=gradle:gradle gradlew /opt/Beverage-Service



EXPOSE 8080
WORKDIR /OPT/Beverage-Service
ENTRYPOINT gradle build && java -jar build/libs/Assignment-2-all.jar
