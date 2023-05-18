FROM openjdk:20
MAINTAINER manojKumarAnanthu
COPY target/microservice-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]