FROM openjdk:11-jre-slim
RUN mkdir -p /usr/src/app
COPY ./target/cv-api-0.0.1-SNAPSHOT.jar /usr/src/app/cv-api.jar
EXPOSE 8080
CMD /usr/bin/env java -jar /usr/src/app/cv-api.jar
