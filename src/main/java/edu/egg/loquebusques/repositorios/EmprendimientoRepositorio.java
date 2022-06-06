package edu.egg.loquebusques.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.egg.loquebusques.entidades.Emprendimiento;

@Repository
public interface EmprendimientoRepositorio extends JpaRepository<Emprendimiento, Integer>{
    
}
