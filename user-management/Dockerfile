FROM openjdk:8-jdk-alpine
VOLUME /tmp
#EXPOSE 8081
ARG JAR_FILE=target/user-management-2.0.0.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]