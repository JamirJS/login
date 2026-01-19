FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

COPY pom.xml .
RUN mvn -B -q -DskipTests dependency:go-offline

COPY src ./src
RUN mvn -B -DskipTests=false clean package

FROM eclipse-temurin:17-jre
WORKDIR /app

ENV PORT=8070
EXPOSE 8070

COPY --from=build /app/target/app-login-0.0.1.jar app-login.jar

CMD ["sh", "-c", "java -Dserver.port=${PORT} -jar app-login.jar"]