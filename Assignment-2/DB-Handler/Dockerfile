FROM openjdk:jre-alpine
FROM gradle:5.4.1-jdk8-alpine
WORKDIR /opt
ADD --chown=gradle:gradle build /opt/DB-Handler
ADD --chown=gradle:gradle src /opt/DB-Handler/src
ADD --chown=gradle:gradle build.gradle /opt/DB-Handler
ADD --chown=gradle:gradle gradlew /opt/DB-Handler
EXPOSE 9999
RUN chown -R gradle:gradle /opt/DB-Handler
RUN chmod 755 /opt/DB-Handler
WORKDIR /opt/DB-Handler
USER gradle


ENTRYPOINT java -jar /opt/DB-Handler/libs/DB-Handler-all.jar



