package edu.egg.loquebusques.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.egg.loquebusques.entidades.Articulo;

@Repository
public interface ArticuloRepositorio extends JpaRepository<Articulo, Integer>{
    
}