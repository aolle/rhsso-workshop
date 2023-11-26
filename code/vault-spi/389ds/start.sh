#!/bin/bash

podman run \
  -p 3389:3389 \
  -e DS_DM_PASSWORD="password" \
  docker.io/389ds/dirsrv
