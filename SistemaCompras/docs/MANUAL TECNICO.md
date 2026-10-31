**MANUAL TECNICO**

**Sistema de Gestión de Adquisiciones y Proveedores**

**Versión:** 1.0  
**Fecha:** 30 de octubre de 2026  
**Proyecto:** Sistema de Gestión de Adquisiciones y Proveedores  
**Institución:** Universidad Mariano Gálvez de Guatemala  
**Curso:** Bases de Datos I **Desarrolladores:** Edison Abadí De la Cruz Hernández Marvin Alberto Jovany Lux Martínez Kilian Saul Jacinto Osorio Brandon Ediberto Galeano Sis Sebastián Nehemías Gonzales Ordoñez

**Indice**

**MANUAL TECNICO** 1

**Sistema de Gestión de Adquisiciones y Proveedores** 1

[1\. Introducción 4](#1-introducción)

[2\. Propósito del documento 5](#2-propósito-del-documento)

[3\. Arquitectura general 5](#3-arquitectura-general)

[3.1 Diagrama de arquitectura 5](#31-diagrama-de-arquitectura)

[3.2 Capa de presentación 6](#32-capa-de-presentación)

[3.3 Capa de negocio 6](#33-capa-de-negocio)

[3.4 Capa de datos 6](#34-capa-de-datos)

[4\. Tecnologías y dependencias 7](#4-tecnologías-y-dependencias)

[4.1 Dependencias Maven 7](#41-dependencias-maven)

[5\. Modelo de datos 8](#5-modelo-de-datos)

[5.1 Descripción general 8](#51-descripción-general)

[5.2 Tablas principales 8](#52-tablas-principales)

[5.3 Columnas principales 9](#53-columnas-principales)

[5.4 Relaciones entre tablas 10](#54-relaciones-entre-tablas)

[5.5 Relaciones muchos a muchos 10](#55-relaciones-muchos-a-muchos)

[6\. Estructura del proyecto 11](#6-estructura-del-proyecto)

[6.1 Paquete datos 12](#61-paquete-datos)

[6.2 Paquete entidades 12](#62-paquete-entidades)

[6.3 Paquete repositorios 12](#63-paquete-repositorios)

[6.4 Paquete negocio 12](#64-paquete-negocio)

[6.5 Paquete ui 13](#65-paquete-ui)

[6.6 Paquete ui.reportes 13](#66-paquete-uireportes)

[7\. Configuración de conexión a bases de datos 13](#7-configuración-de-conexión-a-bases-de-datos)

[7.1 Archivo config.properties 13](#71-archivo-configproperties)

[7.2 Interfaz IConexionBD 13](#72-interfaz-iconexionbd)

[7.3 Patrón Factory 14](#73-patrón-factory)

[7.4 Selección del gestor en el login 14](#74-selección-del-gestor-en-el-login)

[8\. Seguridad 15](#8-seguridad)

[8.1 Autenticación 15](#81-autenticación)

[8.2 BCrypt 15](#82-bcrypt)

[8.3 Roles 16](#83-roles)

[8.4 Tabla Permiso 16](#84-tabla-permiso)

[9\. Generación de reportes 16](#9-generación-de-reportes)

[9.1 JasperReports 16](#91-jasperreports)

[9.2 Ubicación de los reportes 16](#92-ubicación-de-los-reportes)

[9.3 Proceso de generación 17](#93-proceso-de-generación)

[9.4 Reportes integrados 17](#94-reportes-integrados)

[9.5 Parámetros 17](#95-parámetros)

[10\. Compatibilidad con dos SGBD 18](#10-compatibilidad-con-dos-sgbd)

[10.1 Diferencias principales 18](#101-diferencias-principales)

[10.2 Adaptación de consultas 18](#102-adaptación-de-consultas)

[10.3 Reportes 18](#103-reportes)

[11\. Compilación y despliegue 18](#11-compilación-y-despliegue)

[11.1 Requisitos 18](#111-requisitos)

[11.2 Compilación 19](#112-compilación)

[11.3 Ubicación del JAR 19](#113-ubicación-del-jar)

[11.4 Ejecución 19](#114-ejecución)

[11.5 Generación de Fat JAR 19](#115-generación-de-fat-jar)

[12\. Pruebas del sistema 20](#12-pruebas-del-sistema)

[12.1 Pruebas de autenticación 20](#121-pruebas-de-autenticación)

[12.2 Pruebas CRUD 20](#122-pruebas-crud)

[12.3 Pruebas de reportes 21](#123-pruebas-de-reportes)

[12.4 Pruebas de integridad 21](#124-pruebas-de-integridad)

[13\. Mantenimiento y extensión 21](#13-mantenimiento-y-extensión)

[13.1 Agregar un nuevo módulo CRUD 21](#131-agregar-un-nuevo-módulo-crud)

[13.2 Agregar un nuevo reporte 22](#132-agregar-un-nuevo-reporte)

[13.3 Recomendaciones de mantenimiento 22](#133-recomendaciones-de-mantenimiento)

## 1. Introducción

El Sistema de Gestión de Adquisiciones y Proveedores es una aplicación de escritorio desarrollada en Java 17 utilizando Swing para la construcción de interfaces gráficas.

Su objetivo es administrar los procesos de adquisición de productos, gestionar proveedores, registrar pedidos internos, controlar órdenes de compra y documentar adjudicaciones.

La aplicación utiliza JDBC para acceder a bases de datos relacionales y permite trabajar con Microsoft SQL Server y MySQL.

Adicionalmente, incorpora JasperReports para la generación de informes y BCrypt para el almacenamiento seguro de contraseñas.

## 2. Propósito del documento

Este manual describe los componentes técnicos del sistema, su arquitectura, organización interna, modelo de datos, mecanismos de seguridad y procedimientos de compilación y despliegue.

Está dirigido principalmente a:

- Desarrolladores Java.
- Administradores de bases de datos.
- Personal de mantenimiento.
- Responsables de instalación y configuración.

## 3. Arquitectura general

El sistema utiliza una arquitectura organizada por capas, separando las responsabilidades de acceso a datos, lógica de negocio e interfaz gráfica.

### 3.1 Diagrama de arquitectura

+---------------------------------------+

| CAPA DE PRESENTACIÓN |

| |

| com.sistemacompras.ui |

| com.sistemacompras.ui.reportes |

| |

| Java Swing + CardLayout |

| JasperReports + JRViewer |

+-------------------+-------------------+

|

v

+---------------------------------------+

| CAPA DE NEGOCIO |

| |

| com.sistemacompras.negocio |

| |

| LoginService |

| UsuarioService |

| PasswordUtil |

+-------------------+-------------------+

|

v

+---------------------------------------+

| CAPA DE DATOS |

| |

| com.sistemacompras.datos |

| com.sistemacompras.repositorios |

| com.sistemacompras.entidades |

| |

| JDBC + Patrón Factory |

+-------------------+-------------------+

|

+------+------+

| |

v v

+-----------+ +-----------+

| SQL Server| | MySQL |

+-----------+ +-----------+

### 3.2 Capa de presentación

Es responsable de la interacción con el usuario.

Está desarrollada utilizando Java Swing y organiza las pantallas mediante CardLayout.

Sus responsabilidades son:

- Mostrar formularios.
- Capturar información.
- Validar datos de entrada.
- Presentar resultados.
- Mostrar mensajes de confirmación y error.
- Facilitar la navegación entre módulos.

### 3.3 Capa de negocio

Contiene las reglas y procesos principales del sistema.

Entre sus componentes se encuentran:

- LoginService: administra la autenticación.
- UsuarioService: gestiona las operaciones relacionadas con usuarios.
- PasswordUtil: proporciona funcionalidades para el procesamiento de contraseñas mediante BCrypt.

### 3.4 Capa de datos

Se encarga de establecer conexiones y ejecutar operaciones sobre las bases de datos.

Incluye:

- Interfaces de conexión.
- Implementaciones específicas para cada gestor.
- Factoría de conexiones.
- Entidades.
- Repositorios CRUD.
- Repositorios de reportes.

# 4. Tecnologías y dependencias

| **Tecnología** | **Versión**             | **Propósito**                          |
| -------------- | ----------------------- | -------------------------------------- |
| Java           | 17                      | Lenguaje principal.                    |
| Swing          | Incluido en JDK 17      | Interfaces gráficas.                   |
| JDBC           | API de Java             | Acceso a bases de datos.               |
| JasperReports  | 7.0.3                   | Generación de reportes.                |
| BCrypt         | Según dependencia Maven | Protección de contraseñas.             |
| Maven          | Según entorno instalado | Gestión de dependencias y compilación. |
| SQL Server     | Según instalación       | Primer gestor de bases de datos.       |
| MySQL          | Según instalación       | Segundo gestor de bases de datos.      |
| JRViewer       | JasperReports 7.0.3     | Visualización de informes.             |

## 4.1 Dependencias Maven

Las dependencias deben declararse en el archivo pom.xml.

Ejemplo de dependencias principales:

&lt;dependencies&gt;

&lt;!-- SQL Server JDBC --&gt;

&lt;dependency&gt;

&lt;groupId&gt;com.microsoft.sqlserver&lt;/groupId&gt;

&lt;artifactId&gt;mssql-jdbc&lt;/artifactId&gt;

&lt;version&gt;12.10.0.jre11&lt;/version&gt;

&lt;/dependency&gt;

&lt;!-- MySQL JDBC --&gt;

&lt;dependency&gt;

&lt;groupId&gt;com.mysql&lt;/groupId&gt;

&lt;artifactId&gt;mysql-connector-j&lt;/artifactId&gt;

&lt;version&gt;9.4.0&lt;/version&gt;

&lt;/dependency&gt;

&lt;!-- JasperReports --&gt;

&lt;dependency&gt;

&lt;groupId&gt;net.sf.jasperreports&lt;/groupId&gt;

&lt;artifactId&gt;jasperreports&lt;/artifactId&gt;

&lt;version&gt;7.0.3&lt;/version&gt;

&lt;/dependency&gt;

&lt;!-- BCrypt --&gt;

&lt;dependency&gt;

&lt;groupId&gt;org.mindrot&lt;/groupId&gt;

&lt;artifactId&gt;jbcrypt&lt;/artifactId&gt;

&lt;version&gt;0.4&lt;/version&gt;

&lt;/dependency&gt;

&lt;/dependencies&gt;

**Nota:** Las versiones mostradas son referencias de configuración. Deben coincidir con las dependencias reales del proyecto y con sus requisitos de compatibilidad.

# 5. Modelo de datos

### 5.1 Descripción general

La base de datos del sistema está diseñada mediante un modelo relacional compuesto por 19 tablas.

El diseño busca mantener la integridad de la información y reducir la redundancia mediante normalización hasta la Tercera Forma Normal (3FN).

Se utilizan:

- Claves primarias (PK).
- Claves foráneas (FK).
- Restricciones UNIQUE.
- Restricciones CHECK.
- Relaciones uno a muchos.
- Relaciones muchos a muchos.

### 5.2 Tablas principales

| **N.º** | **Tabla**            |     | **Descripción**                                    |
| ------- | -------------------- | --- | -------------------------------------------------- |
| 1       | Sucursal             |     | Almacena las sucursales de la organización.        |
| 2       | Departamento         |     | Registra departamentos asociados a sucursales.     |
| 3       | Articulo             |     | Contiene el catálogo de artículos.                 |
| 4       | Categoria            |     | Clasifica los artículos o proveedores.             |
| 5       | Proveedor            |     | Almacena información de proveedores.               |
| 6       | Proveedor_Articulo   |     | Relaciona proveedores con artículos y sus precios. |
| 7       | Proveedor_Categoria  |     | Relaciona proveedores con categorías.              |
| 8       | Relacion_Proveedor   |     | Registra relaciones entre proveedores.             |
| 9       | Pedido_Interno       |     | Registra solicitudes internas de artículos.        |
| 10      | Oferta_Proveedor     |     | Almacena ofertas presentadas por proveedores.      |
| 11      | Orden_Compra         |     | Registra órdenes de compra.                        |
| 12      | Orden_Pedido         |     | Relaciona órdenes con pedidos internos.            |
| 13      | Adjudicacion         |     | Registra resoluciones de órdenes de compra.        |
| 14      | Adjudicacion_Detalle |     | Almacena los detalles de cada adjudicación.        |
| 15      | Rol                  |     | Define los roles del sistema.                      |
| 16      | Pantalla             |     | Registra las pantallas disponibles.                |
| 17      | Permiso              |     | Establece los permisos por rol y pantalla.         |
| 18      | Sucursal_Telefono    |     | Registra teléfonos asociados a sucursales.         |
| 19      | Usuario              |     | Almacena las cuentas de acceso al sistema.         |

### 5.3 Columnas principales

| **Tabla**            | **Columnas principales**                                                       |
| -------------------- | ------------------------------------------------------------------------------ |
| Sucursal             | SucursalID, Codigo, Direccion, Ciudad, Departamento                            |
| Departamento         | DepartamentoID, SucursalID, nombre, descripcion                                |
| Articulo             | ArticuloID, codigo, nombre, descripcion                                        |
| Categoria            | CategoriaID, nombre, descripcion                                               |
| Proveedor            | ProveedorID, nit, nombreComercial, Direccion, telefono                         |
| Proveedor_Articulo   | ProveedorID, ArticuloID, precio                                                |
| Proveedor_Categoria  | ProveedorID, CategoriaID                                                       |
| Relacion_Proveedor   | ProveedorID1, ProveedorID2, tipoRelacion                                       |
| Pedido_Interno       | PedidoID, DepartamentoID, ArticuloID, cantidad, fechaSolicitud, fechaNecesidad |
| Oferta_Proveedor     | OfertaID, ProveedorID, PedidoID, precioUnitario, fechaOferta                   |
| Orden_Compra         | OrdenID, descripcion, fechaCreacion, fechaLimite, tipoOrden, subtipoOrden      |
| Orden_Pedido         | OrdenID, PedidoID                                                              |
| Adjudicacion         | AdjudicacionID, OrdenID, fechaResolucion                                       |
| Adjudicacion_Detalle | AdjudicacionID, PedidoID, ProveedorID, cantidadFinal, precioAcordado           |
| Rol                  | RolID, nombre, descripcion                                                     |
| Pantalla             | PantallaID, nombre, descripcion                                                |
| Permiso              | RolID, PantallaID, permiteCrear, permiteLeer, permiteActualizar, permiteBorrar |
| Sucursal_Telefono    | SucursalID, telefono                                                           |
| Usuario              | UsuarioID y demás campos definidos en el DDL del proyecto                      |

**Importante:** Para documentar completamente los campos de Usuario y sus tipos de datos, debe consultarse el script DDL definitivo.

### 5.4 Relaciones entre tablas

Las relaciones principales son:

- Una sucursal puede tener varios departamentos.
- Un departamento puede generar varios pedidos internos.
- Un artículo puede aparecer en varios pedidos internos.
- Un proveedor puede presentar varias ofertas.
- Una orden de compra puede relacionarse con varios pedidos.
- Una adjudicación puede contener varios detalles.
- Un rol puede tener permisos sobre diferentes pantallas.

### 5.5 Relaciones muchos a muchos

Se utilizan tablas intermedias para representar relaciones N.

**Proveedor_Articulo**

Relaciona proveedores con artículos. Incluye el precio ofrecido por cada proveedor para un artículo determinado.

**Proveedor_Categoria**

Permite relacionar proveedores con diferentes categorías.

**Orden_Pedido**

Permite asociar varios pedidos internos con una misma orden de compra y que los pedidos se relacionen con órdenes según las reglas del sistema.

**Permiso**

Relaciona roles con pantallas y define las operaciones permitidas.

**Adjudicacion_Detalle**

Almacena los detalles de las adjudicaciones por pedido y proveedor.

## 6. Estructura del proyecto

El proyecto está organizado en paquetes para separar responsabilidades.

SistemaCompras/

│

├── pom.xml

│

├── src/

│ ├── main/

│ │ ├── java/

│ │ │ └── com/

│ │ │ └── sistemacompras/

│ │ │ │

│ │ │ ├── datos/

│ │ │ │ ├── IConexionBD.java

│ │ │ │ ├── ConexionSQLServer.java

│ │ │ │ ├── ConexionMySQL.java

│ │ │ │ ├── ConexionFactory.java

│ │ │ │ └── Configuracion.java

│ │ │ │

│ │ │ ├── entidades/

│ │ │ │ └── (19 clases POJO)

│ │ │ │

│ │ │ ├── repositorios/

│ │ │ │ ├── (Repositorios CRUD)

│ │ │ │ └── reportes/

│ │ │ │ └── (Repositorios de reportes)

│ │ │ │

│ │ │ ├── negocio/

│ │ │ │ ├── LoginService.java

│ │ │ │ ├── UsuarioService.java

│ │ │ │ └── PasswordUtil.java

│ │ │ │

│ │ │ └── ui/

│ │ │ ├── (Ventanas y paneles Swing)

│ │ │ └── reportes/

│ │ │ └── (Paneles JRViewer)

│ │ │

│ │ └── resources/

│ │ ├── config.properties

│ │ └── reportes/

│ │ └── (Archivos .jrxml)

│ │

│ └── test/

│ └── (Pruebas automatizadas)

│

├── database/

│ ├── sqlserver/

│ │ └── (Script DDL)

│ ├── mysql/

│ │ └── (Script DDL)

│ └── datos_prueba.sql

│

└── README.md

### 6.1 Paquete datos

Contiene las clases responsables de la conexión con los gestores.

**IConexionBD:** define el contrato de conexión.

**ConexionSQLServer:** implementa la conexión JDBC para SQL Server.

**ConexionMySQL:** implementa la conexión JDBC para MySQL.

**ConexionFactory:** determina qué implementación debe utilizarse.

**Configuracion:** permite leer las propiedades de configuración.

### 6.2 Paquete entidades

Contiene los 19 POJOs que representan las entidades del modelo relacional.

Cada clase representa una tabla y contiene atributos, constructores y métodos de acceso.

### 6.3 Paquete repositorios

Contiene las operaciones de acceso a datos.

Sus responsabilidades incluyen:

- Insertar registros.
- Consultar información.
- Actualizar registros.
- Eliminar registros.
- Ejecutar consultas específicas.

### 6.4 Paquete negocio

Contiene las reglas de negocio y los servicios utilizados por la interfaz.

### 6.5 Paquete ui

Contiene los formularios, ventanas y paneles gráficos.

### 6.6 Paquete ui.reportes

Contiene los componentes encargados de presentar los reportes mediante JRViewer.

## 7. Configuración de conexión a bases de datos

### 7.1 Archivo config.properties

Este archivo contiene los parámetros de conexión.

Ejemplo:

gestor.activo=SQL_SERVER

sqlserver.url=jdbc:sqlserver://localhost:1433;databaseName=SistemaCompras;encrypt=true;trustServerCertificate=true

sqlserver.user=sa

sqlserver.password=TU_PASSWORD

mysql.url=jdbc:mysql://localhost:3306/SistemaCompras

mysql.user=root

mysql.password=TU_PASSWORD

Los parámetros permiten modificar las conexiones sin cambiar directamente el código fuente.

### 7.2 Interfaz IConexionBD

Define los métodos que deben implementar los gestores de conexión.

Ejemplo conceptual:

public interface IConexionBD {

Connection crearConexion() throws SQLException;

String getNombreGestor();

boolean probarConexion();

}

### 7.3 Patrón Factory

La clase ConexionFactory implementa el patrón Factory para centralizar la creación de conexiones.

Su responsabilidad es seleccionar la implementación correspondiente.

Ejemplo conceptual:

public enum TipoGestor {

SQL_SERVER,

MYSQL

}

public static IConexionBD obtenerConexion(

TipoGestor tipo) {

switch (tipo) {

case SQL_SERVER:

return new ConexionSQLServer(

Configuracion.get("sqlserver.url"),

Configuracion.get("sqlserver.user"),

Configuracion.get("sqlserver.password")

);

case MYSQL:

return new ConexionMySQL(

Configuracion.get("mysql.url"),

Configuracion.get("mysql.user"),

Configuracion.get("mysql.password")

);

default:

throw new IllegalArgumentException(

"Gestor no soportado"

);

}

}

### 7.4 Selección del gestor en el login

El usuario selecciona SQL Server o MySQL desde la interfaz de inicio de sesión.

El valor seleccionado se utiliza para solicitar la conexión correspondiente a ConexionFactory.

El flujo general es:

Usuario selecciona gestor

|

v

Pantalla de Login

|

v

ConexionFactory

|

+----------+

| |

v v

SQL Server MySQL

| |

+-----+----+

|

v

Validación Login

|

v

Acceso al sistema

## 8. Seguridad

### 8.1 Autenticación

El acceso al sistema requiere un nombre de usuario y una contraseña válidos.

El proceso de autenticación es administrado por LoginService.

### 8.2 BCrypt

El sistema utiliza BCrypt para proteger las contraseñas.

En lugar de almacenar las contraseñas originales, se almacenan hashes.

Ejemplo conceptual:

// Generar hash

String hash = BCrypt.hashpw(

password,

BCrypt.gensalt()

);

// Verificar contraseña

boolean valido = BCrypt.checkpw(

passwordIngresada,

hashAlmacenado

);

BCrypt incorpora un proceso de salting, lo que dificulta los ataques basados en tablas precalculadas.

### 8.3 Roles

El sistema contempla cuatro roles:

| **Rol**                    | **Responsabilidad**                           |
| -------------------------- | --------------------------------------------- |
| Administrador              | Administración general del sistema.           |
| Gestor de Compras          | Gestión de adquisiciones.                     |
| Administrador de Proveedor | Administración de información de proveedores. |
| Auditor                    | Consulta y revisión de información.           |

### 8.4 Tabla Permiso

La tabla Permiso establece la relación entre los roles y las pantallas.

Contiene indicadores:

- permiteCrear
- permiteLeer
- permiteActualizar
- permiteBorrar

Cada indicador determina si un rol puede ejecutar una operación sobre una pantalla.

Los permisos deben comprobarse también en la lógica de negocio y no únicamente ocultando botones en la interfaz gráfica.

## 9. Generación de reportes

### 9.1 JasperReports

JasperReports es la biblioteca utilizada para generar informes a partir de consultas y datos de la base de datos.

Los diseños de los reportes se almacenan en archivos .jrxml.

### 9.2 Ubicación de los reportes

Los archivos se organizan dentro de:

src/main/resources/reportes/

Cada archivo contiene la estructura visual del reporte, campos, expresiones y parámetros.

### 9.3 Proceso de generación

El procedimiento general es:

1. El usuario selecciona un reporte.
2. La interfaz solicita los parámetros necesarios.
3. El repositorio de reportes ejecuta la consulta correspondiente.
4. Se prepara la fuente de datos.
5. JasperReports procesa el diseño.
6. Se genera un objeto JasperPrint.
7. El resultado se presenta mediante JRViewer.

### 9.4 Reportes integrados

| **N.º** | **Reporte**                       | **Finalidad**                                |
| ------- | --------------------------------- | -------------------------------------------- |
| 1       | Análisis de Ofertas por Orden     | Comparar propuestas de proveedores.          |
| 2       | Eficiencia del Proceso de Compra  | Evaluar el rendimiento de las adquisiciones. |
| 3       | Gasto Departamental               | Analizar gastos por departamento.            |
| 4       | Historial de Compras por Artículo | Consultar compras históricas.                |
| 5       | Órdenes Activas                   | Supervisar órdenes vigentes.                 |
| 6       | Pedidos Pendientes                | Identificar solicitudes pendientes.          |
| 7       | Top 5 Proveedores                 | Identificar proveedores destacados.          |
| 8       | Variación de Precios              | Analizar cambios en los precios.             |

### 9.5 Parámetros

Los parámetros se definen en los diseños JRXML y se envían durante la generación.

Ejemplos posibles:

- Identificador de orden.
- Identificador de artículo.
- Identificador de proveedor.
- Fecha inicial.
- Fecha final.
- Departamento.

Los parámetros exactos dependen de cada reporte.

## 10. Compatibilidad con dos SGBD

El sistema está diseñado para funcionar con SQL Server y MySQL.

### 10.1 Diferencias principales

| **Característica** | **SQL Server** | **MySQL**       |
| ------------------ | -------------- | --------------- |
| Fabricante         | Microsoft      | Oracle          |
| Lenguaje           | T-SQL          | SQL             |
| Autoincremento     | IDENTITY       | AUTO_INCREMENT  |
| Fecha actual       | GETDATE()      | NOW()           |
| Concatenación      | +              | CONCAT()        |
| Paginación         | OFFSET/FETCH   | LIMIT           |
| Tipos booleanos    | BIT            | BOOLEAN/TINYINT |

### 10.2 Adaptación de consultas

Las consultas deben considerar las diferencias sintácticas entre los motores.

Se recomienda mantener separados los fragmentos SQL que no sean compatibles entre ambos gestores.

### 10.3 Reportes

Cuando las consultas de los reportes difieren entre los gestores, se pueden utilizar:

- Vistas específicas para cada motor.
- Consultas SQL adaptadas.
- Archivos JRXML diferenciados.
- Repositorios que seleccionen la consulta según el gestor.

Si se utilizan diseños duplicados, deben conservar la misma estructura de parámetros y campos para facilitar su mantenimiento.

## 11. Compilación y despliegue

### 11.1 Requisitos

- JDK 17.
- Maven.
- Dependencias declaradas en pom.xml.
- Acceso a los gestores de bases de datos.

### 11.2 Compilación

Desde la carpeta principal del proyecto:

mvn clean package

Este comando elimina compilaciones anteriores, compila el código, ejecuta las pruebas configuradas y genera el paquete.

### 11.3 Ubicación del JAR

El archivo generado normalmente se encuentra en:

target/

### 11.4 Ejecución

java -jar target/SistemaCompras.jar

El nombre definitivo depende de la configuración de Maven.

### 11.5 Generación de Fat JAR

Para generar un JAR que incluya las dependencias, puede utilizarse maven-shade-plugin.

Ejemplo de configuración:

&lt;plugin&gt;

&lt;groupId&gt;org.apache.maven.plugins&lt;/groupId&gt;

&lt;artifactId&gt;maven-shade-plugin&lt;/artifactId&gt;

&lt;version&gt;3.6.0&lt;/version&gt;

&lt;executions&gt;

&lt;execution&gt;

&lt;phase&gt;package&lt;/phase&gt;

&lt;goals&gt;

&lt;goal&gt;shade&lt;/goal&gt;

&lt;/goals&gt;

&lt;configuration&gt;

&lt;transformers&gt;

<transformer implementation=

"org.apache.maven.plugins.shade.resource.ManifestResourceTransformer">

&lt;mainClass&gt;

com.sistemacompras.Main

&lt;/mainClass&gt;

&lt;/transformer&gt;

&lt;/transformers&gt;

&lt;/configuration&gt;

&lt;/execution&gt;

&lt;/executions&gt;

&lt;/plugin&gt;

**Importante:** Se debe sustituir com.sistemacompras.Main por la clase principal real del proyecto. También deben revisarse los recursos y servicios de las dependencias antes de generar el paquete final.

## 12. Pruebas del sistema

Las pruebas permiten verificar el funcionamiento de los módulos y la compatibilidad con ambos gestores.

### 12.1 Pruebas de autenticación

| **Caso** | **Descripción**                       | **Resultado esperado** |
| -------- | ------------------------------------- | ---------------------- |
| LOGIN-01 | Inicio con Administrador              | Acceso según permisos. |
| LOGIN-02 | Inicio con Gestor de Compras          | Acceso según permisos. |
| LOGIN-03 | Inicio con Administrador de Proveedor | Acceso según permisos. |
| LOGIN-04 | Inicio con Auditor                    | Acceso según permisos. |
| LOGIN-05 | Contraseña incorrecta                 | Acceso rechazado.      |
| LOGIN-06 | Usuario inexistente                   | Acceso rechazado.      |

### 12.2 Pruebas CRUD

Se deben ejecutar operaciones de creación, consulta, actualización y eliminación sobre:

- Sucursales.
- Departamentos.
- Artículos.
- Proveedores.
- Pedidos Internos.
- Órdenes de Compra.
- Adjudicaciones.
- Usuarios.

Se debe comprobar la persistencia de los datos y el cumplimiento de las restricciones de integridad.

### 12.3 Pruebas de reportes

Se debe verificar la generación de los ocho reportes en SQL Server y MySQL.

| **Reporte**                       | **SQL Server**       | **MySQL**            |
| --------------------------------- | -------------------- | -------------------- |
| Análisis de Ofertas por Orden     | Pendiente de validar | Pendiente de validar |
| Eficiencia del Proceso de Compra  | Pendiente de validar | Pendiente de validar |
| Gasto Departamental               | Pendiente de validar | Pendiente de validar |
| Historial de Compras por Artículo | Pendiente de validar | Pendiente de validar |
| Órdenes Activas                   | Pendiente de validar | Pendiente de validar |
| Pedidos Pendientes                | Pendiente de validar | Pendiente de validar |
| Top 5 Proveedores                 | Pendiente de validar | Pendiente de validar |
| Variación de Precios              | Pendiente de validar | Pendiente de validar |

### 12.4 Pruebas de integridad

Se deben comprobar:

- Restricciones de claves primarias.
- Restricciones de claves foráneas.
- Valores UNIQUE.
- Restricciones CHECK.
- Integridad de relaciones N.
- Validación de datos obligatorios.

## 13. Mantenimiento y extensión

### 13.1 Agregar un nuevo módulo CRUD

Para incorporar una nueva entidad se recomienda seguir este procedimiento:

1. Crear la tabla correspondiente en SQL Server y MySQL.
2. Definir las claves primarias y foráneas.
3. Crear la clase POJO en com.sistemacompras.entidades.
4. Crear el repositorio en com.sistemacompras.repositorios.
5. Implementar las operaciones CRUD.
6. Crear el servicio de negocio si existen reglas específicas.
7. Desarrollar el panel Swing.
8. Incorporar el panel al sistema de navegación.
9. Configurar los permisos en las tablas Rol, Pantalla y Permiso.
10. Realizar pruebas en ambos gestores.

### 13.2 Agregar un nuevo reporte

1. Diseñar el informe en Jaspersoft Studio.
2. Definir los campos y parámetros.
3. Crear el archivo .jrxml.
4. Guardarlo en resources/reportes/.
5. Adaptar las consultas para SQL Server y MySQL.
6. Crear el método correspondiente en el repositorio de reportes.
7. Generar el objeto JasperPrint.
8. Integrar el resultado en un panel con JRViewer.
9. Agregar la opción de acceso en la interfaz.
10. Verificar los resultados en ambos motores.

### 13.3 Recomendaciones de mantenimiento

- Mantener copias de seguridad de las bases de datos.
- No almacenar contraseñas en texto plano.
- Evitar duplicar lógica de negocio.
- Utilizar consultas parametrizadas.
- Documentar los cambios realizados.
- Probar las modificaciones en SQL Server y MySQL.
- Mantener actualizadas las dependencias.

**Fin del Manual Técnico**

Sistema de Gestión de Adquisiciones y Proveedores  
Universidad Mariano Gálvez de Guatemala  
Curso: Bases de Datos I