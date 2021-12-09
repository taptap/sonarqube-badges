#!/bin/bash

nohup java -Xmx1024M -Xms1024M -Dfile.encoding=UTF-8 -Dspring.config.location=./conf.properties -jar sonarqube-badges-*.jar > ./sonarqube-badges.log 2>&1 &
