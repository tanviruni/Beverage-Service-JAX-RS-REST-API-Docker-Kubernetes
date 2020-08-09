FROM openjdk:jre-alpine
FROM gradle:5.4.1-jdk8-alpine
WORKDIR /opt

ADD --chown=gradle:gradle . /opt/Management-Service

RUN chown -R gradle:gradle /opt/Management-Service
RUN chmod 755 /opt/Management-Service
WORKDIR /opt/Management-Service
USER gradle

EXPOSE 8090
ENTRYPOINT  gradle build && gradle run



