#!/bin/bash
sbt -Dlogback.configurationFile=project/logback.xml "pact-verify --source pacts/ --host localhost --port 11200"