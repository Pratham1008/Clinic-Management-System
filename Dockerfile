FROM jelastic/maven:3.9.4-openjdk-22.ea-b17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:22-jdk-slim
WORKDIR /app
COPY --from=build /app/target/clinic-management-0.0.1-SNAPSHOT.jar ./app.jar
COPY src/main/resources/serviceKey.json ./serviceKey.json
EXPOSE 8080

ENTRYPOINT ["java","-jar", "app.jar"]