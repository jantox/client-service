FROM openjdk:8-jdk-alpine

ADD ./build/libs/client-service-0.0.1-SNAPSHOT.jar /usr/src/client-service-0.0.1-SNAPSHOT.jar
WORKDIR usr/src
ENTRYPOINT ["java","-jar", "client-service-0.0.1-SNAPSHOT.jar"]