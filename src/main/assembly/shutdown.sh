#!/bin/bash
kill -15 `ps -ef|grep sonarqube-badges-*.jar|awk 'NR==1{print $2}'`
