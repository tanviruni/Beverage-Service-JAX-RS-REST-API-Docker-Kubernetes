FROM openjdk:jre-alpine
FROM gradle:5.4.1-jdk8-alpine
WORKDIR /opt
ADD --chown=gradle:gradle . /opt/Beverage-Service


RUN chown -R gradle:gradle /opt/Beverage-Service
RUN chmod 755 /opt/Beverage-Service
WORKDIR /opt/Beverage-Service
USER gradle

EXPOSE 8080
ENTRYPOINT gradle build && gradle run



