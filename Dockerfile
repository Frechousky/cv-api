FROM openjdk:11-jre-slim
RUN mkdir -p /var/www/cv-api
COPY ./target/cv-api-0.0.1-SNAPSHOT.jar /var/www/cv-api/cv-api.jar
EXPOSE 8080
CMD /usr/bin/env java -jar /var/www/cv-api/cv-api.jar
