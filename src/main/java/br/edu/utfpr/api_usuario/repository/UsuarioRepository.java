package br.edu.utfpr.api_usuario.repository;

import br.edu.utfpr.api_usuario.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {}
