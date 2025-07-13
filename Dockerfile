# ---- Build Stage ----
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
COPY docs ./docs
COPY application.yml ./src/main/resources/application.yml
RUN mvn clean package -DskipTests

# ---- Run Stage ----
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/portfolio-backend-*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
