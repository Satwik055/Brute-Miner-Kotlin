FROM gradle:7.6.0-jdk11 AS build

WORKDIR /Brute-Miner-Kotlin

COPY build.gradle.kts .
COPY settings.gradle.kts .
COPY gradlew .
COPY src /Brute-Miner-Kotlin/src

RUN ./gradlew build

ENTRYPOINT ["./gradlew", "run"]
