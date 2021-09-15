#!/bin/bash

docker build -t zesow/organization:1.31 .
docker tag zesow/organization:1.31 zesow/organization:1.31
docker push zesow/organization:1.31

