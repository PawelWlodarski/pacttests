#!/bin/bash
sbt -Dlogback.configurationFile=project/logback.xml "pactVerify --source pacts/ --host localhost --port 11200"