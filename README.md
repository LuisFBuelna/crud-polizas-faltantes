# Plantilla Framework Spring Boot

---

## Propiedades (application.yml)

Este archivo corresponde a la configuración del servicio y se compone de pocos apartados.

#### Servidor, Puerto y Contexto

La configuración inicial del servicio corresponde al puerto por default que servirá la aplicación (8080), y el contexto que se usará para acceder a los endpoints de la API.

```yaml
server:
  servlet:
    context-path: /api/v1
  port: 8080
```

#### Integración con SSO

Pensando en `Desarrollo`, al principio se ignora la validación de un token de autenticación.

Es por ello que la variable `app.ignoreSession = true`  por default. Este valor debe cambiarse a `false` en entornos productivos. Una vez puesta en `app.ignoreSession = false`, el servicio validará el token de autenticación para cada una de las peticiones y lo hará por medio del servicio indicado en la propiedad `app.authUri`.

```yaml
app:
  authUri: https://sso.coppel.io:50061/api/v1/verify
  ignoreSession: true
```

#### Database (Spring Boot DataSource)

En este apartado se definen las variables (propiedades) correspondientes a conexiones con bases de datos.

**Nota sobre H2:**

La plantilla incluye la dependencia con `H2`, una base de datos en memoria usada ampliamente para crear `mocks` o datos ficticios. Aunado a esta base de datos se incluye un archivo `resources/data.sql` con una definición de tablas y carga inicial de datos.

**Para BD institucionales:**

Suponiendo que nos conectaremos a una base de datos de Postgres, los valores para crear la conexión a la base de datos, serían definidos de la siguiente manera:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://local.coppel.io:5432/test?currentSchema=public&ApplicationName=nom_aplicacion&socketTimeout=30
    driver-class-name: org.postgresql.Driver
    username: sa
    password: 
```

Para crear una conexión con MSSQL Server:

```yaml
spring:
  datasource:
    url: jdbc:sqlserver://local.coppel.io:1433;databaseName=test;applicationName=nom_aplicacion;socketTimeout=30000
    driver-class-name: om.microsoft.sqlserver.jdbc.SQLServerDriver
    username: sa
    password: 
```

Como puede verse en ambos ejemplos, es importante indicar el `applicationName` para monitorear desde la base de datos el número de conexiones creadas (y mantenidas) por el pool de conexiones de la aplicación.

Puedes usar los siguientes comandos, dependiendo del motor de base de datos que estés usando:

**Postgres:**

```sql
SELECT now(), * 
FROM pg_stat_activity 
WHERE application_name = 'nom_aplicacion'
```

**MSSQL Server:**

```sql
SELECT c.session_id
    , c.net_transport
    , c.encrypt_option
    , c.auth_scheme
    , s.host_name
    , s.program_name
    , s.client_interface_name
    , s.login_name
    , c.connect_time
    , s.login_time
FROM sys.dm_exec_connections AS c JOIN sys.dm_exec_sessions AS s ON c.session_id = s.session_id 
WHERE s.program_name = 'nom_aplicacion'
```

**Nota importante:**

Las credenciales de la BD se incluyen en el archivo de configuración para soportar el ejemplo y facilitar el desarrollo.

Pero en Master (o Main) y en entornos productivos, es importante exlcuir las credenciales de bases de datos. Estas credenciales deben ser proporcionadas por el equipo de operación durante el despliegue del servicio, como una buena práctica de seguridad y para que las credenciales de bases de datos productivas queden como parte de la configuración.

#### Hikari - Connection Pool

A partir de la versión 2 de Spring Boot, se incluye este pool de conexiones.

Es recomendable usar un pool de conexiones por varias razones, en lugar de usar clases personalizadas para el manejo de conexiones a bases de datos.

La configuración de Hikari en el archivo se lleva a cabo por las siguientes variables:

```yaml
hikari:
    minimumIdle: 4
    maximumPoolSize: 8
    idleTimeout: 15000
    poolName: SpringBootJPAHikariCP
    maxLifetime: 1800000
    connectionTimeout: 30000
```

Los valores para cada una de las propiedades fueron elegidos en base a los entornos comunes en `Grupo Coppel`, y a la [documentación oficial de Hikari](https://github.com/brettwooldridge/HikariCP).

#### Notas sobre el uso de YAML en el archivo de configuración `application.yml`

A partir de la versión 2.7 se reemplaza el archivo `application.properties` por su equivalente en formato YAML: `application.yml`. El primer archivo seguirá siendo compatible (`application.properties`). Simplemente se cambia para mejorar la facilidad de lectura debido a que en algunas implementaciones el manejo de variables resulta ser demasiado verboso.

Si deseas conocer o aumentar tus conocimientos sobre este lenguaje de serialización de datos, te recomendamos visitar 2 sitios.

- [¿Qué es YAML?](https://www.redhat.com/es/topics/automation/what-is-yaml)
- [YAML for beginners](https://www.redhat.com/sysadmin/yaml-beginners)

---

## Cambios (09/08/2022)

#### Actualización a la versión 2.7.2 de Spring Boot

#### Corrección de `codesmells` detectados por Sonarqube con respecto a la versión de Java 11.

Algunos de los `codesmells` continúan existiendo. Unos para mantener compatiblidad con versiones anteriores de Java, y otros para mantener la legibilidad del código.

#### Implementación de Sonar Scanner para Maven

Con este cambio se elimina la dependencia con el plugin de Jacoco, con el objetivo de dar soporte a las clases creadas con la biblioteca Project Lombok, y se transfiere el escaneo al plugin de Maven incluido en el proyecto.

En el archivo `pom.xml` se incluyen las propiedades de Sonar que se incluían en el archivo `sonar-project.properties`. En lo sucesivo deben actualizarse las variables siguientes en `pom.xml` con el objetivo de realizar el escaneo:

```xml
<properties>
    <java.version>11</java.version>
    <sonar.host.url>https://sonarqube.coppel.io/sonar</sonar.host.url>
    <sonar.projectKey>centro:folio</sonar.projectKey>
    <sonar.projectName>nombre</sonar.projectName>
    <sonar.projectVersion>1.0.0</sonar.projectVersion>
    <sonar.sources>./src/main</sonar.sources>
    <sonar.exclusions>./src/test</sonar.exclusions>
    <sonar.java.binaries>./src</sonar.java.binaries>
    <sonar.java.libraries>./target/dependency/*.jar</sonar.java.libraries>
</properties>
```

Una vez actualizadas las variables, el escaneo debe ejecutarse con el siguiente comando:

```terminal
<Carpeta principal del proyecto>  mvn clean verify sonar:sonar
```# crud-polizas-faltantes
