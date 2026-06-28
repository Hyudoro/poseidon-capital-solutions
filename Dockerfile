# Testing
FROM maven:3.9-eclipse-temurin-17 AS test
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -q
COPY src ./src
RUN mvn verify

# Compiling
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
#RUN mvn package -Dmaven.test.skip=true -q
RUN mvn package -DskipTests -q

# Report export (use: docker build --target report --output type=local,dest=./jacoco-report .)
FROM scratch AS report
COPY --from=test /app/target/site/jacoco /

# Running
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
