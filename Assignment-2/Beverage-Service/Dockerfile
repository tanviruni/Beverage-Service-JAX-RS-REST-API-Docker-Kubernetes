FROM openjdk:jre-alpine
FROM gradle:5.4.1-jdk8-alpine
WORKDIR /opt
ADD --chown=gradle:gradle build /opt/Beverage-Service
ADD --chown=gradle:gradle src /opt/Beverage-Service/src
ADD --chown=gradle:gradle build.gradle /opt/Beverage-Service
ADD --chown=gradle:gradle gradlew /opt/Beverage-Service
EXPOSE 8080
RUN chown -R gradle:gradle /opt/Beverage-Service
RUN chmod 755 /opt/Beverage-Service
WORKDIR /opt/Beverage-Service
USER gradle


ENTRYPOINT java -jar /opt/Beverage-Service/libs/Beverage-Service-all.jar



