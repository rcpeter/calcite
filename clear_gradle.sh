#!/bin/bash

echo "Stopping Gradle daemons..."
./gradlew --stop

echo "Cleaning project..."
./gradlew clean

echo "Clearing Gradle cache..."
sudo rm -rf ~/.gradle/caches/

echo "Clearing Visual Studio Code's cached data..."
rm -rf ~/Library/Application\ Support/Code/CachedData/*

echo "Cache clearance and cleaning process completed."

