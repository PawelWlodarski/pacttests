#!/bin/bash
sbt -Dlogback.configurationFile=project/logback.xml "pact-verify --host localhost --port 11200"