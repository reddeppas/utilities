#!/bin/bash


# Create a safe temporary file to write to
FILE=$(mktemp /tmp/smart_quote_check.XXXXXXXXXX) || exit 2

# Check for tabs in the repo and output the filenames to the temp file.
grep -Irl "[‘’“”–]" . | grep -Ev "(^...git|kitchen|./ubuntu-bionic|smart_quote_check)" > "${FILE}"

# Count how many files have smart quotes in them.
count=$(wc -l "${FILE}" | awk '{print $1}')

if [ "${count}" == "0" ]; then
  # None were found, exit successfully after cleaning up the temp file.
  echo "No smart quotes found."
  /bin/rm "${FILE}"
  exit 0
else
  # Smart quotes were found, display the list of files containing them, remove the temp file,
  # and then exit with an error to make rake abort.
  echo "Smart quotes found in following files:"
  cat "${FILE}"
  /bin/rm "${FILE}"
  exit 1
fi
