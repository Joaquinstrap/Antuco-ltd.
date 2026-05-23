package com.example.antuco.inventario.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.example.antuco.inventario.Model.Inventariomodel;
import com.example.antuco.inventario.Repository.InventarioRepository;

import jakarta.transaction.Transactional;

@Service
public class ServiceInventario {

    @Autowired
    private InventarioRepository inventarioRepository;

    @Transactional
    public Inventariomodel obtenerPorProductoId(Long productoId){
        try{
            //Buscamos el producto
            return inventarioRepository.findByProductoId(productoId).orElseThrow(() -> new RuntimeException("Producto de id" + productoId  +"no se ha encontrado en el inventario"));

        }catch (DataAccessException e){
            throw new RuntimeException("Error al acceder a la base de datos: " + e.getMessage());
        }catch(Exception e){
            throw new RuntimeException("Error inesperado: " + e.getMessage());
        }
    }


    //Al crear un nuevo registro es crear un nuevo objeto
    //por lo tanto hay que colocarle los atributos que tendrea segun el modelo
    //ademas de validar si esque existe ya validandolo
    @Transactional
    public Inventariomodel CreacionNuevoRegistro(Long productoId, Integer cantidad, String tipoProducto){

        //lo encapsulamos con el try para manejar excepciones
        try{

            //ahora validamos si el producto ya existe en el inventario
            if(inventarioRepository.findByProductoId(productoId).isPresent()){
                throw new RuntimeException("El producto de id " + productoId + " Ya existe" );
            }
            //y si no se tiene registro del producto entonces se crea un nuevo registro
            Inventariomodel nuevoRegistro = new Inventariomodel(productoId, cantidad, tipoProducto);
            return inventarioRepository.save(nuevoRegistro);

        }catch (DataAccessException e){
            throw new RuntimeException("Error al acceder a la base de datos: " + e.getMessage());
        }catch(Exception e){
            throw new RuntimeException("Error inesperado: " + e.getMessage());
        }
    }


    //Ahora el metodo de incrementacion de stock
    @Transactional
    //para que podamos incrementarle la cantidad de stock a un producdo, debemos al metodo
    //primero que todo asignarle la id del producto al cual vamos a incrementar
    //y ademas la cantidad, que se llarama cantidadSuma, porque si se llama cantidad
    //reemplazaria la anterior entonces no se podria realizar correctamente lo que se desea hacer
    public Inventariomodel IncrementarStock(Long productoId, Integer cantidadSuma){
        try{
            if (cantidadSuma <= 0){
                throw new RuntimeException("La cantidad a sumar debe ser mayor que cero");
            }

            //para poder actualizar la cantidad deberemos llamar un atributo de clase inventariomodel
            Inventariomodel inventario = obtenerPorProductoId(productoId);
            inventario.setCantidad(inventario.getCantidad() + cantidadSuma);
            return inventarioRepository.save(inventario);
        
        }catch (DataAccessException e){
            throw new RuntimeException("Error al acceder a la base de datos: " + e.getMessage());
        }catch(Exception e){
            throw new RuntimeException("Error inesperado: " + e.getMessage());
        }
    }


    @Transactional
    public Inventariomodel DecrementarStock(Long productoId, Integer cantidadResta){
        try{
            if (cantidadResta <= 0){
                throw new RuntimeException("La cantidad a restarle al stock debe ser mayor a 0");

            }
            Inventariomodel inventario = obtenerPorProductoId(productoId);

            //aqui valido si el stock es 0 para que no se realize una resta invalida
            if (inventario.getCantidad().equals(0) ){
                throw new RuntimeException("No se puede restar si no hay stock disponible");
            }

            //aqui valido si la cantidad a restar es mayor a la cantidad disponible para que no se realice una resta invalida
            if (inventario.getCantidad() < cantidadResta) {
                throw new RuntimeException("Stock insuficiente. Unidades disponibles: " + inventario.getCantidad());
            }

            inventario.setCantidad(inventario.getCantidad() - cantidadResta);
            return inventarioRepository.save(inventario);


        }catch (DataAccessException e){
            throw new RuntimeException("Error al acceder a la base de datos: " + e.getMessage());
        }catch(Exception e){
            throw new RuntimeException("Error inesperado: " + e.getMessage());
        }

    }

    @Transactional
    public void EliminarRegistro(Long productoId){
        try{
            Inventariomodel inventario = obtenerPorProductoId(productoId);
            inventarioRepository.delete(inventario);
        }catch (DataAccessException e){
            throw new RuntimeException("Error al acceder a la base de datos: " + e.getMessage());
        }catch(Exception e){
            throw new RuntimeException("Error inesperado: " + e.getMessage());
        }
    }







}
