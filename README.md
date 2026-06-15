<h1 align="center">Antuco-ltd</h1> <br>

[**Click Aquí para Obtener la Configuración!**](https://start.spring.io/#!type=maven-project&language=java&platformVersion=4.0.7&packaging=jar&configurationFileFormat=properties&jvmVersion=21&groupId=com.grupodos.antuco&artifactId=&packageName=com.grupodos.antuco.&dependencies=lombok,web,data-jpa,validation,mysql,mariadb,flyway,cloud-feign,webflux)

<img width="1341" height="865" alt="imagen" src="https://github.com/user-attachments/assets/2972b3ce-d447-4699-9803-01cde2700f67" />


# INFORMACIÓN IMPORTANTE

#### Catalogo (8080) [catalogo_bd]: 
> Da información de productos.<br>
#### Carrito (8081) [carrito_bd]:
> Guarda compras temporales.<br>
#### Usuarios (8082) [usuarios_bd]:
> Crea y asigna rol a usuarios.<br>
#### Autenticacion (8083) [autenticacion_bd]:
> Guarda el username y la contraseña segura.<br>
#### Pagos (8084) [pagos_bd]:
> Guarda un historial de pagos de un usuario.<br>
#### Comentarios (8085) [comentarios_bd]:
> Sistema de reseñas y calificaciones.<br>
#### Evento (8086) [eventos_bd]:
> Gestión de conciertos y venta de entradas.<br>
#### Inventario (8087) [inventario_bd]:
> Control de stock físico y existencias.<br>
#### Pedidos (8088) [pedidos_bd]:
> Orquestador de ventas, conecta Pagos e Inventario.<br>
#### Media (8089) [media_bd]:
> Reproductor de música y carga de archivos MP3.<br>

---

# DEPENDENCIAS

| NOMBRE            | TIPO               |
| ----------------- | ------------------ |
| Spring Data JPA   | SQL                |
| Lombok            | DEVELOPER TOOLS    |
| Spring Web        | WEB                |
| Validation        | I/O                |
| MySQL Driver      | SQL                |
| MariaDB Driver    | SQL                |
| WebClient         | WEB                |
| Flyway            | BASE DE DATOS      |


## Dependencia de seguridad pom.xml
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```
---

# INFORMACIÓN DEL PROYECTO

<h1 align="center">Catálogo</h1>

### Obtener Producto (GET):<br>
URL: http://localhost:8080/api/V1/productos/1 <br>
Nota: Devuelve la información del producto (nombre, precio, categoría) para que otros servicios la consuman.

---

<h1 align="center">Carrito</h1>

### Agregar Item Simple (POST):<br>
URL: http://localhost:8081/api/V1/carrito/agregar-simple <br>
Body:<br>
```json
    {
        "usuarioId": "nombre123xd",
        "productoId": 1,
        "cantidad": 2
    }
```
Nota: Este endpoint consulta automáticamente al servicio de Catálogo para obtener el nombre y precio.

### Ver Carrito (GET):<br>
URL: http://localhost:8081/api/V1/carrito/nombre123xd <br>
Nota: Muestra todos los items actuales en el carrito del usuario.

---

<h1 align="center">Usuarios</h1>

### Registrar Usuario (POST):<br>
URL: http://localhost:8082/api/V1/auth/registro <br>
Body:<br>
```json
    {
        "username": "juaco_teto",
        "password": "password123",
        "rol": "USER"
    }
```

---

<h1 align="center">Autenticacion</h1>

### Login (POST):<br>
URL: http://localhost:8083/api/V1/autenticacion/login <br>
Body:<br>
```json
    {
        "username": "Alonozxd",
        "password": "password123"
    }
```
Nota: Si es exitoso, devuelve un TOKEN (JWT) para ser usado en futuras peticiones.

---

<h1 align="center">Pagos</h1>

### Procesar Pago (POST):<br>
URL: http://localhost:8084/api/V1/pagos/procesar <br>
Body:<br>
```json
    {
        "monto": 25000.0,
        "metodo": "TARJETA_CREDITO"
    }
```
Nota: Simula la validación de la tarjeta y devuelve un estado de aprobación.

---

<h1 align="center">Comentario</h1>

### Crear Comentario (POST):<br>
URL: http://localhost:8085/api/V1/comentarios/con-usuario <br>
Body:<br>
```json
    {
        "productoId": 1,
        "usuarioUsername": "nico_chavez",
        "texto": "Este vinilo es increíble, la mejor banda!",
        "calificacion": 5
    }
```
     
### Ver Comentarios (GET):<br>
URL: http://localhost:8085/api/V1/comentarios/producto/1 <br>

---

<h1 align="center">Evento</h1>

### Crear un Concierto (POST):<br>
URL: http://localhost:8086/api/V1/eventos <br>
Body:
```json
    {
        "nombre": "Gira de Despedida",
        "fecha": "2026-12-31T22:00:00",
        "lugar": "Estadio Nacional",
        "precio": 15000.0,
        "capacidadTotal": 100
    }
```
     
### Comprar Entradas (POST):<br>
URL: http://localhost:8086/api/V1/eventos/reservar<br>
Body:

```json
    {
        "eventoId": 1,
        "cantidad": 2,
        "usuarioUsername": "fan_nro1"
    }
```     

### Ver Reservas (GET):<br>
URL: http://localhost:8086/api/V1/eventos/reservas <br>

---

<h1 align="center">Inventario</h1>

### Crear Registro de Stock (POST):<br>
URL: http://localhost:8087/api/V1/inventario <br>
Body:
```json
    {
        "productoId": 1,
        "cantidad": 50,
        "tipoProducto": "Vinilo"
    }
```

### Decrementar Stock (PUT):<br>
URL: http://localhost:8087/api/V1/inventario/producto/1/decrementar?cantidad=2 <br>
Nota: Usado por el servicio de Pedidos. Si no hay suficiente stock, lanzará un error.

---

<h1 align="center">Pedidos</h1>

### Crear Pedido / Checkout (POST):<br>
URL: http://localhost:8088/api/V1/pedidos/crear <br>
Body:
```json
    {
        "usuarioUsername": "seba_test",
        "items": [
            {
                "productoId": 1,
                "nombreProducto": "Vinilo Edición Plata",
                "cantidad": 1,
                "precioUnitario": 25000.0
            }
        ]
    }
```
Nota: Este endpoint orquesta todo: Valida Inventario, Cobra con Pagos y Guarda la orden.

---

<h1 align="center">Media</h1>

### Subir Canción (POST):<br>
URL: http://localhost:8089/api/V1/media/subir <br>
Body (Form-data, no JSON):<br>
- Key: `titulo`, Value: `Mi Canción`<br>
- Key: `idAlbum`, Value: `01`<br>
- Key: `file`, Value: (Seleccionar archivo .mp3)<br>

### Reproducir Canción (GET):<br>
URL: http://localhost:8089/api/V1/media/reproducir/1 <br>
Nota: Abrir esta URL en el navegador reproduce el audio directamente.


---
# COMO CONECTAR HIBERNATE A XAMPP:
## Qué Hice?

Esta guía detalla cómo establecer una conexión entre una aplicación Java con **Hibernate** y el servidor local **XAMPP**.

---

## 📋 Requisitos Previos

1.  **XAMPP** instalado y ejecutándose.
2.  **MySQL/MariaDB** activo en Laragon (botón "Start All").
3.  **HeidiSQL** (incluido en Laragon) para gestionar la base de datos.

---

## 🛠️ Paso 1: Configurar la Base de Datos

Antes de ejecutar tu código Java, debes crear el esquema manualmente:

1. Abre **Laragon** y pulsa en **Database**.
2. En **HeidiSQL**, haz clic derecho sobre tu sesión -> **Crear nuevo** -> **Base de datos**.
3. Dale un nombre (ej. `mi_proyecto_db`).

> [!IMPORTANT]
> Hibernate crea las tablas automáticamente, pero **no** puede crear la base de datos por ti.

---

## 📦 Paso 2: Dependencias (Maven)

Asegúrate de tener el driver de MySQL y el núcleo de Hibernate en tu `pom.xml`:

```xml
<dependencies>
    <dependency>
        <groupId>org.hibernate</groupId>
        <artifactId>hibernate-core</artifactId>
        <version>6.4.4.Final</version>
    </dependency>

    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
        <version>8.3.0</version>
    </dependency>
</dependencies>
```

## Paso 3: Archivo de Configuración (hibernate.cfg.xml)

Crea este archivo en la carpeta src/main/resources. Aquí definimos las credenciales por defecto de Laragon.
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE hibernate-configuration PUBLIC
        "-//Hibernate/Hibernate Configuration DTD 3.0//EN"
        "[http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd](http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd)">

<hibernate-configuration>
    <session-factory>
        <property name="connection.driver_class">com.mysql.cj.jdbc.Driver</property>
        <property name="connection.url">jdbc:mysql://localhost:3306/mi_proyecto_db</property>
        <property name="connection.username">root</property>
        <property name="connection.password"></property> <property name="dialect">org.hibernate.dialect.MySQLDialect</property>

        <property name="show_sql">true</property>
        <property name="format_sql">true</property>
        <property name="hbm2ddl.auto">update</property>

        </session-factory>
</hibernate-configuration>
```

## Paso 4: Implementación en Java

Para interactuar con la base de datos, seguimos este flujo básico de transacciones:
```java
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Main {
    public static void main(String[] args) {
        // Crear la fábrica de sesiones
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(TuClase.class)
                .buildSessionFactory();

        // Crear sesión e iniciar transacción
        try (Session session = factory.openSession()) {
            session.beginTransaction();

            // Lógica de base de datos aquí...
            
            session.getTransaction().commit();
        } finally {
            factory.close();
        }
    }
}
```
<img width="1525" height="871" alt="image" src="https://github.com/user-attachments/assets/0a1bbc85-38ff-47ce-bfc5-80cc3656d949" />







ok?
