# Guía de Implementación de OpenFeign

Este documento describe cómo implementar la librería OpenFeign para gestionar la comunicación entre microservicios, reemplazando el uso manual de RestTemplate.

## 1. Agregar la Dependencia

En el archivo `pom.xml` de cada microservicio que deba conectarse a otro, agrega la dependencia de OpenFeign:

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
    <version>4.0.4</version>
</dependency>
```

*Nota: Recuerda recargar el proyecto de Maven después de agregar esta dependencia.*

## 2. Activar Feign en la Aplicación

En la clase principal de tu aplicación (donde está el metodo `main`), agrega la anotacion `@EnableFeignClients`.

**Ejemplo en `PedidosApplication.java`:**

```java
package com.example.antuco.pedidos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
// ... otros imports

@SpringBootApplication
@EnableWebSecurity
@EnableFeignClients // Habilita la búsqueda de clientes Feign
public class PedidosApplication {

    public static void main(String[] args) {
        SpringApplication.run(PedidosApplication.class, args);
    }

    // Ya no es necesario el Bean de RestTemplate para estos clientes
}
```

## 3. Definir el Cliente (Interfaz)

En lugar de crear una clase Java que use `RestTemplate`, OpenFeign utiliza interfaces Java. Crea una interfaz en el paquete `client` (por ejemplo, `InventarioClient.java`) y define la URL base y los endpoints como si fueran metodos locales.

**Ejemplo de conexion al servicio de Inventario:**

```java
package com.example.antuco.pedidos.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "inventario-service", url = "http://localhost:8087/api/V1/inventario")
public interface InventarioClient {

    @PutMapping("/producto/{productoId}/decrementar")
    void restarStock(@PathVariable("productoId") Long productoId, 
                     @RequestParam("cantidad") Integer cantidad);
}
```

### Explicacion de las anotaciones:
*   `@FeignClient`: Define el nombre del servicio y la URL base.
*   `@PutMapping`, `@GetMapping`, etc.: Indican el metodo HTTP y la ruta del endpoint remoto.
*   `@PathVariable`, `@RequestParam`: Mapean los argumentos del metodo Java a los parametros de la URL.

## 4. Inyectar y Usar el Cliente

Ahora, en tu clase de Servicio (`PedidoService`, `CarritoService`, etc.), puedes inyectar la interfaz directamente como si fuera cualquier otro Bean de Spring y usar sus metodos.

**Ejemplo en `PedidoService.java`:**

```java
@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private InventarioClient inventarioClient; // Inyeccion de la interfaz

    @Transactional
    public void procesarPedido(Long productoId, Integer cantidad) {
        try {
            // Llamada remota limpia y sencilla
            inventarioClient.restarStock(productoId, cantidad);
            
            // Continuar con la logica de negocio...
            
        } catch (Exception e) {
            throw new RuntimeException("Error al comunicarse con Inventario: " + e.getMessage());
        }
    }
}
```

## 5. Migracion desde RestTemplate

Si tenias un codigo previo usando `RestTemplate`, debes reemplazar la construccion manual de URLs y el metodo `exchange`.

**Antes (RestTemplate):**
```java
String url = "http://localhost:8087/api/V1/inventario/producto/" + id + "/decrementar?cantidad=" + cant;
restTemplate.exchange(url, HttpMethod.PUT, null, String.class);
```

**Despues (OpenFeign):**
```java
inventarioClient.restarStock(id, cant);
```

## 6. Buenas Practicas

*   **Nombres de Interfaces:** Usa el nombre del servicio seguido de "Client" (ej: `InventarioClient`, `PagosClient`).
*   **Gestion de Errores:** Las llamadas a Feign lanzaran excepciones si el servicio remoto no responde (por ejemplo, `FeignException` o `RuntimeException`). Es importante manejarlas con `try-catch` en tu Servicio.
*   **URLs:** Manten las URLs en la anotacion `@FeignClient`. Si en el futuro cambia el puerto o el dominio, solo se modifica en ese lugar.
