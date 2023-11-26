#!/bin/bash

./mvnw install -Dquarkus.kubernetes.deploy=true -DskipTests=true -Dquarkus.kubernetes-client.trust-certs=true

