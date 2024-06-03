# Use the official OpenJDK image for Java 17
FROM amazoncorretto:17

# Set the working directory inside the container
WORKDIR /app

# Build argument for Spring profile
ARG SPRING_PROFILES_ACTIVE

# Set environment variable for Spring profile
ENV SPRING_PROFILES_ACTIVE=${SPRING_PROFILES_ACTIVE}

# Copy the built artifact into the container
COPY build/libs/*.jar myapp.jar

EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java","-jar","/app/myapp.jar"]

## Use the official OpenJDK image for Java 17
#FROM amazoncorretto:17
#
## Set the working directory inside the container
#WORKDIR /app
#
## Copy the built artifact into the container
#COPY build/libs/Konada-0.0.1-SNAPSHOT.jar myapp.jar
#
## Run the Spring Boot application
#ENTRYPOINT ["java","-jar","/app/myapp.jar"]