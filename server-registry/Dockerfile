# ==========================================================
# Multi-stage Dockerfile for Server Registry (Eureka)
# ==========================================================

# Stage 1: Build JAR
FROM maven:3.9.6-eclipse-temurin-17-alpine AS builder
WORKDIR /build

COPY pom.xml .
RUN mvn dependency:go-offline -B || true

COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Lightweight, Secure Runtime
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Run as non-root user for industry security compliance
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

COPY --from=builder /build/target/*.jar app.jar

# JVM memory tuning optimized for free-tier hosting (512MB RAM)
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -Xss256k"
EXPOSE 8761

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
