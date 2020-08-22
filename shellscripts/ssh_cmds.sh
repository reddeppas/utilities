#!/bin/bash

servers_file="/Users/admin/servers.txt"
server_list=$(cat $servers_file)

for server in $server_list;do
    echo $server
    sudo ssh admin@$server "ls"
done