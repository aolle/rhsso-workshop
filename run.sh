#!/bin/bash

podman run -it --rm -h sso-workshop  --name sso-workshop \
    -p 8080:8080 \
    sso-workshop
