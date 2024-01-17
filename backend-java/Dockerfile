FROM openjdk:17
VOLUME /tmp
EXPOSE 8080
ADD ./target/karatedopi-0.0.3-SNAPSHOT.jar karatedopi.jar
ENTRYPOINT ["java","-jar","/karatedopi.jar"]