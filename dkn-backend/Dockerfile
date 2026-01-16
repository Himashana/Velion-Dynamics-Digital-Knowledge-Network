# Stage 1: Build
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copy only the project definition
COPY pom.xml .
# Download dependencies
RUN mvn dependency:go-offline

# Copy the rest of the source code
COPY src ./src

# Build the app
RUN mvn clean package -DskipTests

# Stage 2: Run
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copy the JAR file built in Stage 1
COPY --from=build /app/target/*.jar app.jar

# Expose port 8080 (Render's default)
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]