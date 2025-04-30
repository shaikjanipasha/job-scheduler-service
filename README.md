## 🛠️ Job Scheduler Service
A robust, extensible, and production-ready Job Scheduler Service built using Java, Spring Boot, and PostgreSQL — designed for distributed environments with dynamic job registration, observability, fault tolerance, and full audit logging.

## 🚀 Features
- ✅ Dynamic Job Registration via REST APIs
- ⏰ Flexible Scheduling with support for:
  - Cron expressions (cron_expression)
  - Fixed-rate execution (fixed_rate_ms)

- ♻️ Resilient Execution
  - Retry logic on failure
  - Recovery on service restarts

- 📊 Observability & Logging
   - Distributed tracing via micrometer-tracing-bridge-brave with automatic span creation
   - Trace context (Trace ID, Span ID) included in logs for seamless debugging

- 🧠 Pluggable Execution Logic
   - Job execution routed to specific executors based on job name
   - Default executor handles unmatched jobs
   - To add new jobs, just implement the executor and register the job

- ⚖️ Concurrency Control
   - Ensures single execution across nodes using PostgreSQL FOR UPDATE row-level locking
   - Distributed-safe execution with built-in DB-based locking mechanism

- 🔐 User Management & Security
   - Supports user onboarding and login via REST APIs
   - Secured with JWT-based authentication and authorization
   - Protects endpoints with token validation and access control

- 📜 Liquibase Database Deployment
   - Automated database schema management and versioning
   - Supports multiple environments (dev, test, prod) with separate configurations


## Installation
**Approach-1**: To get your job-scheduler-service up and running with Rancher Desktop, follow the steps below:
1. **Install Rancher Desktop**: Download and install Rancher Desktop(containerd) from the [official website](https://rancherdesktop.io/).
2. Reboot Your Machine: After installing Rancher Desktop, reboot your machine to ensure all changes take effect.
- Note: if you are encountering issues with respective to WLS2 the rancher installation, please follow the below steps:
Open the Windows PowerShell as an administrator and run the following command:
  1) Check if WSL is Installed
     - wsl --list --verbose
     - If WSL is not recognized, you need to install it. If WSL1 is there, you need to upgrade to WSL2.
  2) Install or Upgrade to WSL2, this will Install the WSL2 engine and set it as default
        - wsl --install (or) wsl.exe --install
  3) Set WSL2 as Default (if needed)
     - wsl --set-default-version 2
  4) Reboot your machine
  5) Now, install the Rancher Desktop and verify with command on powershell
     - docker --version
3. **Install Git**: Download and install Git from the [official website](https://git-scm.com/downloads).

### Clone the Repository and Execute below command from the root directory of the project.
    bash ./run.sh
    (or)
    ./run.sh

### Access the Application
- **Swagger UI**: Open your browser and navigate to `http://localhost:8083/swagger-ui/index.html` to access the Swagger UI for API documentation and testing.
     Refer "\job-scheduler-service\src\test\samplepayloads" this location in the repo for sample payloads

####
**Approach-2**: To get your job-scheduler-service up and running with in local with manual setup, follow the steps below:
1. **Install Java**: Ensure you have Java 21("21.0.4"). You can download it from the https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html. Post installation of Java 21.0.4 set the path in environment variables(user and system).
2. **Install PostgreSQL**: Download and install PostgreSQL("16.8") from the https://www.enterprisedb.com/downloads/postgres-postgresql-downloads. During installation, set the password for the `postgres` user with password `admin`. Grant all privileges on the database to this user.
3. **Install Git**: Download and install Git from the [official website](https://git-scm.com/downloads). 
4. **Install Gradle**: Download and install Gradle("8.7") from the [official website](https://gradle.org/install/). https://gradle.org/next-steps/?version=8.7&format=all. Post installation set the path in environment variables(user and system).
5. **Clone the Repository**: Clone the repository to your local machine using Git
6. **Build the project**: Navigate to the project directory and run the application using the following command:
    ./gradlew clean build
       (or)
    ./gradlew clean build -x test
7.  **Run the Application**: Navigate to the project directory and run the application using the following command:
    ./gradlew bootRun
    (or)
    java -jar build/libs/job-scheduler-service-0.0.1-SNAPSHOT.jar
8. **Access the Application**: Open your browser and navigate to `http://localhost:8083/swagger-ui/index.html` to access the Swagger UI for API documentation and testing.
     Refer "\job-scheduler-service\src\test\samplepayloads" this location in the repo for sample payloads

 




