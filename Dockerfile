FROM openjdk:17-jdk-alpine
ARG JAR_FILE=target/bcm.code.challenge-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} ./bcm.code.challenge.jar
ENTRYPOINT ["java","-jar","/bcm.code.challenge.jar"]
