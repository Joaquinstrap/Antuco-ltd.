<div align="center">

# 🎸 Antuco-ltd
### Arquitectura de Microservicios con Spring Boot y Spring Cloud

[![Java](https://img.shields.io/badge/Java-21ED8B0?style=for-the-badge&logo=openjdk&logoColor=black)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.0.7-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Spring Cloud](https://img.shields.io/badge/Spring_Cloud-Eureka-6DB33F?style=for-the-badge&logo=spring&logoColor=white)](https://spring.io/projects/spring-cloud)
[![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)](https://www.mysql.com/)
[![Flyway](https://img.shields.io/badge/Flyway-CC0200?style=for-the-badge&logo=flyway&logoColor=white)](https://flywaydb.org/)
[![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)](https://www.docker.com/)

</div>

---

## Descripción del Proyecto

[**Click Aquí para Obtener la Configuración!**](https://start.spring.io/#!type=maven-project&language=java&platformVersion=4.0.7&packaging=jar&configurationFileFormat=properties&jvmVersion=21&groupId=com.example.antuco&artifactId=&packageName=com.example.antuco.&dependencies=web,cloud-eureka,data-jpa,validation,flyway,mysql,mariadb,lombok)

**Antuco-ltd** es una banda de música con una web diseñada específicamente para la gestión de eventos y merch. El sistema permite a los fans comprar merchandising oficial (vinilos, poleras, etc.), adquirir entradas para conciertos, reproducir media y dejar reseñas sobre los productos.

El proyecto está construido bajo una **arquitectura de microservicios**, garantizando alta escalabilidad, mantenimiento independiente de cada módulo y resiliencia.

---

## Arquitectura y Microservicios

El sistema está compuesto por **11 servicios en total** (1 Servidor de Descubrimiento + 10 Microservicios de Negocio). Todos los microservicios se registran en **Eureka** para el balanceo de carga y descubrimiento dinámico.

### 🗺️ Mapa de Puertos y Bases de Datos

| Microservicio           | Puerto | Base de Datos       | Descripción                                 |
| ----------------------- | :----: | ------------------- | ------------------------------------------- |
| **Eureka Server**       | `8761` | N/A                 | Servidor de descubrimiento de servicios.    |
| **Catálogo**            | `8080` | `catalogo_bd`       | Información de productos (vinilos, ropa).   |
| **Carrito**             | `8081` | `carrito_bd`        | Compras temporales del usuario.             |
| **Usuarios**            | `8082` | `usuarios_bd`       | Gestión de usuarios y roles.                |
| **Autenticación**       | `8083` | `autenticacion_bd`  | Seguridad, login y manejo de credenciales.  |
| **Pagos**               | `8084` | `pagos_bd`          | Historial y procesamiento de pagos.         |
| **Comentarios**         | `8085` | `comentarios_bd`    | Reseñas y calificaciones de productos.      |
| **Eventos**             | `8086` | `eventos_bd`        | Gestión de conciertos y venta de entradas.  |
| **Inventario**          | `8087` | `inventario_bd`     | Control de stock físico.                    |
| **Pedidos**             | `8088` | `pedidos_bd`        | Orquestador de ventas (Saga transaccional). |
| **Media**               | `8089` | `media_bd`          | URLs de imágenes y carga de archivos MP3.   |

---

## Lógica de Dependencias y Comunicación

La comunicación entre microservicios se realiza de forma asíncrona y síncrona utilizando `WebClient` a través de Eureka. La arquitectura se divide en tres capas lógicas:

### 1. Servicios Independientes (Base)
No dependen de nadie, son el pilar de los datos maestros.
*  **Catálogo:** Información de productos.
*  **Usuarios / Autenticación:** Gestión de identidad.
*  **Eventos:** Gestión de conciertos.

### 2. Servicios Dependientes (Lectura y Soporte)
Consumen de los servicios base para validar existencia.
*  **Inventario:** Depende de *Catálogo*. El stock carece de sentido sin el producto asociado.
*  **Media:** Depende de *Catálogo*. Guarda las URLs de imágenes asociadas por ID de producto.
*  **Comentarios:** Depende de *Usuarios* y *Catálogo*. Vincula el ID del usuario que comenta con el ID del producto comentado.

### 3. Flujo Transaccional (Patrón Saga)
El flujo de compra sigue un patrón de orquestación:
1.  **Carrito:** Depende de *Usuarios* y *Catálogo*. Guarda la intención de compra temporal.
2.  **Pedidos:** Recibe la info del Carrito, genera un número de orden y la pone en espera (Estado: PENDIENTE).
3.  **Pagos:** Procesa el cobro de la orden (Simulación de pasarelas como Stripe/Transbank).
   - ❌ **Si es rechazado:** Emite alerta de error, no se descuenta stock, el pedido queda fallido.
   - ✅ **Si es aprobado:** Notifica a *Pedidos* (Pasa a estado PAGADA/EN PREPARACIÓN) y notifica a *Inventario* (Descuenta el stock físico).

---

## Stack Tecnológico

- **Lenguaje:** Java 21
- **Framework:** Spring Boot 4.0.7
- **Arquitectura de Nube:** Spring Cloud (Eureka Discovery Client)
- **Base de Datos:** MySQL / MariaDB
- **Migraciones:** Flyway
- **Persistencia:** Spring Data JPA / Hibernate
- **Comunicación reactiva:** Spring Reactive Web (WebClient)
- **Documentación:** Swagger / OpenAPI
- **Otros:** Lombok, Spring Validation

---

## Configuración y Ejecución Local

### Requisitos Previos
- JDK 21 o superior instalado.
- MySQL o MariaDB corriendo localmente.
- Maven.

### Pasos para levantar el sistema

1. **Clonar el repositorio:**
   ```bash
   git clone https://github.com/Joaquinstrap/Antuco-ltd.git
   cd Antuco-ltd
   ```

2. **Configurar Bases de Datos:**
   Crea una base de datos vacía en tu gestor SQL para cada microservicio (ej: `catalogo_bd`, `usuarios_bd`, etc.). Flyway se encargará de crear las tablas automáticamente.

3. **Levantar Eureka Server:**
   Navega a la carpeta `eureka-server` y ejecuta:
   ```bash
   mvn spring-boot:run
   ```
   *Verifica que esté activo en: [http://localhost:8761](http://localhost:8761)*

4. **Levantar los Microservicios:**
   En terminales separadas, navega a la carpeta de cada microservicio y ejecuta:
   ```bash
   mvn spring-boot:run
   ```
   *Cada servicio se registrará automáticamente en Eureka.*

---

## 👥 Equipo de Desarrollo

<div align="center">
<table>
<tr>
<td align="center">
<a href="https://github.com/Sebastia1111">
<img src="https://github.com/Sebastia1111.png" width="100" style="border-radius:50%"><br>
<strong>Sebastian Orellana</strong><br>
</a>
</td>
<td align="center">
<a href="https://github.com/Joaquinstrap">
<img src="https://github.com/Joaquinstrap.png" width="100" style="border-radius:50%"><br>
<strong>Joaquin Correa</strong><br>
</a>
</td>
<td align="center">
<a href="https://github.com/nicchavez-duocuc">
<img src="https://github.com/nicchavez-duocuc.png" width="100" style="border-radius:50%"><br>
<strong>Nicolas Chavez</strong><br>
</a>
</td>
</tr>
</table>
</div>

---

## 📄 Licencia

Este proyecto fue desarrollado con fines **educativos** como parte de la asignatura de Fullstack en DuocUC.

<div align="center">

**Hecho con ❤️ para DuocUC**

<p>
<img src="https://img.shields.io/badge/FULLSTACK-2026-FFC20E?style=for-the-badge">
</p>
