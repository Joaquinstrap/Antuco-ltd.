package com.tienda.catalogo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tienda.catalogo.Productos.Ropa;
import com.tienda.catalogo.Repo.repo;
import jakarta.transaction.Transactional;
import jakarta.validation.*;

import java.util.Set;
import java.util.List;


@Service
@Transactional
public class servicio {

    @Autowired
    private repo repositorio;

    private Validator validated;


    public List<Ropa> Listaropa(){
        return repositorio.findAll();
    }


    public Ropa buscar_por_id(Long Id){
        return repositorio.findById(Id).orElse(null);
    }

    public Ropa guardarropa(Ropa ropita){
        Set<ConstraintViolation<Ropa>> violations = validated.validate(ropita);
        
        if(!violations.isEmpty()){
            String mensaje = violations.iterator().next().getMessage();
            throw new IllegalArgumentException(mensaje);
        }

        return repositorio.save(ropita);
    }

    public Boolean eliminar(Long id){
        if (repositorio.existsById(id)){

            return true;
            //Este return le verifica al controller que se realizo la accion correctamente

        }else{
            return false;
            //y este que no
        }
        
    }


    

}
