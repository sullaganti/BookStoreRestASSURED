# RestAssured API Testing Framework

[Click Here for Latest Report](https://bhargavkumar-65.github.io/RestAssured_Framework/)

A comprehensive **RestAssured API automation framework** built with **Java**, **Maven**, and **TestNG** for testing REST APIs with robust reporting and CI/CD integration.

## 📋 Table of Contents

- [Overview](#overview)
- [Architecture](#architecture)
- [Tech Stack](#tech-stack)
- [Getting Started](#getting-started)
- [Project Structure](#project-structure)
- [Running Tests](#running-tests)
- [Reporting](#reporting)
- [CI/CD Pipeline](#cicd-pipeline)
- [Environment Configuration](#environment-configuration)
- [Test Data Management](#test-data-management)
- [Challenges & Solutions](#challenges--solutions)
- [Best Practices](#best-practices)
- [Documentation](#documentation)
- [Contributing](#contributing)

## 🎯 Overview

This framework is designed to validate **Swagger PetStore API** endpoints comprehensively, providing:
- ✅ **Complete CRUD operations testing** (Create, Read, Update, Delete)
- ✅ **Positive and negative test scenarios** 
- ✅ **Data-driven testing** with TestNG DataProviders
- ✅ **Dual reporting** with Allure and Extent Reports  
- ✅ **CI/CD integration** with GitHub Actions
- ✅ **Environment-specific configurations** with .properties files
- ✅ **Retry mechanisms** for flaky tests using iRetryAnalyzer of TestNg
- ✅ **Custom annotations** like `@AzureDevOps` `@ProdOnly` for enhanced test management

## 🏗 Architecture

The framework follows a **Page Object Model (POM)** pattern adapted for API testing:

```
📁 RestAssured_Framework/
├── 📁 src/
│   ├── 📁 main/java/
│   │   ├── 📁 base/           # Base classes and configurations
│   │   ├── 📁 listeners/      # TestNG listeners (Allure, Extent, Teams)
│   │   └── 📁 Utilities/      # Utility classes, POJOs, DataProviders
│   ├── 📁 test/java/
│   │   └── 📁 PetStore/       # Test classes organized by response codes
│   └── 📁 resources/          # Environment configs, test data
├── 📁 Suites/                 # TestNG suite XML files
└── 📁 .github/workflows/      # CI/CD pipeline configurations
```

## 🛠 Tech Stack

| Technology | Version | Purpose |
|------------|---------|---------|
| **Java** | 8+ | Core programming language |
| **Maven** | 3.6+ | Build automation and dependency management |
| **RestAssured** | 5.1.0 | REST API testing library |
| **TestNG** | 7.7.0 | Testing framework |
| **Allure** | 2.15.0 | Advanced test reporting |
| **Extent Reports** | 5.0.8 | HTML test reporting |
| **Jackson** | 2.11.2 | JSON data binding |
| **AssertJ** | 3.24.2 | Fluent assertions |
| **Lombok** | 1.18.32 | Code generation for POJOs |
| **Owner** | 1.0.8 | Configuration management of .properties files |

## 🚀 Getting Started

### Prerequisites

- **Java 8** or higher
- **Maven 3.6+**
- **Git**
- **IDE** (IntelliJ IDEA, Eclipse, VS Code)

### Installation

1. **Clone the repository:**
   ```bash
   git clone https://github.com/bhargavkumar-65/RestAssured_Framework.git
   cd RestAssured_Framework
   ```

2. **Install dependencies:**
   ```bash
   mvn clean install
   ```

3. **Verify installation:**
   ```bash
   mvn test -D TestNgXml=PetStore.xml
   ```

### Quick Start

Run your first test:
```bash
# Run all tests
mvn clean test -DTestNgXml=PetStore.xml

# Run specific test class
mvn test -Dtest=PositiveTests

# Run with specific environment
mvn test -DTestNgXml=PetStore.xml -Denvironment=PROD
```

## 📁 Project Structure

```
src/
├── main/java/
│   ├── base/
│   │   └── BaseClass.java                    # Base test configuration
│   ├── listeners/
│   │   ├── Allure/AllureListener.java        # Allure reporting
│   │   ├── Extent/ExtentListener.java        # Extent reporting  
│   │   └── MicrosoftTeams/TeamsListener.java # Teams notifications
│   └── Utilities/
│       ├── POJO/                             # Data models
│       ├── DataProvider/testData.java        # Test data providers
│       ├── envPicker/Environment.java        # Environment configs
│       └── customAnnotations/                # Custom annotations
└── test/java/
    └── PetStore/
        ├── StatusCode2xx/PositiveTests.java  # Positive test cases
        └── StatusCode4xx/NegativeTests.java  # Negative test cases
```

## 🏃‍♂️ Running Tests

### Local Execution

#### Command Line Options

```bash
# Basic execution
mvn clean test -D TestNgXml=PetStore.xml

# With environment parameter
mvn test -D TestNgXml=PetStore.xml -D environment=PROD/DEV

# Generate reports
mvn allure:report

# Open reports
mvn allure:serve

#### IDE Execution

1. **Right-click** on `Suites/PetStore.xml`
2. **Select** "Run As" → "TestNG Suite"
3. **View results** in Allure/ Extent reports

### Test Categories

| Test Category | Description | Test Methods |
|---------------|-------------|--------------|
| **Positive Tests** | Valid API operations | POST, GET, PUT, DELETE operations |
| **Negative Tests** | Invalid/edge cases | Invalid data, non-existent resources |
| **Data-Driven Tests** | Multiple datasets | Pet creation with various statuses |

### Test Scenarios Covered

#### ✅ CRUD Operations
- **CREATE**: Add new pets with different statuses
- **READ**: Retrieve pets by ID and status
- **UPDATE**: Modify existing pet information  
- **DELETE**: Remove pets and verify deletion

#### ✅ Validation Points
- ✓ HTTP status codes (200, 404, 405)
- ✓ Response headers validation
- ✓ JSON schema validation
- ✓ Data integrity checks

## 📊 Reporting

### Dual Reporting System

The framework generates two types of comprehensive reports:

#### 1. 🎯 Allure Reports

**Access:** `target/site/allure-maven-plugin/index.html`

#### 2. 📋 Extent Reports  

**Access:** `ExtentReports/ExtentReport.html`

### Generating Reports

```bash
# Generate Allure report
mvn allure:report

# View Allure report
mvn allure:serve

# Extent reports are auto-generated during test execution
```

### CI/CD Reports

Reports are automatically deployed to **GitHub Pages** after each CI run:
- **Live Reports:** `https://bhargavkumar-65.github.io/RestAssured_Framework/`
- **Build-specific URLs** for historical tracking

## 🔄 CI/CD Pipeline

### GitHub Actions Workflow

The framework includes a robust CI/CD pipeline with:

#### Pipeline Features
- ✅ **Automated testing** on push/PR
- ✅ **Multi-environment support**
- ✅ **Parallel execution**
- ✅ **Report generation** (Allure + Extent)
- ✅ **GitHub Pages deployment**
- ✅ **Build artifacts** preservation

#### Accessing CI Reports

1. **Navigate to Actions tab** in this GitHub repository
2. **Click on latest workflow run**
3. **View deployment** link in the summary
4. **Access reports** via GitHub Pages URL

## ⚙️ Environment Configuration

### Supported Environments

| Environment | Base URI | Purpose |
|-------------|----------|---------|
| **PROD** | `https://petstore.swagger.io` | Production testing |
| **DEV** | `https://petstore.swagger.io` | Development testing |

### Configuration Files

```properties
# src/main/resources/environment/PROD.properties
BaseURI=https://petstore.swagger.io
```

### Environment Selection

```bash
# Via command line
mvn test -DTestNgXml=PetStore.xml -Denvironment=PROD

# Via TestNG XML
<parameter name="environment" value="PROD"/>
```

## 📊 Test Data Management

### Data-Driven Testing

The framework uses **TestNG DataProviders** for comprehensive test coverage:

```java
@DataProvider(name = "createPetData")
public Object[][] createPetData() {
    return new Object[][] {
        {"Fluffy", 123, PetStatus.AVAILABLE},
        {"Buddy", 456, PetStatus.PENDING},
        {"Max", 789, PetStatus.SOLD}
    };
}
```

### Test Data Categories

| Data Provider | Purpose | Test Scenarios |
|---------------|---------|----------------|
| `createPetData` | Pet creation | Multiple pets with different attributes |
| `updatePetData` | Pet updates | Status changes and data modifications |
| `petStatusData` | Status filtering | Available, pending, sold pets |
| `deletePetData` | Pet deletion | Cleanup and verification |

### Dynamic Data Generation

```java
// Random string generation
generateRandomString(10)

// Random numeric generation  
generateRandomNumericString(5)
```

## 🔧 Challenges & Solutions

### Challenge 1: Flaky Test Handling
**Problem:** Network timeouts and intermittent failures
**Solution:** 
- Implemented retry mechanism with `@Test(retryAnalyzer = retry.class)`
- Configurable retry count (default: 3 attempts)

### Challenge 2: Environment Management
**Problem:** Multiple environment configurations
**Solution:**
- Environment-specific property files
- Runtime environment selection
- Parameterized TestNG suites

### Challenge 3: Report Consolidation
**Problem:** Multiple report formats and CI integration
**Solution:**
- Dual reporting system (Allure + Extent)
- GitHub Pages deployment
- Build-specific report URLs

### Challenge 4: Test Data Dependencies
**Problem:** Test execution order and data consistency
**Solution:**
- TestNG dependency management `@Test(dependsOnMethods="")`
- ITestContext for data sharing
- Independent test data creation

### Challenge 5: CI/CD Pipeline Complexity
**Problem:** Complex workflow with multiple steps
**Solution:**
- Modular pipeline design
- Conditional step execution


## 📋 Test Coverage Summary

| API Endpoint | HTTP Method | Test Scenarios | Status |
|--------------|-------------|----------------|---------|
| `/pet` | POST | Create pet with valid/invalid data | ✅ |
| `/pet` | PUT | Update existing/non-existent pet | ✅ |
| `/pet/{id}` | GET | Retrieve pet by valid/invalid ID | ✅ |
| `/pet/{id}` | DELETE | Delete existing pet | ✅ |
| `/pet/{id}` | POST | Update pet with form data | ✅ |
| `/pet/findByStatus` | GET | Find pets by status | ✅ |

## 🔗 Useful Links

- **GitHub Repository:** [RestAssured_Framework](https://github.com/bhargavkumar-65/RestAssured_Framework)
- **Live Reports:** [GitHub Pages](https://bhargavkumar-65.github.io/RestAssured_Framework/)
- **PetStore API:** [Swagger PetStore](https://petstore.swagger.io/)
- **RestAssured Documentation:** [REST Assured](https://rest-assured.io/)
- **TestNG Documentation:** [TestNG](https://testng.org/doc/)