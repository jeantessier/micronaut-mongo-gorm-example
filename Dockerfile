FROM adoptopenjdk/openjdk11-openj9:jdk-11.0.1.13-alpine-slim
COPY build/libs/*.jar mongo-test.jar
CMD java  -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -noverify ${JAVA_OPTS} -jar mongo-test.jar
