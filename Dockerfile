FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/miniature_app-0.0.1.jar
COPY ${JAR_FILE} miniature_app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "miniature_app.jar"]