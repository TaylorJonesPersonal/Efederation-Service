#!/usr/bin/env bash
cd /home/ec2-user/server/target || exit
nohup java -jar e-federation-service-0.0.1-SNAPSHOT.jar > /dev/null 2>&1 &