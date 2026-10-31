**Sistema de Gestión de Adquisiciones y Proveedores**

Aplicación de escritorio desarrollada en **Java 17 y Swing** para administrar procesos de adquisición, proveedores, pedidos internos, órdenes de compra y adjudicaciones. Cuenta con integración de reportes mediante JasperReports y compatibilidad con SQL Server y MySQL.

&lt;p align="center"&gt;  
&lt;img src="<https://img.shields.io/badge/Java-17-orange?logo=openjdk>" /&gt;  
&lt;img src="<https://img.shields.io/badge/Maven-Build-blue?logo=apachemaven>" /&gt;  
&lt;img src="<https://img.shields.io/badge/SQL%20Server-Compatible-red?logo=microsoftsqlserver>" /&gt;  
&lt;img src="<https://img.shields.io/badge/MySQL-Compatible-blue?logo=mysql>" /&gt;  
&lt;img src="<https://img.shields.io/badge/JasperReports-7.0.3-green>" /&gt;  
&lt;/p&gt;

**Proyecto académico:** Trabajo final del curso Bases de Datos I  
**Institución:** Universidad Mariano Gálvez de Guatemala  
**Versión:** 1.0

**Tabla de contenido**

- [Descripción](https://chatgpt.com/c/6ae55b84-3380-83e8-ae78-1b65c75c2181#-descripci%C3%B3n)
- [Características principales](https://chatgpt.com/c/6ae55b84-3380-83e8-ae78-1b65c75c2181#-caracter%C3%ADsticas-principales)
- [Tecnologías](https://chatgpt.com/c/6ae55b84-3380-83e8-ae78-1b65c75c2181#-tecnolog%C3%ADas)
- [Requisitos previos](https://chatgpt.com/c/6ae55b84-3380-83e8-ae78-1b65c75c2181#-requisitos-previos)
- [Instalación](https://chatgpt.com/c/6ae55b84-3380-83e8-ae78-1b65c75c2181#-instalaci%C3%B3n)
- [Ejecución](https://chatgpt.com/c/6ae55b84-3380-83e8-ae78-1b65c75c2181#-ejecuci%C3%B3n)
- [Estructura del proyecto](https://chatgpt.com/c/6ae55b84-3380-83e8-ae78-1b65c75c2181#-estructura-del-proyecto)
- [Módulos del sistema](https://chatgpt.com/c/6ae55b84-3380-83e8-ae78-1b65c75c2181#-m%C3%B3dulos-del-sistema)
- [Reportes](https://chatgpt.com/c/6ae55b84-3380-83e8-ae78-1b65c75c2181#-reportes)
- [Base de datos](https://chatgpt.com/c/6ae55b84-3380-83e8-ae78-1b65c75c2181#-base-de-datos)
- [Seguridad](https://chatgpt.com/c/6ae55b84-3380-83e8-ae78-1b65c75c2181#-seguridad)
- [Autores](https://chatgpt.com/c/6ae55b84-3380-83e8-ae78-1b65c75c2181#-autores)
- [Licencia](https://chatgpt.com/c/6ae55b84-3380-83e8-ae78-1b65c75c2181#-licencia)
- [Agradecimientos](https://chatgpt.com/c/6ae55b84-3380-83e8-ae78-1b65c75c2181#-agradecimientos)

**Descripción**

El Sistema de Gestión de Adquisiciones y Proveedores es una aplicación de escritorio desarrollada como proyecto académico para el curso Bases de Datos I de la Universidad Mariano Gálvez de Guatemala.

Su propósito es facilitar la administración de los procesos de compra de una organización mediante una plataforma que centraliza la información de sucursales, departamentos, artículos, proveedores, pedidos internos, órdenes de compra y adjudicaciones.

El sistema utiliza una arquitectura organizada por capas y permite seleccionar entre dos sistemas gestores de bases de datos desde la pantalla de inicio de sesión.

Además, incorpora autenticación mediante BCrypt y generación de reportes utilizando JasperReports.

**Características principales**

- **Autenticación segura:** Inicio de sesión con contraseñas protegidas mediante BCrypt.
- **Control de acceso:** Cuatro roles con diferentes permisos.
- **Gestión de sucursales:** Administración de las sucursales de la organización.
- **Gestión de departamentos:** Registro de departamentos asociados a sucursales.
- **Gestión de artículos:** Administración del catálogo de productos.
- **Gestión de proveedores:** Registro y administración de proveedores.
- **Pedidos internos:** Registro y seguimiento de solicitudes.
- **Órdenes de compra:** Administración de órdenes de adquisición.
- **Adjudicaciones:** Registro de resoluciones y proveedores seleccionados.
- **Reportes:** Ocho informes desarrollados con JasperReports.
- **Doble gestor de base de datos:** Compatibilidad con SQL Server y MySQL.
- **Operaciones CRUD:** Crear, consultar, actualizar y eliminar registros.

**Tecnologías**

| **Tecnología** | **Versión**         | **Utilización**              |
| -------------- | ------------------- | ---------------------------- |
| Java           | 17                  | Desarrollo de la aplicación. |
| Java Swing     | JDK 17              | Interfaz gráfica.            |
| JDBC           | API de Java         | Conexión a bases de datos.   |
| JasperReports  | 7.0.3               | Generación de reportes.      |
| JRViewer       | JasperReports 7.0.3 | Visualización de informes.   |
| BCrypt         | Según dependencia   | Seguridad de contraseñas.    |
| Maven          | Según entorno       | Gestión de dependencias.     |
| SQL Server     | Según instalación   | Gestor de base de datos.     |
| MySQL          | Según instalación   | Gestor de base de datos.     |

**Requisitos previos**

Antes de instalar el proyecto es necesario contar con:

- JDK 17.
- Apache Maven.
- Microsoft SQL Server o MySQL.
- Controlador JDBC de SQL Server.
- MySQL Connector/J.
- Conexión al servidor de base de datos.
- Jaspersoft Studio (opcional, para modificar los diseños de reportes).
- Git (para clonar el repositorio).

**Instalación**

**1\. Clonar el repositorio**

Ejecutar el siguiente comando:

git clone <https://github.com/USUARIO/SistemaCompras.git>

Ingresar a la carpeta:

cd SistemaCompras

Sustituir la dirección por la URL real del repositorio.

**2\. Crear la base de datos**

El proyecto incluye scripts para los dos gestores.

**Para SQL Server:**

1. Abrir SQL Server Management Studio.
2. Conectarse al servidor.
3. Abrir el script DDL de SQL Server.
4. Ejecutar el script para crear la estructura de la base de datos.
5. Ejecutar el script DML para insertar los datos de prueba.

**Para MySQL:**

1. Abrir MySQL Workbench.
2. Conectarse al servidor.
3. Abrir el script DDL de MySQL.
4. Ejecutar el script de creación.
5. Ejecutar el script DML correspondiente.

**3\. Configurar config.properties**

Localizar el archivo de configuración y establecer los parámetros de conexión.

Ejemplo:

gestor.activo=SQL_SERVER

\# SQL Server

sqlserver.url=jdbc:sqlserver://localhost:1433;databaseName=SistemaCompras;encrypt=true;trustServerCertificate=true

sqlserver.user=sa

sqlserver.password=TU_PASSWORD

\# MySQL

mysql.url=jdbc:mysql://localhost:3306/SistemaCompras

mysql.user=root

mysql.password=TU_PASSWORD

Modificar los valores de acuerdo con el entorno local.

**4\. Compilar el proyecto**

Desde la carpeta raíz:

mvn clean package

Maven descargará las dependencias necesarias, compilará el proyecto y generará el paquete.

**5\. Ejecutar la aplicación**

Una vez compilado el proyecto, ejecutar el archivo JAR generado:

java -jar target/SistemaCompras.jar

El nombre del archivo puede variar según la configuración del proyecto.

**Ejecución**

Al iniciar la aplicación se mostrará la pantalla de inicio de sesión.

El usuario deberá:

1. Seleccionar el gestor de base de datos.
2. Introducir su nombre de usuario.
3. Introducir su contraseña.
4. Presionar el botón **Iniciar sesión**.

Si las credenciales son válidas, el sistema mostrará el menú correspondiente al rol del usuario.

**Credenciales de prueba**

Las siguientes credenciales son ejemplos propuestos para la configuración inicial. Deben existir previamente en la base de datos y tener sus contraseñas almacenadas con hashes BCrypt válidos.

| **Usuario** | **Contraseña de ejemplo** | **Rol**                    |
| ----------- | ------------------------- | -------------------------- |
| admin       | admin123                  | Administrador              |
| gestor      | admin123                  | Gestor de Compras          |
| proveedor   | admin123                  | Administrador de Proveedor |
| auditor     | admin123                  | Auditor                    |

**Importante:** Estas credenciales no deben considerarse cuentas verificadas del proyecto. Deben configurarse mediante el script de datos iniciales o desde el módulo de usuarios.

**Estructura del proyecto**

SistemaCompras/

│

├── src/

│ ├── main/

│ │ ├── java/

│ │ │ └── com/sistemacompras/

│ │ │ │

│ │ │ ├── datos/

│ │ │ │ ├── IConexionBD.java

│ │ │ │ ├── ConexionSQLServer.java

│ │ │ │ ├── ConexionMySQL.java

│ │ │ │ ├── ConexionFactory.java

│ │ │ │ └── Configuracion.java

│ │ │ │

│ │ │ ├── entidades/

│ │ │ │ └── POJOs

│ │ │ │

│ │ │ ├── repositorios/

│ │ │ │ └── CRUD y reportes

│ │ │ │

│ │ │ ├── negocio/

│ │ │ │ ├── LoginService.java

│ │ │ │ ├── UsuarioService.java

│ │ │ │ └── PasswordUtil.java

│ │ │ │

│ │ │ └── ui/

│ │ │ └── reportes/

│ │ │

│ │ └── resources/

│ │ ├── config.properties

│ │ └── reportes/

│ │ └── Archivos JRXML

│ │

│ └── test/

│

├── database/

│ ├── sqlserver/

│ ├── mysql/

│ └── datos_prueba.sql

│

├── pom.xml

└── README.md

**Módulos del sistema**

| **Módulo**        | **Descripción**                                                    |
| ----------------- | ------------------------------------------------------------------ |
| Login             | Autenticación de usuarios y selección del gestor de base de datos. |
| Sucursales        | Administración de sucursales.                                      |
| Departamentos     | Gestión de departamentos organizacionales.                         |
| Artículos         | Administración del catálogo de artículos.                          |
| Proveedores       | Registro y administración de proveedores.                          |
| Pedidos Internos  | Registro de solicitudes de artículos.                              |
| Órdenes de Compra | Administración de órdenes de adquisición.                          |
| Adjudicaciones    | Registro de resoluciones y asignaciones de compra.                 |
| Usuarios          | Administración de cuentas y roles.                                 |

**Reportes**

El sistema incorpora ocho reportes desarrollados con JasperReports 7.0.3 y visualizados mediante JRViewer.

| **N.º** | **Reporte**                       | **Descripción**                                                          |
| ------- | --------------------------------- | ------------------------------------------------------------------------ |
| 1       | Análisis de Ofertas por Orden     | Compara las ofertas de proveedores para una orden.                       |
| 2       | Eficiencia del Proceso de Compra  | Analiza el rendimiento de los procesos de adquisición.                   |
| 3       | Gasto Departamental               | Presenta los gastos por departamento.                                    |
| 4       | Historial de Compras por Artículo | Muestra el historial de adquisiciones de artículos.                      |
| 5       | Órdenes Activas                   | Presenta las órdenes de compra vigentes.                                 |
| 6       | Pedidos Pendientes                | Identifica solicitudes que aún no han sido atendidas.                    |
| 7       | Top 5 Proveedores                 | Presenta los cinco proveedores destacados según el criterio del informe. |
| 8       | Variación de Precios              | Analiza los cambios de precios registrados.                              |

Los parámetros específicos dependen del diseño y las consultas de cada reporte.

**Base de datos**

El proyecto utiliza un modelo relacional normalizado hasta la Tercera Forma Normal (3FN), compuesto por 19 tablas.

**Características del modelo**

- Claves primarias.
- Claves foráneas.
- Restricciones UNIQUE.
- Restricciones CHECK.
- Relaciones uno a muchos.
- Relaciones muchos a muchos.
- Tablas intermedias.
- Integridad referencial.

**Sistemas gestores compatibles**

**Microsoft SQL Server**

Utiliza JDBC mediante el controlador oficial de Microsoft.

**MySQL**

Utiliza JDBC mediante MySQL Connector/J.

La selección del gestor se realiza desde la pantalla de inicio de sesión mediante el patrón Factory implementado en ConexionFactory.

**Modelo entidad-relación**

\[Insertar imagen del Modelo Entidad-Relación (MER)\]

**Seguridad**

El sistema incorpora mecanismos de seguridad para controlar el acceso a la información.

**Autenticación**

Las contraseñas se procesan utilizando BCrypt, evitando almacenarlas directamente en texto plano.

**Roles disponibles**

| **Rol**                    | **Descripción**                     |
| -------------------------- | ----------------------------------- |
| Administrador              | Administración general.             |
| Gestor de Compras          | Gestión de adquisiciones.           |
| Administrador de Proveedor | Administración de proveedores.      |
| Auditor                    | Consulta y revisión de información. |

**Control de permisos**

La tabla Permiso permite establecer los permisos de cada rol sobre las diferentes pantallas del sistema.

Las operaciones contempladas son:

- Crear.
- Leer.
- Actualizar.
- Borrar.

**Autores**

Proyecto desarrollado por estudiantes de la Universidad Mariano Gálvez de Guatemala.

**Integrantes:**

- [Nombre del integrante 1](https://github.com/USUARIO1)
- [Nombre del integrante 2](https://github.com/USUARIO2)
- [Nombre del integrante 3](https://github.com/USUARIO3)

_Reemplazar los nombres y enlaces por los datos reales del equipo._

**Licencia**

Este proyecto fue desarrollado con fines académicos para el curso Bases de Datos I de la Universidad Mariano Gálvez de Guatemala.

La licencia de distribución debe definirse por los autores antes de su publicación.

**Agradecimientos**

Agradecemos a la Universidad Mariano Gálvez de Guatemala y al docente del curso Bases de Datos I por los conocimientos y orientación proporcionados durante el desarrollo del proyecto.

También agradecemos a las comunidades y proyectos de software libre que proporcionan las herramientas utilizadas en el desarrollo de esta aplicación.

&lt;p align="center"&gt;  
\*\*Sistema de Gestión de Adquisiciones y Proveedores\*\*

Desarrollado como proyecto académico.

Universidad Mariano Gálvez de Guatemala

&lt;/p&gt;