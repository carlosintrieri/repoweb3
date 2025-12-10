package com.autobots.automanager.controles;

import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/usuario")
@CrossOrigin(origins = "*")
public class UsuarioControle {

    @Autowired
    private UsuarioRepositorio repositorio;

    @GetMapping
    public ResponseEntity<CollectionModel<Usuario>> obterUsuarios() {
        List<Usuario> usuarios = repositorio.findAll();

        usuarios.forEach(usuario -> {
            usuario.add(linkTo(methodOn(UsuarioControle.class)
                    .obterUsuario(usuario.getId())).withSelfRel());
            usuario.add(linkTo(methodOn(UsuarioControle.class)
                    .obterUsuarios()).withRel("usuarios"));
        });

        Link selfLink = linkTo(methodOn(UsuarioControle.class).obterUsuarios()).withSelfRel();
        return ResponseEntity.ok(CollectionModel.of(usuarios, selfLink));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obterUsuario(@PathVariable Long id) {
        Optional<Usuario> optional = repositorio.findById(id);

        if (optional.isPresent()) {
            Usuario usuario = optional.get();
            usuario.add(linkTo(methodOn(UsuarioControle.class).obterUsuario(id)).withSelfRel());
            usuario.add(linkTo(methodOn(UsuarioControle.class).obterUsuarios()).withRel("usuarios"));
            return ResponseEntity.ok(usuario);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping
    public ResponseEntity<Usuario> cadastrarUsuario(@RequestBody Usuario usuario) {
        Usuario salvo = repositorio.save(usuario);
        salvo.add(linkTo(methodOn(UsuarioControle.class).obterUsuario(salvo.getId())).withSelfRel());
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuarioAtualizado) {
        Optional<Usuario> optional = repositorio.findById(id);

        if (optional.isPresent()) {
            Usuario usuario = optional.get();

            if (usuarioAtualizado.getNome() != null) {
                usuario.setNome(usuarioAtualizado.getNome());
            }
            if (usuarioAtualizado.getNomeSocial() != null) {
                usuario.setNomeSocial(usuarioAtualizado.getNomeSocial());
            }
            if (usuarioAtualizado.getDataNascimento() != null) {
                usuario.setDataNascimento(usuarioAtualizado.getDataNascimento());
            }
            if (usuarioAtualizado.getTipoUsuario() != null) {
                usuario.setTipoUsuario(usuarioAtualizado.getTipoUsuario());
            }

            if (usuarioAtualizado.getTelefones() != null) {
                usuario.getTelefones().clear();
                usuario.getTelefones().addAll(usuarioAtualizado.getTelefones());
            }

            if (usuarioAtualizado.getEmails() != null) {
                usuario.getEmails().clear();
                usuario.getEmails().addAll(usuarioAtualizado.getEmails());
            }

            if (usuarioAtualizado.getEndereco() != null) {
                usuario.setEndereco(usuarioAtualizado.getEndereco());
            }

            if (usuarioAtualizado.getEmpresa() != null) {
                usuario.setEmpresa(usuarioAtualizado.getEmpresa());
            }

            Usuario salvo = repositorio.save(usuario);
            salvo.add(linkTo(methodOn(UsuarioControle.class).obterUsuario(id)).withSelfRel());
            salvo.add(linkTo(methodOn(UsuarioControle.class).obterUsuarios()).withRel("usuarios"));

            return ResponseEntity.ok(salvo);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirUsuario(@PathVariable Long id) {
        if (repositorio.existsById(id)) {
            repositorio.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
