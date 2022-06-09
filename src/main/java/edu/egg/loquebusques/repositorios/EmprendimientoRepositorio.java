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
    @Query(value = "UPDATE articulo set eliminado = true where emprendimiento_id = ?1;" , nativeQuery = true)
    void eliminarArticulosDelEmprendimiento(Integer id);

}
