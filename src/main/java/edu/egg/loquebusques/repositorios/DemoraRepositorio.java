package edu.egg.loquebusques.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.egg.loquebusques.entidades.Demora;

@Repository
public interface DemoraRepositorio extends JpaRepository<Demora, Integer>{
    
}
