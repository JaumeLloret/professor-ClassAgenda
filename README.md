# 🚀 ClassAgenda - Profesor: Jaume Lloret Enríquez

Repositorio oficial del proyecto intermodular **ClassAgenda** para el ciclo de **1º DAM**. Este proyecto sirve como base técnica y guía de referencia para la implementación de una API REST robusta, escalable y siguiendo los estándares más exigentes de la industria.

---

## 🎯 Descripción del Proyecto
**ClassAgenda** es una solución integral para la gestión académica colaborativa. Permite a los usuarios organizar **tareas** y **eventos**, integrando un sistema de permisos avanzado para compartir elementos entre usuarios con roles de lectura (`READ`) o edición (`EDIT`).

El proyecto demuestra la convergencia de:
* **Arquitectura Limpia (Clean Architecture)**: Independencia de frameworks y base de datos.
* **Diseño Relacional**: Gestión de integridad mediante PK/FK en SQL Server.
* **Integración Continua**: Automatización de calidad mediante GitHub Actions.

---

## 🏗️ Arquitectura y Tecnologías

### Stack Tecnológico
* **Lenguaje**: Java 21 (LTS).
* **Servidor**: Java `HttpServer` nativo (sin frameworks como Spring).
* **Persistencia**: Microsoft SQL Server mediante JDBC puro.
* **Gestión de Dependencias**: Maven.
* **Calidad**: JUnit 5 para tests unitarios y de integración (IT).



### Estructura del Proyecto (Clean Architecture)
El código se organiza en capas para separar responsabilidades técnicas de las reglas de negocio:
* **/presentation**: Controladores (Handlers), DTOs y validación de entrada.
* **/domain**: Modelos de negocio inmutables y contratos de repositorios (Interfaces).
* **/data**: Implementaciones JDBC, DAOs, Entidades y Mappers.
* **/shared**: Configuraciones globales, cargador de `.env` y utilidades de red.

---

## 🗄️ Base de Datos

El esquema relacional garantiza la consistencia de los datos mediante restricciones estrictas de claves primarias (PK) y foráneas (FK).



### Entidades y Relaciones
* **USERS**: Almacena el `id`, `name`, `email` (único) y fecha de creación.
* **TASKS**: Tareas con `status` (`PENDING`/`DONE`), `priority` (`LOW`/`MED`/`HIGH`) y fecha de vencimiento.
* **EVENTS**: Gestión de eventos con tipos (`EXAM`, `DELIVERY`, `CLASS`, `OTHER`) y franjas horarias.
* **SHARES**: Tablas intermedias (`TASK_SHARES`, `EVENT_SHARES`) para gestionar la colaboración N:N con permisos específicos.

---

## 🌐 API REST: Contratos y Reglas

### Identificación de Usuario
La API no implementa autenticación; el usuario activo se indica en cada petición mediante la cabecera HTTP obligatoria:
`X-User-Id: <entero>`.
*Si falta la cabecera o no es válida, el servidor responde con 400 Bad Request.*

### Resumen de Endpoints Principales
| Recurso | Método | Endpoint | Descripción |
| :--- | :--- | :--- | :--- |
| **Usuarios** | `POST` | `/users` | Crear un nuevo usuario. |
| **Tareas** | `GET` | `/tasks` | Listar tareas con filtros de `scope`, `status` y `priority`. |
| **Tareas** | `DELETE` | `/tasks/{id}` | Borrar tarea (solo permitido al propietario). |
| **Eventos** | `POST` | `/events/{id}/share` | Compartir un evento con otro usuario. |
| **Salud** | `GET` | `/health` | Comprobar el estado del servidor (sin cabecera). |

---

## ⚙️ Configuración del Entorno

### Requisitos Técnicos
1.  **Java 21** o superior.
2.  **Maven** (gestión de dependencias y ciclo de vida).
3.  **SQL Server** configurado con los scripts `01_schema.sql` y `02_seed.sql`.

### Variables de Entorno (.env)
Es necesario un archivo `.env` local para configurar la base de datos (excluido de Git):
* `CLASSAGENDA_DB_URL`: URL de conexión JDBC.
* `CLASSAGENDA_DB_USER`: Usuario de la base de datos.
* `CLASSAGENDA_DB_PASSWORD`: Contraseña de acceso.

---

## 🧪 Estrategia de Pruebas
* **Sanity Tests**: Validación inicial de que JUnit y Maven funcionan correctamente.
* **Unit Tests**: Pruebas de lógica de dominio y validaciones de modelos (ej. el nombre de usuario es obligatorio).
* **Integration Tests (IT)**: Pruebas de flujo completo que levantan el servidor y conectan a la DB real.

---

## 📈 Metodología y Calidad
* **SOLID**: Enfoque en Responsabilidad Única e Inversión de Dependencias.
* **Clean Code**: Código autodocumentado, métodos pequeños y manejo de errores con códigos estándar (`VALIDATION_ERROR`, `NOT_FOUND`, etc.).
* **Fail Fast**: Validación rigurosa de entradas para detectar errores en etapas tempranas.

---
*Repositorio mantenido por el docente **Jaume Lloret Enríquez**. Documentación sujeta a actualizaciones según el progreso del ciclo.*
