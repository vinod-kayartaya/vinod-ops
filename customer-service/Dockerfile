FROM openjdk:17-alpine
WORKDIR /app-root
COPY ./target/*.jar ./app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
