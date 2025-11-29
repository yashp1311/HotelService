FROM eclipse-temurin:21-jre-alpine@sha256:latest AS builder
WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine@sha256:latest
WORKDIR /app
COPY --from=builder /app/target/HotelService-0.0.1-SNAPSHOT.jar HotelService.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "HotelService.jar"]