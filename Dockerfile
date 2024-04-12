# Use the official OpenJDK image for Java 17
FROM openjdk:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the built artifact into the container
COPY build/libs/Konada-0.0.1-SNAPSHOT.jar myapp.jar

# Run the Spring Boot application
ENTRYPOINT ["java","-jar","/app/myapp.jar"]
