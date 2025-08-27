FROM openjdk:17
WORKDIR /app
COPY ./target/task-pay-api.jar /app
RUN mkdir -p /app/firebase
COPY ./src/main/resources/firebase/google-services.json /app/firebase/google-services.json
EXPOSE 2024
ENTRYPOINT ["java", "-jar", "task-pay-api.jar"]

