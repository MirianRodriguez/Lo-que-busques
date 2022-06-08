package edu.egg.loquebusques.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import edu.egg.loquebusques.entidades.Emprendimiento;

@Repository
public interface EmprendimientoRepositorio extends JpaRepository<Emprendimiento, Integer> {

    boolean existsByNombre(String nombre);

    @Modifying
    @Query("UPDATE Articulo a set a.eliminado = true where a.emprendimiento_id = ?1")
     void eliminarArticulosDelEmprendimiento(Integer id);
}
