#!/bin/bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"

testFile() {
  if [ -f $1 ]; then
    return 0
  else
    return 1
  fi
}
for A in $DIR/../README.md $DIR $DIR/ubuntu_osupgrade.sh
 do
   testFile $A
   if [ $? -eq 0 ]; then 
     echo $A is REG file
   else
     echo $A is not a REG file
   fi
 done