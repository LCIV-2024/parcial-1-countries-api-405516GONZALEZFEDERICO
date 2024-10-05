FROM openjdk:17-jdk-alpine
COPY target/lciii-scaffolding-0.0.1-SNAPSHOT.jar app-country.jar
ENTRYPOINT ["java", "-jar", "app-country.jar"]