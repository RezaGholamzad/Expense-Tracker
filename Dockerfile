# Use the official OpenJDK base image
FROM amazoncorretto:17

# Set the working directory inside the container
WORKDIR /app

# the JAR file path
ARG JAR_FILE=target/expense-tracker*.jar

# Copy the JAR file from the build context into the Docker image
COPY ${JAR_FILE} /app/expense-tracker.jar

# Expose the port the app runs on (optional)
EXPOSE 8080

# Command to run the application
CMD ["java", "-jar", "expense-tracker.jar"]