# BookStore API Testing Framework

[Click Here for Latest Report](https://sullaganti.github.io/BookStoreRestASSURED/)

A comprehensive **RestAssured API automation framework** built with **Java**, **Maven**, and **TestNG** for testing BookStore REST APIs with robust reporting and CI/CD integration. The framework includes JWT authentication, comprehensive book management operations, and user registration/login functionality.

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

This framework is designed to validate **BookStore API** endpoints comprehensively, providing:
- ✅ **Complete CRUD operations testing** for Books (Create, Read, Update, Delete)
- ✅ **User Authentication** testing (Registration and Login with JWT tokens)
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
📁 BookStoreRestASSURED/
├── 📁 src/
│   ├── 📁 main/java/
│   │   ├── 📁 base/           # Base classes and configurations
│   │   ├── 📁 listeners/      # TestNG listeners (Allure, Extent, Teams)
│   │   └── 📁 Utilities/      # Utility classes, POJOs, DataProviders
│   ├── 📁 test/java/
│   │   └── 📁 BookStore/      # Test classes organized by functionality
│   │       ├── 📁 Positive/   # Positive test scenarios
│   │       └── 📁 Negative/   # Negative test scenarios
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
- **BookStore API** running on `http://127.0.0.1:8000/` (See [bookstore](./bookstore/) directory for API setup)

### BookStore API Setup

The BookStore API is included in this project under the `bookstore/` directory. To set it up:

1. **Navigate to the bookstore directory:**
   ```bash
   cd bookstore/bookstore
   ```

2. **Install Python dependencies:**
   ```bash
   pip install -r requirements.txt
   ```

3. **Start the BookStore API:**
   ```bash
   uvicorn main:app --reload
   ```

4. **Verify API is running:**
   - API will be available at `http://127.0.0.1:8000`
   - API documentation at `http://127.0.0.1:8000/docs`
   - Health check at `http://127.0.0.1:8000/health`

### Installation

1. **Clone the repository:**
   ```bash
   git clone https://github.com/sullaganti/BookStoreRestASSURED.git
   cd RestAssured_Framework
   ```

2. **Install dependencies:**
   ```bash
   mvn clean install
   ```

3. **Verify installation:**
   ```bash
   mvn test -D TestNgXml=BookStore.xml
   ```

### Quick Start

Run your first test:
```bash
# Run all tests
mvn clean test -DTestNgXml=BookStore.xml

# Run specific test class
mvn test -Dtest=RegistrationTests

# Run with specific environment
mvn test -DTestNgXml=BookStore.xml -Denvironment=PROD
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
    └── BookStore/
        ├── Positive/
        │   ├── RegistrationTests.java        # User registration tests
        │   └── BooksTests.java               # Book CRUD operations tests
        └── Negative/
            ├── RegistrationNegativeTests.java # Registration negative tests
            ├── LoginNegativeTests.java        # Login negative tests
            └── BooksNegativeTests.java        # Books negative tests
```

## 🏃‍♂️ Running Tests

### Local Execution

#### Command Line Options

```bash
# Basic execution
mvn clean test -D TestNgXml=BookStore.xml

# With environment parameter
mvn test -D TestNgXml=BookStore.xml -D environment=PROD/DEV

# Generate reports
mvn allure:report

# Open reports
mvn allure:serve

#### IDE Execution

1. **Right-click** on `Suites/BookStore.xml`
2. **Select** "Run As" → "TestNG Suite"
3. **View results** in Allure/ Extent reports

### Test Categories

| Test Category | Description | Test Methods |
|---------------|-------------|--------------|
| **Positive Tests** | Valid API operations | User Registration, Login, Book CRUD operations |
| **Negative Tests** | Invalid/edge cases | Invalid data, authentication failures, non-existent resources |
| **Data-Driven Tests** | Multiple datasets | Book creation with various data sets, user registration scenarios |

### Test Scenarios Covered

#### ✅ CRUD Operations
- **CREATE**: Add new books with different attributes and user registration
- **READ**: Retrieve books by ID, get all books, user authentication
- **UPDATE**: Modify existing book information  
- **DELETE**: Remove books and verify deletion

#### ✅ Validation Points
- ✓ HTTP status codes (200, 400, 404, 422, 500)
- ✓ Response headers validation
- ✓ JSON schema validation
- ✓ JWT token authentication
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
- **Live Reports:** `https://github.com/sullaganti/BookStoreRestASSURED.git`
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
| **PROD** | `http://127.0.0.1:8000/` | Production/Local testing |
| **DEV** | `http://127.0.0.1:8000/` | Development testing |

### Configuration Files

```properties
# src/main/resources/environment/PROD.properties
BookStore_BaseURI=http://127.0.0.1:8000/
```

### Environment Selection

```bash
# Via command line
mvn test -DTestNgXml=BookStore.xml -Denvironment=PROD

# Via TestNG XML
<parameter name="environment" value="PROD"/>
```

## 📊 Test Data Management

### Data-Driven Testing

The framework uses **TestNG DataProviders** for comprehensive test coverage:

```java
@DataProvider(name = "createBookData")
public Object[][] createBookData() {
    return new Object[][] {
        {"The Great Gatsby", "F. Scott Fitzgerald", 1925, "A classic American novel"},
        {"To Kill a Mockingbird", "Harper Lee", 1960, "A story of racial injustice"},
        {"1984", "George Orwell", 1949, "A dystopian social science fiction novel"}
    };
}
```

### Test Data Categories

| Data Provider | Purpose | Test Scenarios |
|---------------|---------|----------------|
| `createBookData` | Book creation | Multiple books with different attributes |
| `updateBookData` | Book updates | Content changes and data modifications |
| `registrationData` | User registration | Valid and invalid user data |
| `loginData` | User authentication | Successful and failed login attempts |

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
| `/signup` | POST | User registration with valid/invalid data | ✅ |
| `/login` | POST | User authentication with correct/incorrect credentials | ✅ |
| `/books/` | POST | Create book with valid/invalid data (requires auth) | ✅ |
| `/books/` | GET | Retrieve all books (requires auth) | ✅ |
| `/books/{id}` | GET | Retrieve book by valid/invalid ID (requires auth) | ✅ |
| `/books/{id}` | PUT | Update existing/non-existent book (requires auth) | ✅ |
| `/books/{id}` | DELETE | Delete existing book (requires auth) | ✅ |
| `/health` | GET | API health check | ✅ |

## 🔗 Useful Links

- **GitHub Repository:** [BookStoreRestASSURED](https://github.com/sullaganti/BookStoreRestASSURED.git)
- **Live Reports:** [GitHub Pages](https://sullaganti.github.io/BookStoreRestASSURED/)
- **BookStore API Documentation:** [Local API Docs](http://127.0.0.1:8000/docs)
- **RestAssured Documentation:** [REST Assured](https://rest-assured.io/)
- **TestNG Documentation:** [TestNG](https://testng.org/doc/)