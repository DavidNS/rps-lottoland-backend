FROM maven:3.9.6-amazoncorretto-17-debian AS MAVEN_BUILD

COPY pom.xml /build/
COPY src /build/src/

WORKDIR /build/
RUN mvn package

FROM amazoncorretto:17-alpine3.16

ARG JAR_FILE=/build/target/*.jar

COPY --from=MAVEN_BUILD ${JAR_FILE} app.jar
EXPOSE 8080

ENTRYPOINT ["java","-jar","/app.jar"]