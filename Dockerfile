# Step 1: Use a base image with Maven or Gradle
FROM maven:3.8.6-eclipse-temurin-17 AS build

# Set the working directory
WORKDIR /app

# Copy the pom.xml and the source code
COPY pom.xml .
COPY src ./src

# Build the application (creates the target/clustering-coefficient-calculator.jar)
RUN mvn clean package -DskipTests

# Step 2: Use a smaller base image for the final container
FROM openjdk:17-slim

# Set the working directory for the final image
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/clustering-coefficient-calculator-0.0.1-SNAPSHOT.jar /app/clustering-coefficient-calculator.jar

# Run the JAR file
CMD ["java", "-jar", "clustering-coefficient-calculator.jar"]