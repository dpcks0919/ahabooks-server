FROM kbook-server:base AS builder
COPY . .
RUN mvn -e clean -o package

FROM adoptopenjdk/openjdk8
ARG JAR_FILE_PATH=target/*.jar
COPY --from=builder ${JAR_FILE_PATH} app.jar
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=ec2", "-Duser.timezone=Asia/Seoul", "/app.jar"]