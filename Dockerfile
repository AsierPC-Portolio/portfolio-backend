# Use an official OpenJDK runtime as a parent image
FROM eclipse-temurin:17-jdk-alpine

# Set the working directory
WORKDIR /app

# Copy the Maven build output (fat jar) to the container
COPY target/portfolio-backend-*.jar app.jar

# Expose the port your app runs on (default Spring Boot port)
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java","-jar","app.jar"]
