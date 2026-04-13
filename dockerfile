# Stage 1: Build using JDK 25
FROM eclipse-temurin:25-jdk-alpine AS build
WORKDIR /sp26-crud-api-demo
COPY . .
# Use the Maven wrapper included in the project to build
RUN ./mvnw clean package -DskipTests

# Stage 2: Runtime using JRE 25 (lighter)
FROM eclipse-temurin:25-jre-alpine
WORKDIR /sp26-crud-api-demo
COPY --from=build /sp26-crud-api-demo/target/*.jar CrudApiApplication.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "CrudApiApplication.jar"]