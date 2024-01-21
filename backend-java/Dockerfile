FROM eclipse-temurin:17-jdk-jammy AS build
RUN apt-get update && apt-get install -y maven
WORKDIR /app
COPY ./pom.xml /app/pom.xml
RUN mvn -B dependency:go-offline
COPY . /app
RUN mvn clean package -DskipTests
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY --from=build /app/target/karatedopi-0.0.3-SNAPSHOT.jar karatedopi.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "karatedopi.jar"]
