FROM openjdk:11
VOLUME /aggregation
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} aggregation-service
ENTRYPOINT ["java","-jar","/aggregation-service"]

