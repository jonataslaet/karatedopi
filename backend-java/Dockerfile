FROM eclipse-temurin:17-jdk-alpine as builder
WORKDIR /app
COPY . .
RUN apk add maven
RUN mvn clean package -DskipTests
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
EXPOSE 8080
COPY --from=builder /app/target/karatedopi-0.0.3-SNAPSHOT.jar karatedopi.jar
CMD [ "java", "-jar", "karatedopi.jar"]
