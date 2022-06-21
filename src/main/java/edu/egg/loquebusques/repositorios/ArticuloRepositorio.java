package edu.egg.loquebusques.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import edu.egg.loquebusques.entidades.Articulo;

@Repository
public interface ArticuloRepositorio extends JpaRepository<Articulo, Integer>{
    @Query(value = "SELECT * FROM articulo WHERE eliminado = 0 ORDER BY articulo_id DESC LIMIT 9;", nativeQuery = true)
    List<Articulo> obtenerMasRecientes();

    @Query(value = "SELECT * FROM articulo WHERE emprendimiento_id = ?1 AND eliminado = 0;" , nativeQuery = true)
    List<Articulo> articulosDeUnEmprendimiento(Integer emprendimiento_id);

    @Query(value = "SELECT * FROM articulo WHERE categoria_id = ?1 AND eliminado = false" , nativeQuery = true)
    List<Articulo>buscarPorCategoria(Integer categoria_id);
}