# Project Pipeline CI/CD

Mise en place d'une pipeline DevOps avec des tests automatisés.

## Prerequisites
- Java 17
- JDK 17
- Spring Boot 3.2.2

## Getting Started

### Step 1: Initialisez un dépôt Git local sur votre ordinateur et connectez-le à votre dépôt GitHub.
```bash
# Clonez le dépôt GitHub
git clone https://github.com/yosraomran/project_pipeline_ci_cd.git

# Accédez au répertoire cloné
cd app_devops_pipeline

# Configurez le dépôt distant GitHub
git remote add origin https://github.com/yosraomran/project_pipeline_ci_cd.git

# Vérifiez la configuration du dépôt distant
git remote -v
```

### Step 2: Structurez le code de projet en packages, modules ou classes appropriés.
Organisez le  code Java en packages: models,controllers,repositories.

### Step 3: Create GitHub Actions Workflow
Create a new file called `.github/workflows/maven.yml` in the root directory of your project. This file will contain the instructions for building and testing your application.

```yaml
name: CI/CD Pipeline

on:
  push:
    branches: [main]
  pull_request:
    branches: [main]

jobs:
  build:
    name: Build
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      - name: Set up JDK
        uses: actions/setup-java@v2
        with:
          java-version: '17'
      - name: Cache Maven packages
        uses: actions/cache@v2
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
    needs: [build]
    steps:
      - uses: actions/checkout@v2
      - name: Set up JDK
        uses: actions/setup-java@v2
        with:
          java-version: '17'
      - name: Run automated tests
        run: |
          mvn test

  ````
  

This maven.yml file sets up a workflow that runs on every push to the main branch. It has one job:

build and test the application.

### Step 4: Push to GitHub Repository
Commit and push your changes to your GitHub repository. The GitHub Actions workflow should automatically trigger.

```bash
# Ajoutez tous les fichiers modifiés à l'index
git add .

# Créez un nouveau commit avec un message descriptif
git commit -m "Votre message de commit"

# Poussez les modifications vers le dépôt GitHub
git push origin main
```
