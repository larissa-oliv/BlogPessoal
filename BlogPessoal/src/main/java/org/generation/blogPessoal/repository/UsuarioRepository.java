package org.generation.blogPessoal.repository;

import org.generation.blogPessoal.model.*;
import org.springframework.data.jpa.repository.*;

import java.util.*;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    public static Optional<Usuario> findByUsuario(String usuario);

    public static List <Usuario> findAllByNomeContainingIgnoreCase(String nome);
}
