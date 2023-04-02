FROM openjdk:11-jdk-slim
VOLUME /tmp
EXPOSE 8080
COPY target/store-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT java -Djava.security.egd=file:/dev/./urandom -jar /app.jar