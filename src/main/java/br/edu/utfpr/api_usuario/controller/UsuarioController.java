package br.edu.utfpr.api_usuario.controller;

import br.edu.utfpr.api_usuario.model.Usuario;
import br.edu.utfpr.api_usuario.repository.UsuarioRepository;
import br.edu.utfpr.api_usuario.dto.UsuarioDTO;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> create(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        Usuario novoUsuario = mapToEntity(usuarioDTO);
        novoUsuario.setCreatedAt(LocalDateTime.now());

        Usuario savedUsuario = usuarioRepository.save(novoUsuario);

        return ResponseEntity.created(null).body(mapToDTO(savedUsuario));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> getAll() {
        List<UsuarioDTO> listaDTOs = usuarioRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(listaDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> getById(@PathVariable Long id) {
        return usuarioRepository.findById(id)
                .map(usuario -> ResponseEntity.ok(mapToDTO(usuario)))
                .orElse(ResponseEntity.notFound().build());
    }
    
    private UsuarioDTO mapToDTO(Usuario usuario) {
        return new UsuarioDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getPassword()
        );
    }

    private Usuario mapToEntity(UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setNome(usuarioDTO.nome());
        usuario.setEmail(usuarioDTO.email());
        usuario.setPassword(usuarioDTO.password());
        return usuario;
    }
}

