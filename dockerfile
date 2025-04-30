# Use the official OpenJDK base image with Java 21
FROM openjdk:21-jdk-slim as builder

# Set working directory
WORKDIR /app

# Copy the Spring Boot JAR file to the container
COPY build/libs/job-scheduler-service-0.0.1-SNAPSHOT.jar /app/app.jar

# Expose the application port (default Spring Boot port is 8080)
EXPOSE 8083

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
