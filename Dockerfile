# Use Java 21 runtime (supports class version 65.0)
FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY target/mail-server-1.0.0.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
