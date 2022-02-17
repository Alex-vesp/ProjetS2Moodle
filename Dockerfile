FROM maven:3.5.3-jdk-8-alpine
COPY ./. /
ENTRYPOINT ["mvn","spring-boot:run"]
EXPOSE 8080