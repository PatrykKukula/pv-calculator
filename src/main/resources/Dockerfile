FROM maven:3.9.11-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn -B dependency:go-offline
COPY src ./src
COPY .mvn .mvn
RUN mvn clean package -DskipTests -Pproduction

FROM eclipse-temurin:21
WORKDIR /app

RUN useradd -m appuser
USER appuser

COPY --from=build --chown=appuser:appuser /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Dfile.encoding=UTF-8", "-Djava.security.egd=file:/dev/./urandom", "-Djava.net.preferIPv4Stack=true", 
 "-Djava.net.preferIPv6Addresses=false","-jar", "app.jar", "--spring.profiles.active=prod"]