FROM openjdk:jre-alpine
FROM gradle:5.4.1-jdk8-alpine
WORKDIR /opt

ADD --chown=gradle:gradle . /opt/DB-Handler


RUN chown -R gradle:gradle /opt/DB-Handler
RUN chmod 755 /opt/DB-Handler
WORKDIR /opt/DB-Handler
USER gradle

EXPOSE 9999
ENTRYPOINT  gradle build && gradle run



