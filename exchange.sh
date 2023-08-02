#!/bin/bash

# Check if the 'java' command is available
command -v java >/dev/null 2>&1 || { echo >&2 "Java is not installed. Aborting."; exit 1; }

# Set the path to the order-book.jar file
JAR_PATH="./target/order-book.jar"

# Check if the target folder exists, and if not, run 'mvn clean install'
if [ ! -d "./target" ]; then
  echo "Target folder does not exist. Running 'mvn clean install'..."
  mvn clean install
  echo "Build successful."
fi

# Check if the order-book.jar file exists
if [ ! -f "$JAR_PATH" ]; then
  echo "The order-book.jar file does not exist. Make sure you have built the project using 'mvn package'."
  exit 1
fi

# Check if a file argument (e.g., test2.txt) is provided
if [ -z "$1" ]; then
  echo "Usage: ./exchange.sh <input_file>"
  exit 1
fi

# Check if the input file exists
if [ ! -f "$1" ]; then
  echo "Input file '$1' does not exist."
  exit 1
fi

# Run the Java application with the provided input file
java -jar "$JAR_PATH" < "$1"
