# project_Pipeline_ci_cd
Mise en place d'une pipeline DevOps avec des tests automatisés

# Prerequisites
##### . Java 17
##### . JDK 17
##### . Spring boot 3.2.2
# Getting Started
## Step 1: Set up Github Repository
Create a new Github repository for your Spring boot application project.
## Step 2: Create Github Actions Workflow
Create a new file called .github/workflows/maven.yml in the root directory of your project. This file will contain the instructions for building, testing your application.

 name: CI/CD Pipeline

on:
  push:
    branches: [master]
  pull_request:
    branches: [master]

jobs:
  build:
    name: Build
    runs-on: ubuntu-latest
    steps:
      #Check-out your repository under $GITHUB_WORKSPACE, so your workflow can access it
      - uses: actions/checkout@v1
      #Set up JDK 17
      - name: Set up JDK
        uses: actions/setup-java@v1
        with:
          java-version: '17'
      - name: Cache Maven packages
        uses: actions/cache@v1
        with:
          path: ~/.m2
          key: ${{ runner.os }}-m2-${{ hashFiles('**/pom.xml') }}
          restore-keys: ${{ runner.os }}-m2
      - name: Build with Maven
        run: |
          mvn clean
          mvn -B package --file pom.xml
  test:
    name: Test
    runs-on: ubuntu-latest
    steps:
      - name: Checkout repository
        uses: actions/checkout@v1
      - name: Set up JDK
        uses: actions/setup-java@v1
        with:
          java-version: '17'
      - name: Run automated tests
        run: |
          mvn test
  deployment:
    name: Deployment
    runs-on: ubuntu-latest
    needs: [test] # This job depends on the 'test' job
    steps:
    - name: Checkout repository
      uses: actions/checkout@v2
    - name: Build the Docker image
      uses: docker/build-push-action@v4
      with:
        context: .
        dockerfile: Dockerfile
        push: false
        tags: ${{ secrets.DOCKER_HUB_USERNAME }}/devops-pipeline:v1.0.0

    - name: Login to Docker Hub
      uses: docker/login-action@v1
      with:
        username: ${{ secrets.DOCKER_HUB_USERNAME }}
        password: ${{ secrets.DOCKER_HUB_ACCESS_TOKEN }}

    - name: Push the Docker image to Docker Hub
      uses: docker/build-push-action@v4
      with:
        context: .
        dockerfile: Dockerfile
        push: true
        tags: ${{ secrets.DOCKER_HUB_USERNAME }}/devops-pipeline:v1.0.0

This maven.yml file sets up a workflow that runs on every push to the main branch. It has one job:

build and test the application.

## Step 4: Push to Github Repository
Commit and push your changes to your Github repository. The Github Actions workflow should automatically trigger.
