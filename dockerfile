FROM eclipse-temurin:21.0.2_13-jdk AS builder
WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:21.0.2_13-jdk
WORKDIR /app
COPY --from=builder /app/target/HotelService-0.0.1-SNAPSHOT.jar HotelService.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "HotelService.jar"]