# Use an official OpenJDK image as a parent image
FROM openjdk:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the host to the container
COPY target/UserApp-0.0.1-SNAPSHOT.jar /app/UserApp-0.0.1-SNAPSHOT.jar

# Expose the port your application runs on
EXPOSE 8081

# Define the entry point to run the JAR file
ENTRYPOINT ["java", "-jar", "/app/UserApp-0.0.1-SNAPSHOT.jar"]