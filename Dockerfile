# Step 1: builder
FROM eclipse-temurin:17-jdk as builder

WORKDIR /app

COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

COPY src ./src

# Compiling
RUN chmod +x mvnw
RUN ./mvnw -q package -DskipTests

# Step 2: final image
FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
