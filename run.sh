#!/bin/bash

set -e

echo "Checking for Docker..."
if ! command -v docker &> /dev/null; then
    echo "Docker is not installed. Please install Docker to run this script."
    echo "Alternatively, run manually:"
    echo "   ./gradlew clean build && java -jar build/libs/job-scheduler-service-0.0.1-SNAPSHOT.jar"
    exit 1
fi
echo "Docker found."

# Step 1: Build the application JAR
echo "Building Spring Boot project (excluding tests)..."
./gradlew clean build -x test

# Step 2: Verify JAR exists
JAR_SOURCE="build/libs/job-scheduler-service-0.0.1-SNAPSHOT.jar"
JAR_TARGET="build/libs/job-scheduler-service.jar"

if [ ! -f "$JAR_SOURCE" ]; then
    echo "Build failed: $JAR_SOURCE not found."
    exit 1
fi

echo "Copying JAR for Dockerfile..."
cp "$JAR_SOURCE" "$JAR_TARGET"

# Step 3: Start containers with Docker Compose
echo "Starting containers using docker-compose..."
docker compose --env-file .env up --build
