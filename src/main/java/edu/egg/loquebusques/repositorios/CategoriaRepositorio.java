package edu.egg.loquebusques.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepositorio extends JpaRepository<Categoria, Integer>{
    
    @Query(value = "SELECT count(*) FROM articulo WHERE categoria = ?1 AND eliminado = 0", nativeQuery = true)
    Integer referenciasEnArticulo(Integer id);

    @Query(value = "SELECT count(*) FROM emprendimiento WHERE categoria = ?1 AND eliminado = 0", nativeQuery = true)
    Integer referenciasEnEmprenimiento(Integer id);
}
