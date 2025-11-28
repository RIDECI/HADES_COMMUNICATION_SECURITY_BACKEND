# 📌 Hades - Communication and Security
This module enables real-time communication between passengers and drivers, incident reporting, emergency handling, deviation alerts, and secure trip monitoring. It strengthens trust and safety across the RideCI ecosystem.

## 👤 Developers
- Juan Pablo Caballero
- Karol Estupiñan
- Juan Andres Suarez 
- Nicolas Andres 
- Julian Santiago Ramirez

## 📑 Content Table
1. [Project Architecture](#-project-architecture)
    - [Hexagonal Structure](#-clean---hexagonal-structure)
2. [API Documentation](#-api-endpoints)
    - [Endpoints](#-api-endpoints)
3. [Input & Output Data](#input-and-output-data)
4. [Microservices Integration](#-connections-with-other-microservices)
5. [Technologies](#technologies)
6. [Branch Strategy](#-branches-strategy--structure)
7. [System Architecture & Design](#-system-architecture--design)
8. [Getting Started](#-getting-started)
9. [Testing](#-testing)
---
## 🏢 Project Architecture
The hades - Communication and Security have an unacoplated hexagonal - clean architecture where it looks to isolate the business logic with the other part of the app dividing it into multiple components:
* **🧠 Domain (Core)**: Contains the business logic and principal rules.
* **🎯 Ports (Interfaces)**: Are interfaces that define the actions that the domain can do.
* **🔌 Adapters (Infrastructure)**: Are the implementations of the ports that connect the domain with the specific technologies.
The use of this architecture has the following benefits:
* ✔️ **Separation of Concerns:** Distinct boundaries between logic and infrastructure.
* ✔️ **Maintainability:** Easier to update or replace specific components.
* ✔️ **Scalability:** Components can evolve independently.
* ✔️ **Testability:** The domain can be tested in isolation without a database or server.
## 📂 Clean - Hexagonal Structure
```
:📂 nemesis_travel_management_backend
┣ :📂 src/
┃ ┣ :📂 main/
┃ ┃ ┣ :📂 java/
┃ ┃ ┃ ┗ :📂 edu/dosw/rideci/
┃ ┃ ┃   ┣ 📄 NemesisTravelManagementBackendApplication.java
┃ ┃ ┃   ┣ :📂 domain/
┃ ┃ ┃   ┃ ┗ :📂 model/            # 🧠 Domain models
┃ ┃ ┃   ┣ :📂 application/
┃ ┃ ┃   ┃ ┣ :📂 ports/
┃ ┃ ┃   ┃ ┃ ┣ :📂 input/          # 🎯 Input ports (Exposed use cases)
┃ ┃ ┃   ┃ ┃ ┗ :📂 output/         # 🔌 Output ports (external gateways)
┃ ┃ ┃   ┃ ┗ :📂 usecases/         # ⚙️ Use case implementations
┃ ┃ ┃   ┣ :📂 infrastructure/
┃ ┃ ┃   ┃ ┗ :📂 adapters/
┃ ┃ ┃   ┃   ┣ :📂 input/
┃ ┃ ┃   ┃   ┃ ┗ :📂 controller/   # 🌎 Input adapters (REST controllers)
┃ ┃ ┃   ┃   ┗ :📂 output/
┃ ┃ ┃   ┃     ┗ :📂 persistence/  # 🗄️ Output adapters (persistance)
┃ ┃ ┗ :📂 resources/
┃ ┃   ┗ 📄 application.properties
┣ :📂 test/
┃ ┣ :📂 java/
┃ ┃ ┗ :📂 edu/dosw/rideci/NEMESIS_TRAVEL_MANAGEMENT_BACKEND/
┃ ┃   ┗ 📄 NemesisTravelManagementBackendApplicationTests.java
┣ :📂 docs/
┃ ┣ diagramaClases.jpg
┃ ┣ diagramaDatos.jpg
┃ ┗ diagramaDespliegue.png
┣ 📄 pom.xml
┣ 📄 mvnw / mvnw.cmd
┗ 📄 README.md
```
# 📡 API Endpoints
For detailed documentation refer to our Swagger UI (Running locally at http://localhost:8080/swagger-ui.html).
## Data input & output – Conversations API

| Method | URI | Description | Request Body / Params |
| :--- | :--- | :--- | :--- |
| `POST` | `/conversations` | Creates a new conversation (chat) between participants. | `{ participants: string[], type: string, tripId: string }` |
| `GET` | `/conversations` | Retrieves all existing conversations. | — |
| `GET` | `/conversations/{id}` | Retrieves details of a specific conversation. | `id` (Path Variable) |
| `GET` | `/conversations/{id}/messages` | Retrieves all messages for a given conversation. | `id` (Path Variable) |
| `POST` | `/conversations/{id}/messages` | Sends a new message to a conversation. | `{ senderId: string, content: string }` |

## Data input & output – Emergency Alerts API

| Method | URI | Description | Request Body / Params |
| :--- | :--- | :--- | :--- |
| `POST` | `/emergencies/activate` | Activates an emergency alert for a user during a trip. | `{ userId: number, tripId: number, currentLocation: Location }` |
| `GET` | `/emergencies/{id}` | Retrieves an emergency alert by its unique identifier. | `id` (Path Variable) |
| `GET` | `/emergencies` | Retrieves all existing emergency alerts. | — |

### 📟 HTTP Status Codes
Common status codes returned by the API.
| Code | Status | Description |
| :--- | :--- | :--- |
| `200` | **OK** | Request processed successfully. |
| `201` | **Created** | Resource (Route/Tracking) created successfully. |
| `400` | **Bad Request** | Invalid coordinates or missing parameters. |
| `401` | **Unauthorized** | Missing or invalid JWT token. |
| `404` | **Not Found** | Route or Trip ID does not exist. |
| `500` | **Internal Server Error** | Unexpected error (e.g., Google Maps API failure).

# Input and Output Data
Data information per functionability
# 🔗 Connections with other Microservices
This module does not work alone. It interacts with the RideCi Ecosystem via REST APIs and Message Brokers:
1. Travel Management Module: Receives information about the travel when the travel is completed or only created.

# Technologies
The following technologies were used to build and deploy this module:
### Backend & Core
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)
### Database
![MongoDB](https://img.shields.io/badge/MongoDB-%234ea94b.svg?style=for-the-badge&logo=mongodb&logoColor=white)
### DevOps & Infrastructure
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)
![Kubernetes](https://img.shields.io/badge/kubernetes-%23326ce5.svg?style=for-the-badge&logo=kubernetes&logoColor=white)
![Railway](https://img.shields.io/badge/Railway-131415?style=for-the-badge&logo=railway&logoColor=white)
![Vercel](https://img.shields.io/badge/vercel-%23000000.svg?style=for-the-badge&logo=vercel&logoColor=white)
### CI/CD & Quality Assurance
![GitHub Actions](https://img.shields.io/badge/github%20actions-%232671E5.svg?style=for-the-badge&logo=githubactions&logoColor=white)
![SonarQube](https://img.shields.io/badge/SonarQube-4E9BCD?style=for-the-badge&logo=sonarqube&logoColor=white)
![JaCoCo](https://img.shields.io/badge/JaCoCo-Coverage-green?style=for-the-badge)
### Documentation & Testing
![Swagger](https://img.shields.io/badge/-Swagger-%23Clojure?style=for-the-badge&logo=swagger&logoColor=white)
![Postman](https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white)
### Design
![Figma](https://img.shields.io/badge/figma-%23F24E1E.svg?style=for-the-badge&logo=figma&logoColor=white)
### Comunication & Project Management
![Jira](https://img.shields.io/badge/jira-%230A0FFF.svg?style=for-the-badge&logo=jira&logoColor=white)
![Slack](https://img.shields.io/badge/Slack-4A154B?style=for-the-badge&logo=slack&logoColor=white)
---
# 🌿 Branches Strategy & Structure
This module follows a strict branching strategy based on Gitflow to ensure the ordered versioning,code quality and continous integration.
| **Branch**                | **Purpose**                            | **Receive of**           | **Sent to**        | **Notes**                      |
| ----------------------- | ---------------------------------------- | ----------------------- | ------------------ | ------------------------------ |
| `main`                  | 🏁 Stable code for preproduction or Production | `release/*`, `hotfix/*` | 🚀 Production      | 🔐 Protected with PR y successful CI   |
| `develop`               | 🧪 Main developing branch             | `feature/*`             | `release/*`        | ↗️ Base to continous deployment |
| `feature/*`             | ✨ New functions or refactors  to be implemented       | `develop`               | `develop`          | 🧹 Are deleted after merge to develop      |
| `release/*`             | 📦 Release preparation & final polish.      | `develop`               | `main` y `develop` | 🧪  Includes final QA. No new features added here.     |
| `bugfix/*` o `hotfix/*` | 🛠️ Critical fixes for production         | `main`                  | `main` y `develop` | ⚡ Urgent patches. Highest priority     
        |
# 🏷️ Naming Conventions
## 🌿 Branch Naming
### ✨ Feature Branches
Used for new features or non-critical improvements.
**Format:**
`feature/[shortDescription]`
**Examples:**
- `feature/authenticationModule`
- `feature/securityService`

**Rules:**
* 🧩 **Case:** strictly *camelCase* (lowercase with hyphens).
* ✍️ **Descriptive:** Short and meaningful description.

---
### 📦 Release Branches
Used for preparing a new production release. Follows [Semantic Versioning](https://semver.org/).
**Format:**
`release/v[major].[minor].[patch]`
**Examples:**
- `release/v1.0.0`
- `release/v1.1.0-beta`
---

### 🚑 Hotfix Branches
Used for urgent fixes in the production environment.
**Format:**
`hotfix/[shortDescription]`
**Examples:**
- `hotfix/fixTokenExpiration`
- `hotfix/securityPatch`
---

## 📝 Commit Message Guidelines
We follow the **[Conventional Commits](https://www.conventionalcommits.org/)** specification.

### 🧱 Standard Format
```text
<type>(<scope>): <short description>
```
# 📐 System Architecture & Design
This section provides a visual representation of the module's architecture ilustrating the base diagrams to show the application structure and components flow.

### 🧩 Context Diagram
---
Text
![Context Diagram](./docs/diagramaContexto.png)

### 🧩 Specific Components Diagram
---
This diagram visualizes the dependencies between classes for developing the module's logic. It includes the following components:
* Controllers:
    * Geolocalization Controller: This controller receives and manages all requests related to geolocation management, routes, and tracking, including references handled via DTOs.
When applying a hexagonal architecture, before developing the use cases, we need adapter components:
* Adapter:
    * Geolocalization Adapter: Contracts (interfaces) are defined based on the input received from the controllers.
    * Mapper Adapter: This adapter transforms data types from one object to another for use in the respective use cases.
* Use Cases:
    * Get Emergency Contacts Use Case: Implementation to allow passengers to share their location with emergency contacts.
    * Get Geolocation Information Use Case: Obtain position information at regular intervals.
    * Generate Automatic Alerts Use Case: Generate automatic alerts at the beginning and end of the trip.
* Ports: The following interfaces were defined as the data we will receive from the outside:
    * Port Notifications
    * Port Profiles
    * Port Travel Information
![Specific Components Diagram](./docs/diagramaComponentes.png)

### 🧩 Use Cases Diagram
---
This diagram presents the main functionalities defined by each actor. This facilitates a better understanding when implementing the module's multiple functions, as well as identifying and separating each actor's roles when using the application.
![Use Cases Diagram](./docs/diagramaCasosUso.png)

### 🧩 Class Diagram
---
Based on the Specific Components diagram, we created the class diagram, where we defined an Observer design pattern that will notify all passengers already registered on the trip, allowing them to view the current location at certain intervals, and all the information about the estimated route, the distance traveled, and so on.
![Class Diagram](./docs/diagramaClases.png)

### 🧩 Data Base Diagram
---
This diagram represents how the data is stored, where we will find the multiple documents, and the data that will be stored in an embedded or referenced manner.
![Data Base Diagram](./docs/diagramaDeBasesDeDatos.png)

### 🧩 Sequence Diagrams
---
This diagram presents the complete CRUD workflow for trip geolocation. It includes sequence diagrams for initiating, updating, and ending a trip.
The diagram also details the required validations and potential errors that may occur in each functionality. Finally, it illustrates the components involved in each process, including the Google Maps API and the relationship between them.
![Sequence Diagrams](./docs/diagramaSecuenciaGeolocalización.png)

### 🧩 Specific Deploy Diagram
---
This diagram illustrates the cloud deployment architecture and workflow of the geolocation, routes and tracking module.
![Specific Deploy Diagram](./docs/diagramaDespliegueEspecifico.png)

# 🚀 Getting Started
This section guides you through setting ip the project locally. This project requires **Java 17**. If you have a different version, you can change it or we recommend using **Docker** to ensure compatibility before compile.

### Clone & open repository
``` bash
git clone https://github.com/RIDECI/HADES_COMMUNICATION_SECURITY_BACKEND
```
``` bash
cd HADES_COMMUNICATION_SECURITY_BACKEND
```
You can open it on your favorite IDE

### Dockerize the project
Dockerize before compile the project avoid configuration issues and ensure environment consistency.
``` bash
docker compose up -d
```
### Install dependencies & compile project
Download dependencies and compile the source code.
``` bash
mvn clean install
```
``` bash
mvn clean compile
```
### To run the project
Start the Spring Boot server
``` bash
mvn spring-boot:run
```
# 🧪 Testing
Testing is a essential part of the project functionability, this part will show the code coverage and code quality analazing with tools like JaCoCo and SonarQube.

### 📊 Code Coverage (JaCoCo) and 🔎 Static Analysis (SonarQube)
---
[CoberturaSonarJacoco](./docs/documentos/Cobertura.pdf)
