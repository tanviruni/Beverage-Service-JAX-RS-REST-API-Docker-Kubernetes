FROM openjdk:8
FROM gradle:jdk8 AS build-env

#making work directory
RUN mkdir -p /usr/src/app/services/db

#defining work directory
WORKDIR /usr/src/app/services/db

#Go to the Project Directory
COPY  . /usr/src/app/services/db

#deleting build directory (if exists)
RUN rm -rf /usr/src/app/services/db/build

#building app
RUN gradle build

#assigning entry point
ENTRYPOINT ["java", "-jar", "/usr/src/app/services/db/build/libs/db-all.jar"]
#ENTRYPOINT ["tail", "-f", "/dev/null"]