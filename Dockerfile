# Dockerfile Multi-stage para Backend Ejercicio5MyAPIGestor en Java 21 LTS
FROM maven:3.9.9-eclipse-temurin-21-alpine AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B || true
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
VOLUME /tmp
COPY --from=builder /app/target/*.jar app.jar

ENV PORT=8081
ENV SPRING_DATASOURCE_URL=jdbc:mariadb://localhost:3306/dbgestor
ENV SPRING_DATASOURCE_USERNAME=gestor
ENV SPRING_DATASOURCE_PASSWORD=gestor123

EXPOSE 8081
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]
