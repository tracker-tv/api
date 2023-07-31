FROM gradle:8.4.0-jdk17 as builder
ARG VERSION=1.0.0-SNAPSHOT
ARG FILE_NAME=tv-tracker-${VERSION}.jar
ENV VERSION=${VERSION}

# build
COPY . /opt/app/sources
WORKDIR /opt/app/sources

RUN echo "version=${VERSION}" > gradle.properties
RUN gradle bootJar

# extract
WORKDIR /opt/app/output
RUN java -Djarmode=layertools -jar /opt/app/sources/build/libs/${FILE_NAME} extract

FROM eclipse-temurin:17-jre-jammy

LABEL org.opencontainers.image.source=https://github.com/tracker-tv/api
LABEL org.opencontainers.image.description="TV Tracker API"
LABEL org.opencontainers.image.licenses=MIT

WORKDIR application

COPY --from=builder /opt/app/output/dependencies/ ./
COPY --from=builder /opt/app/output/spring-boot-loader/ ./
COPY --from=builder /opt/app/output/snapshot-dependencies/ ./
COPY --from=builder /opt/app/output/application/ ./

EXPOSE 8080

ENV APP_DATASOURCE_RO_JDBC_URL "jdbc:postgresql://database:5432/tv-tracker"
ENV APP_DATASOURCE_RO_USERNAME "test-user"
ENV APP_DATASOURCE_RO_PASSWORD "test-password"

ENV APP_DATASOURCE_RW_JDBC_URL "jdbc:postgresql://database:5432/tv-tracker"
ENV APP_DATASOURCE_RW_USERNAME "test-user"
ENV APP_DATASOURCE_RW_PASSWORD "test-password"

CMD ["java", "org.springframework.boot.loader.launch.JarLauncher"]
