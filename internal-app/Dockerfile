FROM openjdk:11 as build
WORKDIR /workspace/app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN ./mvnw install -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

FROM openjdk:11
VOLUME /tmp
ARG DEPENDENCY=/workspace/app/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
EXPOSE 8080
# EXPOSE 8125
# ENTRYPOINT ["java","-cp","app:app/lib/*","com.example.demo.DemoApplication"]
RUN wget -O dd-java-agent.jar 'https://dtdg.co/latest-java-tracer'
ENTRYPOINT ["java","-javaagent:/dd-java-agent.jar", "-Ddd.profiling.enabled=true","-XX:FlightRecorderOptions=stackdepth=256", "-Ddd.logs.injection=true", "-Ddd.trace.sample.rate=1", "-Ddd.service=java-app-internal", "-Ddd.env=test", "-Ddd.version=1.0", "-cp","app:app/lib/*","com.example.demo.DemoApplication"]