package edu.egg.loquebusques.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import edu.egg.loquebusques.entidades.Categoria;

@Repository
public interface CategoriaRepositorio extends JpaRepository<Categoria, Integer>{
    
    @Query(value = "SELECT count(*) FROM articulo WHERE categoria_id = ?1 AND eliminado = 0", nativeQuery = true)
    Integer referenciasEnArticulo(Integer id);

    @Query(value = "SELECT count(*) FROM emprendimiento_categorias WHERE categorias_categoria_id = ?1", nativeQuery = true)
    Integer referenciasEnEmprenimiento(Integer id);

    Optional<Categoria> findByNombre(String nombre);

    boolean existsByNombre(String nombre);
}
