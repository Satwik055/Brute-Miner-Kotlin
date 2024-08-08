FROM gradle:7.6.0-jdk11 AS build

WORKDIR /brute-miner-kotlin

COPY build.gradle.kts .
COPY settings.gradle.kts .
COPY src /brute-miner-kotlin/src

RUN gradle build

CMD["gradle", "run"]

