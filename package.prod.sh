#!/usr/bin/env bash
docker run --rm \
  -v $(pwd):/tmp \
  -v $HOME/.m2:/root/.m2 \
  -w /tmp \
  --privileged \
  maven:3.6.3-openjdk-11-slim \
  mvn clean package -DskipTests=true -Pprod
  