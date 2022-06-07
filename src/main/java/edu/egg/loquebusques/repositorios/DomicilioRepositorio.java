package edu.egg.loquebusques.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.egg.loquebusques.entidades.Domicilio;

@Repository
public interface DomicilioRepositorio extends JpaRepository<Domicilio, Integer>{
    
}
