FROM openjdk:14-alpine
COPY build/libs/mongo-test-*-all.jar mongo-test.jar
EXPOSE 8080
CMD ["java", "-Dcom.sun.management.jmxremote", "-Xmx128m", "-jar", "mongo-test.jar"]
