#!/bin/bash
cd "$(dirname "$0")"
./mvnw package
docker network create web2
docker-compose up --force-recreate --build -d
docker-compose down