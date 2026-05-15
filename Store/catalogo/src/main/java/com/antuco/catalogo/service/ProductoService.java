package com.antuco.catalogo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.antuco.catalogo.dto.ProductoDTO;
import com.antuco.catalogo.model.Album;
import com.antuco.catalogo.model.Merch;
import com.antuco.catalogo.model.Producto;
import com.antuco.catalogo.repository.AlbumRepo;
import com.antuco.catalogo.repository.MerchRepo;
import com.antuco.catalogo.repository.ProductoRepo;

@Service
public class ProductoService {
  
    @Autowired
    private ProductoRepo productoRepo;

    @Autowired
    private AlbumRepo albumRepo;

    @Autowired
    private MerchRepo merchRepo;

    public List<Producto> getAllProducts() {
        return productoRepo.findAll();
    }

    public Producto createProducto(ProductoDTO dto) {
        Producto producto = new Producto();
        producto.setSku(dto.getSku());
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock());
        producto.setEspecificacion(dto.getEspecificacion());

        // Logic: Is it an Album or Merch?
        if (dto.getAlbumId() != null) {
            Album album = albumRepo.findById(dto.getAlbumId())
                    .orElseThrow(() -> new RuntimeException("No hay album con ID: " + dto.getAlbumId()));
            producto.setAlbum(album);
        } 
        else if (dto.getMerchId() != null) {
            Merch merch = merchRepo.findById(dto.getMerchId())
                    .orElseThrow(() -> new RuntimeException("No hay merch con ID: " + dto.getMerchId()));
            producto.setMerch(merch);
        } 
        else {
            throw new RuntimeException("Product must be linked to either an Album or Merchandise.");
        }

        return productoRepo.save(producto);
    }

}
