FROM eclipse-temurin:17
WORKDIR /app
COPY Sensor.java /app/Sensor.java
RUN javac Sensor.java
CMD ["java", "Sensor"]
