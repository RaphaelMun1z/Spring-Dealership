FROM maven:3.8.5-openjdk-21 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:21
VOLUME /tmp
COPY --from=build /target/dealership-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8889
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-jar","demo.jar"]