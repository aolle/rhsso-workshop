#!/bin/bash

# helper script, place in $JBOSS_HOME/bin

rm -rf ../standalone/deployments/*
rm -rf ../standalone/log/*
rm -rf ../standalone/data
cp ../standalone/configuration/standalone.xml.orig.bk ../standalone/configuration/standalone.xml  -v

