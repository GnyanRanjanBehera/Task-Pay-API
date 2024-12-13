FROM openjdk:18-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
RUN mkdir -p src/main/resources
COPY src/main/resources/google-services.json src/main/resources/google-services.json
ENTRYPOINT ["java","-jar","/app.jar"]
