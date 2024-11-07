FROM eclipse-temurin:21

WORKDIR /app

COPY target/card-order-0.0.1-SNAPSHOT.jar /app/card-order-0.0.1-SNAPSHOT.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "card-order-0.0.1-SNAPSHOT.jar"]
