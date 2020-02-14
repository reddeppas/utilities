#!/bin/bash

#From trusty -> xenial
#1>  apply updates to current version of OS
#                sudo apt-get update
#2> Install update manager if not installed already
#            sudo apt-get install update-manager-core
#3> Run release upgrade command
# sudo do-release-upgrade
#From xenial -> bionic
#4> after upgrading to  xenial , apply updates to current version of OS
#                sudo apt-get update
#5> Run release upgrade command
#                sudo do-release-upgrade
#6> after upgrading to bionic, apply updates to current version of OS
#                sudo apt-get update

function get_os_codename () {
    current_os=$(cat /etc/lsb-release | grep DISTRIB_CODENAME | awk -F'=' '{ print $2 }')
    echo $current_os
}

current_os="$(get_os_codename)"

if [ $current_os == 'bionic' ]
then
    echo "OS is already upgarded to Ubuntu - 18.04 exiting the OS upgrade"
    exit 0 
fi 

if [ $current_os == 'trusty' ]
then
    echo 'upgrading from trusty to xenial'
    apt-get update
    apt-get dist-upgrade
    if [ ! $(which do-release-upgrade) ]
    then
        apt-get install update-manager-core
        do-release-upgrade
     else
        do-release-upgrade
     fi
    current_os="$(get_os_codename)"
    echo "Upgraded successfully...  Current OS CODE Name: $current_os"
elif [ $current_os == 'xenial' ]
then
    apt-get update
    apt-get dist-upgrade
    do-release-upgrade
    current_os="$(get_os_codename)"
    echo "Upgraded Successfully ... Current OS CODE Name: $current_os"
else
    current_os="$(get_os_codename)"
    echo "Upgraded Successfully ... Current OS CODE Name: $current_os. applying OS updates"
    apt-get update
    apt-get dist-upgrade
fi
