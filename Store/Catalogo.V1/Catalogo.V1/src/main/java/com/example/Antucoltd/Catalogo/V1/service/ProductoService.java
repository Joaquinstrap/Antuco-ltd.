package com.example.Antucoltd.Catalogo.V1.service;

import com.example.Antucoltd.Catalogo.V1.model.Productosdelcatalogo;
import com.example.Antucoltd.Catalogo.V1.model.Ropa;
import com.example.Antucoltd.Catalogo.V1.model.Musicafisica;
import com.example.Antucoltd.Catalogo.V1.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    // --- 1. OBTENER TODOS ---
    public List<Productosdelcatalogo> obtenerTodosProductos() {
        List<Productosdelcatalogo> productos = productoRepository.findAll();
        if (productos.isEmpty()) {
            throw new RuntimeException("No hay ningún producto registrado en el catálogo.");
        }
        return productos;
    }

    // --- 2. OBTENER POR ID ---
    public Productosdelcatalogo obtenerProductoPorId(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró el producto con ID: " + id));
    }

    // --- 3. BUSCAR POR CATEGORÍA ---
    public List<Productosdelcatalogo> buscarPorCategoria(String categoriaId) {
        List<Productosdelcatalogo> productos = productoRepository.findByCategoriaId(categoriaId);
        if (productos.isEmpty()) {
            throw new RuntimeException("No se encontraron productos para la categoría con ID: " + categoriaId);
        }
        return productos;
    }

    // --- 4. GUARDAR (Ropa o Música) ---
    @Transactional
    public Productosdelcatalogo guardarProducto(Productosdelcatalogo producto) {
        try {
            if (producto.getId() != null) {
                Optional<Productosdelcatalogo> existe = productoRepository.findById(producto.getId());
                if (existe.isPresent()) {
                    throw new RuntimeException("El producto con ID " + producto.getId() + " ya existe.");
                }
            }
            producto.enlazarAtributos();
            return productoRepository.save(producto);
        } catch (DataAccessException e) {
            throw new RuntimeException("Error interno al guardar en la base de datos.");
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error inesperado al guardar el producto.");
        }
    }

    // --- 5. ACTUALIZAR (Ropa o Música) ---
    @Transactional
    public Productosdelcatalogo actualizarProducto(Productosdelcatalogo datosNuevos) {
        if (datosNuevos.getId() == null) {
            throw new RuntimeException("El ID del producto es obligatorio para actualizar.");
        }

        try {
            Productosdelcatalogo productoEnBD = productoRepository.findById(datosNuevos.getId())
                    .orElseThrow(() -> new RuntimeException("No se encontró el producto con ID: " + datosNuevos.getId()));

            productoEnBD.setNombre_producto(datosNuevos.getNombre_producto());
            productoEnBD.setPrecio(datosNuevos.getPrecio());
            productoEnBD.setCategoria(datosNuevos.getCategoria());

            // Polimorfismo
            if (datosNuevos instanceof Ropa && productoEnBD instanceof Ropa) {
                Ropa ropaNueva = (Ropa) datosNuevos;
                Ropa ropaEnBD = (Ropa) productoEnBD;
                ropaEnBD.setMaterialcreacion(ropaNueva.getMaterialcreacion());
                ropaEnBD.setTalla(ropaNueva.getTalla());
            } else if (datosNuevos instanceof Musicafisica && productoEnBD instanceof Musicafisica) {
                Musicafisica musicaNueva = (Musicafisica) datosNuevos;
                Musicafisica musicaEnBD = (Musicafisica) productoEnBD;
                musicaEnBD.setTipoformato(musicaNueva.getTipoformato());
                musicaEnBD.setNumerocanciones(musicaNueva.getNumerocanciones());
                musicaEnBD.setAlbum(musicaNueva.getAlbum());
            }

            if (datosNuevos.getAtributos() != null) {
                productoEnBD.getAtributos().clear();
                productoEnBD.getAtributos().addAll(datosNuevos.getAtributos());
            }

            productoEnBD.enlazarAtributos();
            return productoRepository.save(productoEnBD);

        } catch (DataAccessException e) {
            throw new RuntimeException("Error interno al actualizar los datos.");
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error crítico al actualizar el producto.");
        }
    }


    // ========================================================================
    // 6. ELIMINAR PRODUCTO POR ID
    // =========================================================================
    @Transactional
    public boolean eliminarProducto(Long id) {
        if (productoRepository.existsById(id)) {
            productoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}