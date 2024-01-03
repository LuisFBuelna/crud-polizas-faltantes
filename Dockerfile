#inicia con la imagen base que contiene Java runtime
FROM openjdk:17-jdk-slim as build

#se agrega el jar del microservicio al contenedor
COPY target/app.jar app.jar

#se ejecuta el microservicio
ENTRYPOINT ["java", "-jar", "/app.jar"]