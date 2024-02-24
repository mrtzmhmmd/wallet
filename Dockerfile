FROM openjdk:21

# Create a directory inside the container
RUN mkdir /wallet

# Copy the JAR file from the target directory into the container
COPY target/*.jar /wallet/app.jar

# Expose the port that your Spring Boot application runs on
EXPOSE 8080

# Set the working directory
WORKDIR /wallet

# Command to run the Spring Boot application when the container starts
ENTRYPOINT ["java", "-jar", "app.jar"]
