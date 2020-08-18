#!/bin/bash

# Create a safe temporary file to write to
FILE=$(mktemp /tmp/tab_check.XXXXXXXXXX) || exit 2

# Check for tabs in the repo and output the filenames to the temp file
if [ -e "/etc/lsb-release" ]; then
  # Linux grep options
  grep -IrlP "\t" . | grep -Ev "(^...git|kitchen|./ubuntu-bionic)" > "${FILE}"
else
  # OSX grep options
  grep -Irl "\t" . | grep -Ev "(^...git|kitchen|./ubuntu-bionic)" > "${FILE}"
fi

# Count how many files have tabs in them
count=$(wc -l "${FILE}" | awk '{print $1}')

if [ "${count}" == "0" ]; then
  echo "No tabs found."
  /bin/rm "${FILE}"
  exit 0
else
  echo "Tabs found in following files:"
  cat "${FILE}"
  /bin/rm "${FILE}"
  exit 1
fi
