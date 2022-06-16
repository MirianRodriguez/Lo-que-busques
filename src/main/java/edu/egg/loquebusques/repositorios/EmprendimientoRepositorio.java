package edu.egg.loquebusques.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;

import edu.egg.loquebusques.entidades.Emprendimiento;

@Repository
public interface EmprendimientoRepositorio extends JpaRepository<Emprendimiento, Integer> {

    boolean existsByNombre(String nombre);

    @Modifying
    @Query(value = "UPDATE articulo SET eliminado = 1 WHERE emprendimiento_id = ?1" , nativeQuery = true)
    void eliminarArticulosDelEmprendimiento(Integer emprendimiento_id);

    @Query(value = "SELECT * FROM emprendimiento WHERE nombre != null AND eliminado = false" , nativeQuery = true)
    List<Emprendimiento> obtenerTodosActivos();

    @Query(value = "SELECT * FROM emprendimiento where usuario_id = ?1" , nativeQuery = true)
    Optional<Emprendimiento> buscarPorUsuario(Integer id);

    @Query(value = "SELECT * FROM emprendimiento WHERE eliminado = 0 ORDER BY emprendimiento_id DESC LIMIT 6;", nativeQuery = true)
    List<Emprendimiento> obtenerMasRecientes();
}
