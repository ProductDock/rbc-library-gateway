#!/bin/bash
sudo usermod -a -G docker ${USER}
docker-credential-gcr configure-docker

docker stop rbc-library-gateway
docker rm rbc-library-gateway
docker rmi $(docker images | grep "rbc-library-gateway")

docker run -dp 8080:8080  -e SEARCH_SERVICE_URL=http://10.156.15.210:8081 -e CATALOG_SERVICE_URL=http://10.156.15.210:8082 -e RENTAL_SERVICE_URL=http://10.156.15.210:8083 --name=rbc-library-gateway gcr.io/prod-pd-library/rbc-library-gateway:$1
docker container ls -a