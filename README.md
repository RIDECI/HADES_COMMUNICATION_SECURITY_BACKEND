
# HADES_COMUNICATION_SECURITY_BACKEND

## Desarrolladores

* Karol Estefany Estupiñan Viancha
* Juan Andrés Suárez Fonseca
* Juan Pablo Caballero Castellanos
* Julián Santiago Ramírez Urueña
* Nicolás Andrés Duarte Rodríguez

---

## Tabla de Contenidos

* [ Estrategia de Versionamiento y Branching](#-estrategia-de-versionamiento-y-branching)

  * [ Estrategia de Ramas (Git Flow)](#-estrategia-de-ramas-git-flow)
  * [ Convenciones de Nomenclatura](#-convenciones-de-nomenclatura)
  * [ Convenciones de Commits](#-convenciones-de-commits)
* [ Arquitectura del Proyecto](#-arquitectura-del-proyecto)

  * [ Estructura de Capas](#️-estructura-de-capas)
* [ Tecnologías Utilizadas](#️-tecnologías-utilizadas)
* [ Arquitectura Limpia - Organización de Capas](#️-arquitectura-limpia---organización-de-capas)
* [Diagramas del Módulo](#diagramas-del-módulo)


---

##  Estrategia de Versionamiento y Branching

Se implementa una estrategia de versionamiento basada en **GitFlow**, garantizando un flujo de desarrollo **colaborativo, trazable y controlado**.

###  Beneficios:

- Permite trabajo paralelo sin conflictos
- Mantiene versiones estables y controladas
- Facilita correcciones urgentes (*hotfixes*)
- Proporciona un historial limpio y entendible

---

##  Estrategia de Ramas (Git Flow)

| **Rama**                | **Propósito**                            | **Recibe de**           | **Envía a**        | **Notas**                      |
| ----------------------- | ---------------------------------------- | ----------------------- | ------------------ | ------------------------------ |
| `main`                  | Código estable para PREPROD o Producción | `release/*`, `hotfix/*` | Despliegue         | Protegida con PR y CI exitoso  |
| `develop`               | Rama principal de desarrollo             | `feature/*`             | `release/*`        | Base para integración continua |
| `feature/*`             | Nuevas funcionalidades o refactors       | `develop`               | `develop`          | Se eliminan tras el merge      |
| `release/*`             | Preparación de versiones estables        | `develop`               | `main` y `develop` | Incluye pruebas finales        |
| `bugfix/*` o `hotfix/*` | Corrección de errores críticos           | `main`                  | `main` y `develop` | Parches urgentes               |

---

##  Convenciones de Nomenclatura

### Feature Branches

```
feature/[nombre-funcionalidad]-hades_[codigo-jira]
```

**Ejemplos:**

```
- feature/authentication-module-hades_23
- feature/security-service-hades_41
```

**Reglas:**

*  Formato: *kebab-case*
*  Incluir código Jira
*  Descripción breve y clara
*  Longitud máxima: 50 caracteres

---

### Release Branches

```
release/[version]
```

**Ejemplos:**

```
- release/1.0.0
- release/1.1.0-beta
```

---

### Hotfix Branches

```
hotfix/[descripcion-breve-del-fix]
```

**Ejemplos:**

```
- hotfix/fix-token-expiration
- hotfix/security-patch
```

---

## Convenciones de Commits

### Formato Estándar

```
[codigo-jira] [tipo]: [descripción breve de la acción]
```

**Ejemplos:**

```
45-feat: agregar validación de token JWT
46-fix: corregir error en autenticación por roles
```

---

### Tipos de Commit

| **Tipo**   | **Descripción**                      | **Ejemplo**                                     |
| ----------- | ------------------------------------ | ----------------------------------------------- |
| `feat`      | Nueva funcionalidad                  | `22-feat: implementar autenticación con JWT`    |
| `fix`       | Corrección de errores                | `24-fix: solucionar error en endpoint de login` |
| `docs`      | Cambios en documentación             | `25-docs: actualizar README con nuevas rutas`   |
| `refactor`  | Refactorización sin cambio funcional | `27-refactor: optimizar servicio de seguridad`  |
| `test`      | Pruebas unitarias o de integración   | `29-test: agregar tests para AuthService`       |
| `chore`     | Mantenimiento o configuración        | `30-chore: actualizar dependencias de Maven`    |


**Reglas:**

* Un commit = una acción completa
* Máximo **72 caracteres** por línea
* Usar modo imperativo (“agregar”, “corregir”, etc.)
* Descripción clara de qué y dónde
* Commits pequeños y frecuentes

---

## Arquitectura del Proyecto

El backend de **HADES_COMUNICATION_SECURITY** sigue una **arquitectura limpia y desacoplada**, priorizando:

* Separación de responsabilidades
* Mantenibilidad
* Escalabilidad
* Facilidad de pruebas

---

## Estructura de Capas

```
📂 hades_backend
 ┣ 📂 domain/
 ┃ ┣ 📄 Entities/
 ┃ ┣ 📄 ValueObjects/
 ┃ ┣ 📄 Enums/
 ┃ ┣ 📄 Services/
 ┃ ┗ 📄 Events/
 ┣ 📂 application/
 ┃ ┣ 📄 UseCases/
 ┃ ┣ 📄 DTOs/
 ┃ ┣ 📄 Mappers/
 ┃ ┗ 📄 Exceptions/
 ┣ 📂 infrastructure/
 ┃ ┣ 📄 Controllers/
 ┃ ┣ 📄 Database/
 ┃ ┣ 📄 Repositories/
 ┃ ┣ 📄 Config/
 ┃ ┗ 📄 Security/
 ┗ 📄 pom.xml
```

---

## Tecnologías Utilizadas

| **Categoría**              | **Tecnologías**                           |
| -------------------------- | ----------------------------------------- |
| **Backend**                | Java 17, Spring Boot, Maven               |
| **Base de Datos**          | MongoDB, PostgreSQL                       |
| **Infraestructura**        | Docker, Kubernetes (K8s), Railway, Vercel |
| **Seguridad**              | JWT, Spring Security                      |
| **Integración Continua**   | GitHub Actions, Jacoco, SonarQube         |
| **Documentación y Diseño** | Swagger UI, Figma                         |
| **Comunicación y Gestión** | Slack, Jira                               |
| **Testing**                | Postman                                   |

---

## Arquitectura Limpia - Organización de Capas

### DOMAIN (Dominio)

Representa el **núcleo del negocio**, define **qué hace el sistema, no cómo lo hace**.
Incluye entidades, objetos de valor, enumeraciones, interfaces de repositorio y servicios de negocio.

### APPLICATION (Aplicación)

Orquesta la lógica del negocio a través de **casos de uso**, **DTOs**, **mappers** y **excepciones personalizadas**.

### INFRASTRUCTURE (Infraestructura)

Implementa los **detalles técnicos**: controladores REST, persistencia, configuración, seguridad y conexión con servicios externos.

---

### Diagramas del Módulo

### Diagrama de Despliegue

![alt text](docs/images/DiagramaDespliegue.png)

El diagrama representa la arquitectura de despliegue del Módulo de Comunicación y Seguridad del sistema RIDECI, mostrando cómo interactúan los componentes de software, las herramientas CI/CD, la base de datos, las APIs externas y el cliente final.

CLIENTE: 

una aplicación web desarrollada en React + TypeScript, utilizada por conductores, pasajeros y administradores.

Se despliega como un artefacto web estático.

Se comunica con el backend mediante HTTPS y WebSockets (para chat en tiempo real y alertas).

MÓDULO COMUNICACIÓN Y SEGURIDAD

Este es el microservicio principal del módulo y gestiona:

- Chat en tiempo real

- Alertas de emergencia

- Alertas por desviación de ruta

- Historial de incidentes

- Reportes y calificaciones

El módulo se despliega en Railway y contiene los artefactos del backend del proyecto. Funcionalidades claves:

- Enviar y recibir mensajes mediante WebSocket.

- Activar alertas y enviar notificaciones.

- Integrarse con la API de geolocalización.

- Consultar incidentes y calificaciones desde la base de datos.

Conexiones:

- Se conecta con MongoDB mediante un driver de base de datos.

- Envía notificaciones a un servicio externo.

- Publica métricas y resultados de análisis a herramientas CI/CD.

NOTIFICATIONS (Servicio externo)

Este nodo representa el sistema externo encargado de:

- Enviar correos.

- Notificar a contactos de emergencia

- Avisar a seguridad institucional.

El backend envía hacia este sistema las alertas cuando ocurre un evento crítico.

CI/CD TOOLS

🔧 JACOCO

- Genera reportes de cobertura del código Java.

- Se ejecuta durante el pipeline.

🔧 SONARQUBE

Realiza análisis estático de calidad y seguridad del código.

- Detecta code smells, bugs y vulnerabilidades.

🔧 GITHUB ACTIONS

- Orquesta el pipeline de CI/CD.

MONGO DB

La base de datos del módulo está desplegada en un contenedor Docker con MongoDB, y almacena:

- Usuarios

- Historial del chat

- Reportes e incidentes

- Calificaciones

- Alertas

El backend se comunica con este contenedor mediante el driver de MongoDB.

GEOLOCALIZACIÓN (Maps API)

Este servicio externo provee a nuestro módulo:

- Coordenadas de ubicación en tiempo real

- Ruta planificada vs. ruta actual

- Detección de desviaciones

El backend consume esta API para activar alertas automáticas de desviación.
---

### Diagrama de casos de uso

### Diagrama de Componentes Específico
<img width="776" height="1551" alt="Casos de uso hades drawio (1)" src="https://github.com/user-attachments/assets/f66b1a18-25f5-45c8-b5ba-6c62ae7cd965" />

El diagrama representa las funcionalidades principales del Módulo de comunicación y seguridad, mostrando la interacción entre los tres tipos de actores involucrados: Pasajero, Conductor y Administrador. Cada uno accede a diferentes casos de uso según su rol dentro de la plataforma.
- **Pasajero y Conductor – Comunicación y Seguridad Operativa**
  Tanto el pasajero como el conductor pueden:
  Enviar y recibir mensajes, lo cual permite mantener coordinación antes y durante el viaje.
  Activar el botón de emergencia, asegurando una respuesta inmediata ante una situación de riesgo.
  Registrar reportes de comportamiento y calificar el viaje, contribuyendo al sistema de reputación.
  Consultar el detalle de reportes, lo que les brinda transparencia sobre incidentes en los que han sido participantes o testigos.
  Estas funcionalidades están enfocadas en mejorar la interacción, el acompañamiento seguro y la detección temprana de incidentes.
- **Administrador – Supervisión y Gestión de Incidentes**
  El administrador se encarga de supervisar la seguridad general del sistema mediante:
  Consultar el historial de reportes, para revisar el comportamiento de los usuarios.
  Atender y actualizar el estado de los reportes, gestionando los incidentes desde su recepción hasta su cierre.
  Consultar el detalle de los reportes, lo que permite profundizar en cada caso antes de tomar decisiones.
  Esto asegura la trazabilidad completa de alertas y reportes, fortaleciendo el control institucional y el seguimiento de situaciones críticas.
- **Integración entre Roles**
  El diagrama muestra cómo los casos de uso de seguridad (reportes, emergencias, calificaciones) están conectados tanto a los usuarios comunes como al administrador.
  Esto refleja que:
  - Los usuarios generan información de seguridad.
  - El administrador procesa y gestiona esa información.
  Esta relación crea un flujo continuo de supervisión y respuesta.

---

### Diagrama de Clases



---

### Diagrama de Bases de Datos



---



