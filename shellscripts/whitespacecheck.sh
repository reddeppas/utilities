#!/bin/bash

# Create a safe temporary file to write to
FILE=$(mktemp /tmp/whitespace_check.XXXXXXXXXX) || exit 2

# Check for whitespace in the repo and output the filenames to the temp file
grep -Irl " $" . | grep -Ev "(^...git|kitchen|./ubuntu-bionic)" > "${FILE}"

# Count how many files have whitespace in them
count=$(wc -l "${FILE}" | awk '{print $1}')

if [ "${count}" == "0" ]; then
  echo "No trailing whitespace found."
  /bin/rm "${FILE}"
  exit 0
else
  echo "Trailing whitespace found in following files:"
  cat "${FILE}"
  /bin/rm "${FILE}"
  exit 1
fi
