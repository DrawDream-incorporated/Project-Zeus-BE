# Use the official OpenJDK image for Java 17
FROM amazoncorretto:17

# Set the working directory inside the container
WORKDIR /app


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