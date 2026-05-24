<h1 align="center">Antuco-ltd</h1> <br>

[**Click Aquí para Obtener la Configuración!**](https://start.spring.io/#!type=maven-project&language=java&platformVersion=4.0.6&packaging=jar&configurationFileFormat=properties&jvmVersion=25&groupId=com.example.antuco&artifactId=&packageName=com.example.antuco.&dependencies=lombok,web,data-jpa,validation,mysql,mariadb,flyway)

<img width="1717" height="962" alt="imagen" src="https://github.com/user-attachments/assets/21ae1cfb-0b3e-4778-858b-220a9e09757e" />


# INFORMACIÓN IMPORTANTE

#### Pedidos (8088): <br>
#### Inventario (8087): <br>
#### Evento (8086)[eventos_bd]: <br>
#### Comentarios (8085): <br>
#### Pagos (8084): Guarda un historial de pagos de un usuario.<br>
#### Autenticacion (8083): Guarda el username y la contraseña segura.<br>
#### Usuarios (8082): Crea y asigna rol a usuarios.<br>
#### Carrito (8081): Guarda compras.<br>
#### Catálogo (8080): Da información de productos.<br>

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

<h1 align="center">Comentario</h1>

### Crear Comentario (POST):<br>
URL: http://localhost:8085/api/V1/comentarios/con-usuario <br>
Body:<br>
```json
    {
        "productoId": 1,
        "usuarioUsername": "juan_perez",
        "texto": "Este vinilo es increíble, la mejor banda!",
        "calificacion": 5
    }
```
     
### Ver Comentarios (GET):

---

<h1 align="center">Evento</h1>


### Crear un Concierto (POST):<br>

URL: http://localhost:8086/api/V1/eventos <br>
Body:
```json
     
      {
        "nombre": "Gira de Despedida",
        "fecha": "2024-12-31T22:00:00",
        "lugar": "Estadio Nacional",
        "precio": 15000.0,
        "capacidadTotal": 100
    }
```
     
Comprar Entradas (POST):<br>

URL: http://localhost:8086/api/V1/eventos/reservar<br>
Body:

```json
          
    {
        "eventoId": 1,
        "cantidad": 2,
        "usuarioUsername": "fan_nro1"
    }
```     

Intentar comprar de más (Prueba de error):<br>
Intenta comprar 99 entradas más (total 101). Debería decirte "No hay suficientes entradas".<br>
URL: http://localhost:8085/api/V1/comentarios/producto/1 <br>

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
