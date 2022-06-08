package edu.egg.loquebusques.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import edu.egg.loquebusques.entidades.Articulo;

@Repository
public interface ArticuloRepositorio extends JpaRepository<Articulo, Integer>{
    @Query(value = "SELECT * FROM articulo ORDER BY articulo_id DESC LIMIT 9;", nativeQuery = true)
    List<Articulo> obtenerMasRecientes();
}