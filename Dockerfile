# Stage 1: Build the application
FROM maven:3.8.6-openjdk-17-slim AS build

# Set the working directory in the container
WORKDIR /app

# Copy pom.xml and download the dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy the source code into the container
COPY src /app/src

# Build the application
RUN mvn clean package -DskipTests

# Stage 2: Run the application
FROM openjdk:17-jdk-slim

# Copy the jar from the build stage
COPY --from=build /app/target/TaskManagment-demo-0.0.1-SNAPSHOT.jar /app/task-management.jar

# Expose the port your app runs on
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "/app/task-management.jar"]
