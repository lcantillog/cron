# Usamos una imagen base ligera con Java 17
FROM eclipse-temurin:17-jre

# Copiamos el JAR generado por Maven al contenedor
COPY ./build/libs/cron-0.0.1-SNAPSHOT.jar .


# Exponemos el puerto que usa Spring Boot (por defecto 8080)
EXPOSE 8081

# Comando de arranque
ENTRYPOINT ["java", "-jar","cron-0.0.1-SNAPSHOT.jar"]
