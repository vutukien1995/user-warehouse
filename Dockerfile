FROM eclipse-temurin:17

LABEL author="VuKien"

COPY target/user-warehouse-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]