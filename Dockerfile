FROM openjdk:8-jdk-alpine
RUN apk --no-cache add curl
COPY build/libs/*.jar mongo-test.jar
CMD java ${JAVA_OPTS} -jar mongo-test.jar
