#!/usr/bin/env bash


source lib/execTemplate.sh

startTemplate fdfs_trackerd start ${fastdfs_trackerd_Path} ${fastdfs_trackerd_conf_Path}

startTemplate fdfs_storaged start ${fastdfs_storaged_Path} ${fastdfs_storaged_conf_Path}


