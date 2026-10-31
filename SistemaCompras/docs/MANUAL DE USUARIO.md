**MANUAL DE USUARIO**

**Sistema de Gestión de Adquisiciones y Proveedores**

**Versión:** 1.0  
**Fecha:** 30 de octubre de 2026  
**Proyecto:** Sistema de Gestión de Adquisiciones y Proveedores  
**Institución:** Universidad Mariano Gálvez de Guatemala  
**Curso:** Bases de Datos I **Desarrolladores:** Edison Abadí De la Cruz Hernández Marvin Alberto Jovany Lux Martínez Kilian Saul Jacinto Osorio Brandon Ediberto Galeano Sis Sebastián Nehemías Gonzales Ordoñez

Índice

**MANUAL DE USUARIO** 1

**Sistema de Gestión de Adquisiciones y Proveedores** 1

[1\. Introducción 3](#1-introducción)

[1.1 Objetivo 3](#11-objetivo)

[1.2 Usuarios a quienes está dirigido 3](#12-usuarios-a-quienes-está-dirigido)

[2\. Requisitos del sistema 3](#2-requisitos-del-sistema)

[2.1 Requisitos de hardware 3](#21-requisitos-de-hardware)

[2.2 Requisitos de software 3](#22-requisitos-de-software)

[3\. Instalación y configuración inicial 4](#3-instalación-y-configuración-inicial)

[3.1 Instalación de Java 4](#31-instalación-de-java)

[3.2 Preparación de la base de datos 4](#32-preparación-de-la-base-de-datos)

[3.3 Configuración del archivo config.properties 4](#33-configuración-del-archivo-configproperties)

[3.4 Ejecución del sistema 5](#34-ejecución-del-sistema)

[4\. Guía de inicio rápido 5](#4-guía-de-inicio-rápido)

[4.1 Pantalla de inicio de sesión 5](#41-pantalla-de-inicio-de-sesión)

[4.2 Menú principal 6](#42-menú-principal)

[5\. Módulos del sistema 6](#5-módulos-del-sistema)

[5.1 Módulo de Sucursales 6](#51-módulo-de-sucursales)

[Crear una sucursal 6](#crear-una-sucursal)

[Editar una sucursal 6](#editar-una-sucursal)

[Eliminar una sucursal 7](#eliminar-una-sucursal)

[5.2 Módulo de Departamentos 7](#52-módulo-de-departamentos)

[Crear 7](#crear)

[Editar 7](#editar)

[Eliminar 7](#eliminar)

[5.3 Módulo de Artículos 7](#53-módulo-de-artículos)

[Crear 7](#crear-1)

[Editar 8](#editar-1)

[Eliminar 8](#eliminar-1)

[5.4 Módulo de Proveedores 8](#54-módulo-de-proveedores)

[Crear 8](#crear-2)

[Editar 8](#editar-2)

[Eliminar 8](#eliminar-2)

[5.5 Módulo de Pedidos Internos 9](#55-módulo-de-pedidos-internos)

[Crear 9](#crear-3)

[Editar 9](#editar-3)

[Eliminar 9](#eliminar-3)

[5.6 Módulo de Órdenes de Compra 9](#56-módulo-de-órdenes-de-compra)

[Crear 9](#crear-4)

[Editar 10](#editar-4)

[Eliminar 10](#eliminar-4)

[5.7 Módulo de Adjudicaciones 10](#57-módulo-de-adjudicaciones)

[Crear 10](#crear-5)

[Editar 10](#editar-5)

[Eliminar 10](#eliminar-5)

[5.8 Módulo de Usuarios 11](#58-módulo-de-usuarios)

[Crear 11](#crear-6)

[Editar 11](#editar-6)

[Eliminar 11](#eliminar-6)

[6\. Generación de reportes 11](#6-generación-de-reportes)

[6.1 Análisis de Ofertas por Orden 11](#61-análisis-de-ofertas-por-orden)

# 1. Introducción

El Sistema de Gestión de Adquisiciones y Proveedores es una aplicación de escritorio desarrollada en Java Swing, diseñada para administrar y controlar los procesos relacionados con las adquisiciones de productos, la gestión de proveedores y el seguimiento de pedidos internos.

El sistema permite registrar información, consultar datos, modificar registros, administrar usuarios y generar reportes que facilitan el análisis de las operaciones de compra.

Además, cuenta con compatibilidad con dos gestores de bases de datos: Microsoft SQL Server y MySQL. El usuario puede seleccionar el gestor que desea utilizar desde la pantalla de inicio de sesión.

## 1.1 Objetivo

Proporcionar una herramienta que facilite la administración de los procesos de adquisición, centralizando la información y permitiendo un mejor control de las operaciones.

## 1.2 Usuarios a quienes está dirigido

El sistema está dirigido a:

- Administradores del sistema.
- Gestores de compras.
- Administradores de proveedores.
- Auditores.

Cada usuario dispone de opciones según el rol que tenga asignado.

# 2. Requisitos del sistema

## 2.1 Requisitos de hardware

- Procesador de doble núcleo o superior.
- Memoria RAM mínima de 4 GB (recomendado 8 GB).
- Espacio disponible en disco para la aplicación y los gestores de bases de datos.
- Resolución de pantalla recomendada de 1366 × 768 píxeles.

## 2.2 Requisitos de software

| **Software**       | **Requisito**              |
| ------------------ | -------------------------- |
| Sistema operativo  | Windows 10/11 o compatible |
| Java               | JDK/JRE 17                 |
| Base de datos      | SQL Server o MySQL         |
| Controladores JDBC | Según el gestor utilizado  |
| Aplicación         | Archivo JAR del sistema    |

# 3. Instalación y configuración inicial

## 3.1 Instalación de Java

1. Descargar e instalar Java 17.
2. Configurar la variable de entorno JAVA_HOME si es necesario.
3. Verificar la instalación desde una terminal:

java -version

El sistema debe mostrar una versión compatible con Java 17.

## 3.2 Preparación de la base de datos

Antes de ejecutar el sistema, es necesario:

1. Instalar Microsoft SQL Server o MySQL.
2. Iniciar el servicio correspondiente.
3. Crear la base de datos utilizando el script DDL proporcionado.
4. Ejecutar el script DML para insertar los datos iniciales o de prueba.

**Importante:** La estructura de la base de datos debe existir previamente en el gestor seleccionado.

## 3.3 Configuración del archivo config.properties

El archivo config.properties almacena los parámetros de conexión.

Ejemplo ilustrativo:

\# Configuración del gestor predeterminado

gestor.activo=SQL_SERVER

\# SQL Server

sqlserver.url=jdbc:sqlserver://localhost:1433;databaseName=SistemaCompras;encrypt=true;trustServerCertificate=true

sqlserver.user=sa

sqlserver.password=TU_PASSWORD

\# MySQL

mysql.url=jdbc:mysql://localhost:3306/SistemaCompras

mysql.user=root

mysql.password=TU_PASSWORD

Se deben reemplazar los valores de usuario y contraseña según la configuración de cada servidor.

**Nota:** La ubicación del archivo debe coincidir con la ruta que utiliza la aplicación para cargar la configuración.

## 3.4 Ejecución del sistema

Una vez configurado el entorno:

java -jar SistemaCompras.jar

El sistema abrirá la ventana de inicio de sesión.

\[Insertar captura de pantalla de la aplicación iniciada\]

# 4. Guía de inicio rápido

## 4.1 Pantalla de inicio de sesión

La pantalla de inicio de sesión permite autenticar a los usuarios y seleccionar el gestor de base de datos.

Los campos disponibles son:

- **Usuario:** nombre de usuario registrado.
- **Contraseña:** contraseña correspondiente a la cuenta.
- **Gestor de base de datos:** permite seleccionar SQL Server o MySQL.
- **Iniciar sesión:** valida las credenciales e ingresa al sistema.

Pasos:

1. Abrir la aplicación.
2. Seleccionar SQL Server o MySQL.
3. Introducir el nombre de usuario.
4. Introducir la contraseña.
5. Presionar **Iniciar sesión**.
6. Esperar la validación de las credenciales.

Si los datos son correctos, se mostrará el menú principal correspondiente al rol del usuario.

\[Insertar captura de pantalla del Login\]

## 4.2 Menú principal

El menú principal permite acceder a los diferentes módulos de administración y reportes.

Las opciones disponibles dependen del rol asignado al usuario.

\[Insertar captura de pantalla del menú principal\]

# 5. Módulos del sistema

Los módulos CRUD permiten crear, consultar, actualizar y eliminar información.

Los pasos siguientes describen el procedimiento general. Los nombres exactos de los botones pueden variar según la interfaz implementada.

## 5.1 Módulo de Sucursales

Permite administrar las diferentes sucursales de la organización, almacenando información como código, dirección, ciudad y departamento.

### Crear una sucursal

1. Ingresar al módulo **Sucursales**.
2. Presionar **Nuevo**.
3. Introducir el código de la sucursal.
4. Completar la dirección, ciudad y departamento.
5. Presionar **Guardar**.
6. Verificar que el registro aparezca en la tabla.

### Editar una sucursal

1. Seleccionar una sucursal de la tabla.
2. Presionar **Editar**.
3. Modificar los campos necesarios.
4. Presionar **Actualizar**.

### Eliminar una sucursal

1. Seleccionar la sucursal.
2. Presionar **Eliminar**.
3. Confirmar la operación.

\[Insertar captura de pantalla del módulo Sucursales\]

## 5.2 Módulo de Departamentos

Permite administrar los departamentos asociados a cada sucursal.

### Crear

1. Ingresar a **Departamentos**.
2. Presionar **Nuevo**.
3. Seleccionar la sucursal correspondiente.
4. Introducir el nombre y la descripción.
5. Presionar **Guardar**.

### Editar

1. Seleccionar el departamento.
2. Presionar **Editar**.
3. Modificar la información.
4. Presionar **Actualizar**.

### Eliminar

1. Seleccionar el departamento.
2. Presionar **Eliminar**.
3. Confirmar la operación.

\[Insertar captura de pantalla del módulo Departamentos\]

## 5.3 Módulo de Artículos

Permite administrar el catálogo de artículos utilizados en los procesos de adquisición.

### Crear

1. Ingresar a **Artículos**.
2. Presionar **Nuevo**.
3. Introducir el código del artículo.
4. Completar el nombre y la descripción.
5. Presionar **Guardar**.

### Editar

1. Seleccionar el artículo.
2. Presionar **Editar**.
3. Modificar los datos.
4. Presionar **Actualizar**.

### Eliminar

1. Seleccionar el artículo.
2. Presionar **Eliminar**.
3. Confirmar la operación.

\[Insertar captura de pantalla del módulo Artículos\]

## 5.4 Módulo de Proveedores

Permite registrar y administrar los proveedores que participan en los procesos de compra.

Entre los datos principales se encuentran el NIT, nombre comercial, dirección y teléfono.

### Crear

1. Ingresar a **Proveedores**.
2. Presionar **Nuevo**.
3. Introducir el NIT.
4. Completar el nombre comercial.
5. Introducir la dirección y el teléfono.
6. Presionar **Guardar**.

### Editar

1. Seleccionar el proveedor.
2. Presionar **Editar**.
3. Modificar la información.
4. Presionar **Actualizar**.

### Eliminar

1. Seleccionar el proveedor.
2. Presionar **Eliminar**.
3. Confirmar la operación.

\[Insertar captura de pantalla del módulo Proveedores\]

## 5.5 Módulo de Pedidos Internos

Permite registrar las solicitudes de artículos realizadas por los departamentos de la organización.

### Crear

1. Ingresar a **Pedidos Internos**.
2. Presionar **Nuevo**.
3. Seleccionar el departamento solicitante.
4. Seleccionar el artículo.
5. Introducir la cantidad solicitada.
6. Indicar la fecha de solicitud.
7. Indicar la fecha de necesidad.
8. Presionar **Guardar**.

### Editar

1. Seleccionar el pedido.
2. Presionar **Editar**.
3. Modificar los datos permitidos.
4. Presionar **Actualizar**.

### Eliminar

1. Seleccionar el pedido.
2. Presionar **Eliminar**.
3. Confirmar la operación.

\[Insertar captura de pantalla del módulo Pedidos Internos\]

## 5.6 Módulo de Órdenes de Compra

Permite administrar las órdenes generadas durante el proceso de adquisición.

### Crear

1. Ingresar a **Órdenes de Compra**.
2. Presionar **Nuevo**.
3. Introducir la descripción.
4. Seleccionar la fecha de creación.
5. Seleccionar la fecha límite.
6. Seleccionar el tipo y subtipo de orden.
7. Asociar los pedidos correspondientes, si la interfaz lo permite.
8. Presionar **Guardar**.

### Editar

1. Seleccionar la orden.
2. Presionar **Editar**.
3. Modificar los campos disponibles.
4. Presionar **Actualizar**.

### Eliminar

1. Seleccionar la orden.
2. Presionar **Eliminar**.
3. Confirmar la operación.

\[Insertar captura de pantalla del módulo Órdenes de Compra\]

## 5.7 Módulo de Adjudicaciones

Permite registrar la resolución de una orden de compra y documentar los proveedores seleccionados, las cantidades finales y los precios acordados.

### Crear

1. Ingresar a **Adjudicaciones**.
2. Presionar **Nuevo**.
3. Seleccionar la orden de compra.
4. Introducir la fecha de resolución.
5. Registrar los detalles de adjudicación.
6. Seleccionar los proveedores correspondientes.
7. Introducir las cantidades finales y precios acordados.
8. Presionar **Guardar**.

### Editar

1. Seleccionar la adjudicación.
2. Presionar **Editar**.
3. Modificar los datos permitidos.
4. Presionar **Actualizar**.

### Eliminar

1. Seleccionar la adjudicación.
2. Presionar **Eliminar**.
3. Confirmar la operación.

\[Insertar captura de pantalla del módulo Adjudicaciones\]

## 5.8 Módulo de Usuarios

Permite administrar las cuentas de acceso al sistema y asignar roles.

### Crear

1. Ingresar al módulo **Usuarios**.
2. Presionar **Nuevo**.
3. Introducir el nombre de usuario.
4. Introducir la contraseña.
5. Seleccionar el rol correspondiente.
6. Completar los datos adicionales solicitados.
7. Presionar **Guardar**.

### Editar

1. Seleccionar el usuario.
2. Presionar **Editar**.
3. Modificar los campos permitidos.
4. Presionar **Actualizar**.

### Eliminar

1. Seleccionar el usuario.
2. Presionar **Eliminar**.
3. Confirmar la operación.

\[Insertar captura de pantalla del módulo Usuarios\]

# 6. Generación de reportes

El sistema incorpora ocho reportes desarrollados con JasperReports e integrados mediante el componente JRViewer.

Estos reportes permiten consultar y analizar información relacionada con los procesos de adquisición.

## 6.1 Análisis de Ofertas por Orden

**Objetivo:** Comparar las ofertas presentadas por los proveedores para una orden de compra.

Permite identificar diferencias entre precios y evaluar las propuestas recibidas.

**Parámetros:** Orden de compra seleccionada, si corresponde.

**Interpretación:** Permite comparar las propuestas económicas y