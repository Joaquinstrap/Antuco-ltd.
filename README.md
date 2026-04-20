# INFORMACIÓN IMPORTANTE
### Laragon: 

Es como xampp, sirve para levantar la base de datos y poder ocuparla
### Hibernate: 

Es el elemento que ayud a conectar los datos del microservicio con la BD, de forma automatica crea las tablas (creo). 

# INFORMACIÓN DEL PROYECTO


# COMO CONECTAR HIBERNATE A LARAGON:

# 🚀 Configuración de Hibernate con Laragon

Esta guía detalla cómo establecer una conexión entre una aplicación Java con **Hibernate** y el servidor local **Laragon**.

---

## 📋 Requisitos Previos

1.  **Laragon** instalado y ejecutándose.
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


## Paso 3: Archivo de Configuración (hibernate.cfg.xml)

Crea este archivo en la carpeta src/main/resources. Aquí definimos las credenciales por defecto de Laragon.

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


## Paso 4: Implementación en Java

Para interactuar con la base de datos, seguimos este flujo básico de transacciones:

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
