FROM maven:3.9.8-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:21
WORKDIR /app
COPY --from=build /app/target/dealership-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8889
ENTRYPOINT ["java","-jar","demo.jar"]