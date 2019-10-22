#!/usr/bin/env bash
cd /opt/tools/apache-flume-1.8.0-bin
bin/flume-ng agent --conf conf --conf-file conf/flume.conf --name a1