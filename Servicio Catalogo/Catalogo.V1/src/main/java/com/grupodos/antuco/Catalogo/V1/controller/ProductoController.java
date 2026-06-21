package com.grupodos.antuco.Catalogo.V1.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupodos.antuco.Catalogo.V1.model.Categoria;
import com.grupodos.antuco.Catalogo.V1.model.Musicafisica;
import com.grupodos.antuco.Catalogo.V1.model.ProductoAtributo;
import com.grupodos.antuco.Catalogo.V1.model.Productosdelcatalogo;
import com.grupodos.antuco.Catalogo.V1.model.Ropa;
import com.grupodos.antuco.Catalogo.V1.model.productoDTO;
import com.grupodos.antuco.Catalogo.V1.service.ProductoService;


@RestController
@RequestMapping("/api/V1/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    // -- 1. Obtener todos los productos --
    @GetMapping
    public ResponseEntity<?> obtenerTodo() {
        try {
            return ResponseEntity.ok(productoService.obtenerTodosProductos());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // -- 2. Obtener producto por ID --
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(productoService.obtenerProductoPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    // -- 3. Buscar productos por Categoría --
    
    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<Productosdelcatalogo>> listarPorCategoria(@PathVariable String categoriaId) {
        // Corregido: Se quitó la variable 'id' residual al final
        return ResponseEntity.ok(productoService.buscarPorCategoria(categoriaId));
    }

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody productoDTO dto) { // <-- Usando el nombre exacto (con p minúscula)
        try {
            Productosdelcatalogo productoReal;

            // 1. Identificamos qué tipo de producto quiere crear el usuario
            if ("Ropa".equalsIgnoreCase(dto.getDtype())) {
                Ropa ropa = new Ropa();
                ropa.setMaterialcreacion(dto.getMaterialcreacion());
                ropa.setTalla(dto.getTalla());
                productoReal = ropa;
            } else if ("Musicafisica".equalsIgnoreCase(dto.getDtype())) {
                Musicafisica musica = new Musicafisica();
                musica.setTipoformato(dto.getTipoformato());
                musica.setNumerocanciones(dto.getNumerocanciones());
                musica.setAlbum(dto.getAlbum());
                // Nota: no pusiste 'artista' en tu DTO, así que solo mapeamos formato
                productoReal = musica;
            } else {
                return ResponseEntity.badRequest().body("El tipo 'dtype' debe ser 'Ropa' o 'Musicafisica'");
            }

            // 2. Llenamos los datos comunes del padre
            productoReal.setNombre_producto(dto.getNombre_producto());
            productoReal.setPrecio(dto.getPrecio());

            // Vinculamos la categoría
            // (Asumimos que en el JSON el usuario enviará el ID de la categoría, por ejemplo: "02")
            if (dto.getCategoria() != null) {
                Categoria cat = new Categoria();
                cat.setId(dto.getCategoria()); 
                productoReal.setCategoria(cat);
            }

            // 3. Procesamos los atributos extra usando la clase ProductoAtributo
            if (dto.getAtributosExtra() != null) {
                for (Map.Entry<String, String> entry : dto.getAtributosExtra().entrySet()) {
                    ProductoAtributo attr = new ProductoAtributo();
                    attr.setNombreAtributo(entry.getKey());  
                    attr.setValorAtributo(entry.getValue()); 
                    attr.setProducto(productoReal);
                    productoReal.getAtributos().add(attr);
                }
            }

            // 4. Mandamos a guardar usando tu servicio
            Productosdelcatalogo guardado = productoService.guardarProducto(productoReal);
            return ResponseEntity.status(HttpStatus.CREATED).body(guardado);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar: " + e.getMessage());
        }
    }

    // -- 5. Actualizar un producto existente --
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody productoDTO dto) {
        try {
            // 1. Buscamos el producto que ya existe en la base de datos
            // (Ajusta "obtenerPorId" al nombre real del método que este en el productoService)
            Productosdelcatalogo productoExistente = productoService.obtenerProductoPorId(id); 
            
            if (productoExistente == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el producto con ID: " + id);
            }

            // 2. Actualizamos los datos comunes del padre (si el usuario los envió)
            if (dto.getNombre_producto() != null) {
                productoExistente.setNombre_producto(dto.getNombre_producto());
            }
            if (dto.getPrecio() > 0) { // Suponiendo que el precio debe ser mayor a 0
                productoExistente.setPrecio(dto.getPrecio());
            }
            if (dto.getCategoria() != null) {
                Categoria cat = new Categoria();
                cat.setId(dto.getCategoria());
                productoExistente.setCategoria(cat);
            }

            // 3. Actualizamos los datos específicos de los hijos usando Polimorfismo
            if (productoExistente instanceof Ropa && "Ropa".equalsIgnoreCase(dto.getDtype())) {
                Ropa ropa = (Ropa) productoExistente;
                if (dto.getMaterialcreacion() != null) ropa.setMaterialcreacion(dto.getMaterialcreacion());
                if (dto.getTalla() != null) ropa.setTalla(dto.getTalla());
                
            } else if (productoExistente instanceof Musicafisica && "Musicafisica".equalsIgnoreCase(dto.getDtype())) {
                Musicafisica musica = (Musicafisica) productoExistente;
                if (dto.getTipoformato() != null) musica.setTipoformato(dto.getTipoformato());
            }

            // 4. Actualizamos los atributos extra
            if (dto.getAtributosExtra() != null) {
                // El truco maestro: borramos los atributos viejos de la lista
                // (Gracias al orphanRemoval=true que pusimos antes, Spring los borra de MySQL automáticamente)
                productoExistente.getAtributos().clear();
                
                // Y agregamos los nuevos que mandó el usuario en el JSON
                for (Map.Entry<String, String> entry : dto.getAtributosExtra().entrySet()) {
                    ProductoAtributo attr = new ProductoAtributo();
                    attr.setNombreAtributo(entry.getKey());
                    attr.setValorAtributo(entry.getValue());
                    attr.setProducto(productoExistente);
                    productoExistente.getAtributos().add(attr);
                }
            }

            // 5. Guardamos el objeto ya modificado
            
            Productosdelcatalogo actualizado = productoService.actualizarProducto(productoExistente);
            return ResponseEntity.ok(actualizado);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar: " + e.getMessage());
        }
    }

    // -- 6. Eliminar un producto --
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            // Aquí cambiamos "eliminar" por "eliminarProducto"
            boolean eliminado = productoService.eliminarProducto(id);
            if (eliminado) {
                return ResponseEntity.ok("Producto eliminado correctamente");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el producto");
        }
    }
}
