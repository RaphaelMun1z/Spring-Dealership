FROM openjdk:21 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:21
COPY --from=build /target/dealership-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8889
ENTRYPOINT ["java","-jar","demo.jar"]