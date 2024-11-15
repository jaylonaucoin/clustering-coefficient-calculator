# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file into the container
COPY target/clustering-coefficient-calculator.jar /app/clustering-coefficient-calculator.jar

# Run the JAR file
CMD ["java", "-jar", "clustering-coefficient-calculator.jar"]