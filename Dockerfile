# Build stage
FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app

# Copy only the pom.xml and the settings.xml (if available), which are likely to change less frequently
COPY pom.xml ./
# This command downloads the dependencies and caches them in the Docker layer, speeding up subsequent builds
RUN mvn dependency:go-offline -B

# Now copy the source code (src) into the container
COPY src ./src

# Build with Java 21
RUN mvn clean package -DskipTests -Dmaven.compiler.release=21

# Runtime stage (using same Java version as build)
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy only the JAR file from the build stage, not the entire app
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]