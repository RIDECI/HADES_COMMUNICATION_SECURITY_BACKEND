# HADES_COMUNICATION_SECURITY_BACKEND

**Integrantes:**

- Karol Estafany Estupiñan Viancha.

- Juan Andres Suarez Fonseca.

- Juan Pablo Caballero Castellanos.

- Julian Santiago Ramirez Urueña.

---

## Estrategia de Versionamiento y ramas.

**Template ramas**

- main: Versión estable para PREPROD
- develop: Rama principal de desarrollo
- bugix/*: Manejo de errores
- release/*: Manejo de versiones.

---

**Template Commits**
`feature: Tarea - Acción Realizada`

---

## Tecnologías utilizadas

- MongoBD
- Railway
- Vercel
- Java 17
- TypeScript
- Jacoco
- SonarQube
- Swagger UI
- Docker
- Sprint Boot
- Maven
- Kubernetes k8
- GitHub Actions
- Postman
- Figma
- Slack
- Jira
- JWT
- Postgresql

--- 

## Arquitectura Limpia

DOMAIN (Dominio)

Representa el núcleo del negocio y contiene los conceptos más importantes de la aplicación.
En esta capa se define qué hace el sistema, no cómo lo hace.

Contiene:

- Entities: Clases que representan los objetos principales del negocio y sus invariantes.

- Value Objects: Objetos de valor que encapsulan reglas específicas (como Email).

- Enums: Enumeraciones propias del dominio (por ejemplo: tipo usuario, tipo de  pago).

- Repositories (interfaces): Contratos abstractos que definen cómo se accederá a los datos sin depender de una base concreta.

- Services: Reglas de negocio que involucran múltiples entidades o procesos complejos.

- Events: Eventos que representan sucesos importantes dentro del dominio (por ejemplo: enviar alerta).

---

APPLICATION (Aplicación)

Encapsula la lógica de aplicación y los casos de uso.
Define cómo se usa el dominio para resolver un problema o ejecutar una acción.

Contiene:

Use Cases: Casos de uso que orquestan el flujo de la aplicación.

DTOs (Data Transfer Objects): Objetos de transferencia de datos usados entre las capas.

Mappers: Convertidores entre entidades y DTOs.

Exceptions: Excepciones personalizadas que representan errores del negocio o de la aplicación.

---

INFRASTRUCTURE (Infraestructura)

Implementa los detalles técnicos que permiten que el sistema funcione.
Aquí se manejan aspectos de persistencia, comunicación externa, seguridad y configuración.

Contiene:

API / Controllers: Endpoints REST que reciben las solicitudes del usuario, llaman los casos de uso y devuelven las respuestas.

Database: Configuración de la base de datos y sus modelos concretos (por ejemplo, anotaciones de JPA o documentos de MongoDB).

Repositories: Implementaciones concretas de las interfaces del dominio.

Config: Clases de configuración general del sistema (CORS, beans, seguridad, etc.).