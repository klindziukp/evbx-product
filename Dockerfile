FROM openjdk:8-jdk-alpine

COPY src/main/resources/application-docker.yml /config/
ARG JAR_FILE=build/libs/evbx-product-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
EXPOSE 8001
CMD java -Dspring.config.location=config/application-docker.yml -jar app.jar
