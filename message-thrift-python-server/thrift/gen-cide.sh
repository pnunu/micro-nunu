#!/usr/bin/env bash
# py
thrift --gen py -out  ../  message.thrift

# java
thrift --gen java -out  ../../message-thrift-service-api/src/main/java  message.thrift
