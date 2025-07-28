FROM eclipse-temurin:20-jdk-alpine

WORKDIR /app

COPY target/paymentservice-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8087:8087

ENTRYPOINT ["java","-jar","app.jar"]