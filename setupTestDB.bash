#!/bin/sh

docker run --name postgresql -p 127.0.0.1:5432:5432 -e POSTGRESQL_USERNAME=translation -e POSTGRESQL_PASSWORD=translation -e POSTGRESQL_DATABASE=translation bitnami/postgresql:16.1.0
