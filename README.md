
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

### Diagrama de Contexto

<img width="512" height="299" alt="image" src="https://github.com/user-attachments/assets/1389eca0-7874-4f40-916d-fc48de336a03" />


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
### Diagrama de Componentes Específico

<img width="600" height="298" alt="image" src="https://github.com/user-attachments/assets/665a0c30-0801-4a8e-bcd7-61715479149b" />

El diagrama de componentes del Módulo de Comunicación y Seguridad representa la estructura interna del microservicio encargado de gestionar la interacción entre usuarios, la seguridad preventiva durante los viajes y la administración de incidentes dentro de la plataforma RidECI. Cada bloque del diagrama cumple una función específica dentro del ecosistema, y en conjunto garantizan una operación confiable, monitoreada y orientada a la protección del usuario.
**1. Casos de uso internos del microservicio**

Estos componentes representan las funcionalidades centrales del módulo:
Chat UseCase
 Gestiona la comunicación entre conductor y pasajeros antes y durante un viaje. Se encarga del envío, recepción y registro de mensajes, verificando siempre la identidad del usuario mediante el AuthAdapter.


EmergencyAlert UseCase
 Permite activar el botón de emergencia. Cuando el usuario lo presiona, este caso de uso recopila la ubicación, genera una alerta y la envía al NotificationAdapter para informar al contacto de emergencia o a la unidad institucional de seguridad.


Reputation UseCase
 Administra el sistema de calificaciones después de cada viaje. Recibe las evaluaciones y las envía a UserSecurity UseCase o a otros módulos encargados de guardar el historial reputacional del usuario.


UserSecurity UseCase
 Se encarga del historial de reportes y conducta del usuario. Almacena comportamientos, advertencias e incidentes que puedan afectar la reputación o la seguridad en la plataforma.


**2. Componentes especializados de seguridad**

Son piezas internas enfocadas en la protección activa del usuario:
RouteDeviationDetector
 Monitorea en tiempo real la ruta del viaje mediante el módulo externo de geolocalización. Si detecta una desviación significativa de la ruta esperada, genera un "Deviation Alert" que es enviado al EmergencyAlert UseCase o al NotificationAdapter según el caso.


IncidentManager
 Centraliza los reportes de incidentes generados por los usuarios o automáticamente por el sistema (por ejemplo, desvíos de ruta). También es capaz de compartir esta información con el módulo administrativo para el seguimiento institucional.


**3. Adaptadores del microservicio**

Facilitan la comunicación del módulo con otros microservicios del sistema:
AuthAdapter
 Valida la identidad de los usuarios antes de permitir chat, envío de alertas o reportes. Se conecta con el microservicio de User Management.


NotificationAdapter
 Envía notificaciones push, mensajes SMS o correos según el tipo de alerta generada. Es clave para el botón de emergencia y para avisos por desviación de ruta.


**4. Módulos externos conectados**

Estos bloques representan microservicios o funcionalidades externas que interactúan con el módulo:
User Management
 Proporciona información de los perfiles, roles y validaciones de usuarios.


Travel Management
 Ofrece la información de los viajes activos, permitiendo detectar desviaciones, enviar mensajes de chat relacionados al viaje y reportar incidentes.


Geolocalization Routes and Tracking
 Entrega la ubicación en tiempo real y la ruta planificada. Es indispensable para el RouteDeviationDetector.


Alerts (Servicio externo de alertas generales)
 Se utiliza para registrar o enviar notificaciones generales o institucionales que no están directamente vinculadas al botón de emergencia.




---
### Diagrama de casos de uso

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

El siguiente diagrama representa la arquitectura orientada a objetos del módulo de comunicación, chat, alertas y reportes de seguridad del sistema RIDECI. El modelo combina patrones de diseño como Observer, Strategy, Adapter, Factory Method y relaciones UML como composición, agregación y dependencias.

![alt text](docs/images/DiagramaClases.png)

1. Users

Representa a los usuarios del sistema (conductores y pasajeros).
Se relacionan con mensajes, alertas, reportes y viajes.

2. Mensajería (Chat)

Hay una interfaz Client, que define las operaciones del chat.
Dos implementaciones:

- PassengerGroupChat -> chat entre pasajeros.

- TripChat -> chat durante un viaje (entre pasajero y conductor).

3. Alertas

La clase Alert representa una alerta generada durante un viaje (emergencia, desvío, accidente, etc.).

La alerta usa enums:

- AlertType (EMERGENCY, ACCIDENT, etc.) -> tipo de alerta

- AlertStatus (SENT, IN_PROCESS, RESOLVED) -> estado de la alerta

4. Viajes

Viaje con estado (TripStatus).
Contiene:

- Alertas (composición)

- Chat del viaje (composición)

- Calificaciones

Es el centro donde ocurren mensajes, alertas y evaluaciones.

5. Reportes y Seguridad

Dos clases:

- Report → reporte individual.

- ReportHistory → historial que agrupa reportes.

6. Calificaciones (Rating)

Evaluaciones al finalizar un viaje.

**Patrones  de diseño**

- Observer -> Para el sistema de notificaciones (chat en tiempo real, alertas automáticas, calificaciones).

- Strategy -> Para manejar distintos tipos de alertas (Emergencia, DesviaciónRuta, accidente).

- Factory Method -> Para crear objetos de tipo Alerta o Reporte según el evento.

- Adapter -> Para integrar servicios externos (geolocalización, notificaciones, mensajería).
---

### Diagrama de Bases de Datos

<img width="387" height="463" alt="image" src="https://github.com/user-attachments/assets/3b9f2931-6040-44fb-9a0b-07ae3a847d56" />




<img width="382" height="395" alt="image" src="https://github.com/user-attachments/assets/650cda9f-5376-4640-860c-b589470b7a17" />




El diagrama de base de datos NoSQL para el módulo de Comunicación y Seguridad de RidECI representa la estructura principal de las colecciones que gestionan la interacción y protección de los usuarios durante los viajes. Se basa en dos colecciones externas (trips y users) que sirven como referencia para las funcionalidades del módulo. Las colecciones internas incluyen chats para la mensajería entre participantes, route_monitoring para el seguimiento geoespacial del trayecto, emergency_alerts para el manejo de situaciones críticas, ratings_and_reports para la evaluación de comportamiento, y user_reputatiopn para consolidar la reputación de cada usuario. El modelo utiliza documentos embebidos para almacenar mensajes y participantes dentro de los chats, así como detalles de reportes dentro de las calificaciones. También incorpora índices TTL para eliminar datos temporales y geoespaciales para el monitoreo en tiempo real. Las relaciones están claramente definidas mediante referencias y composición, lo que permite una implementación coherente, funcional y alineada con los requerimientos del sistema.





---



