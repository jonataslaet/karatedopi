# Use an official OpenJDK runtime as a parent image
FROM adoptopenjdk:17-jdk-hotspot

# Set the working directory to /app
WORKDIR /app

# Copy the current directory contents into the container at /app
COPY . /app

# Build the application
RUN ./mvnw clean package

# Expose the port that your Spring Boot application will run on
EXPOSE 8080

# Copy the JAR file into the container
ADD ./target/karatedopi-0.0.3-SNAPSHOT.jar karatedopi.jar

# Command to run the application
CMD ["java", "-jar", "karatedopi.jar"]
