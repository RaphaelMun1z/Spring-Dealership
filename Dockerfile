FROM openjdk:21
VOLUME /tmp
RUN mvn clean package -D skipTests
RUN docker compose up -d --build
ADD target/dealership-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8889
RUN bash -c 'touch /app.jar'
ENTRYPOINT [ "java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar" ]