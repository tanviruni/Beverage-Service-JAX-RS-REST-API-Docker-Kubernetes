FROM openjdk:8
FROM gradle:jdk8 AS build-env

#making work directory
RUN mkdir -p /usr/src/app/services/beverage

#defining work directory
WORKDIR /usr/src/app/services/beverage

#Go to the Project Directory
COPY  . /usr/src/app/services/beverage

#deleting build directory (if exists)
RUN rm -rf /usr/src/app/services/beverage/build

#building app
RUN gradle build

#assigning entry point
ENTRYPOINT ["java", "-jar", "/usr/src/app/services/beverage/build/libs/beverage-all.jar"]
#ENTRYPOINT ["tail", "-f", "/dev/null"]