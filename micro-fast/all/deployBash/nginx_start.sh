#!/usr/bin/env bash

source lib/execTemplate.sh

startTemplate nginx test  ${nginxPath}  -t
startTemplate nginx start ${nginxPath}



