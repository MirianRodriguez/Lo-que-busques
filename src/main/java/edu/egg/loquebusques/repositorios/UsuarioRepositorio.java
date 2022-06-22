package edu.egg.loquebusques.repositorios;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import edu.egg.loquebusques.entidades.Usuario;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer> {

    boolean existsByEmail(String email);

    @Query(value = "SELECT * FROM usuario WHERE email = ?1 AND eliminado = 0;" , nativeQuery = true)
    Optional<Usuario> findByEmail(String email);
}
