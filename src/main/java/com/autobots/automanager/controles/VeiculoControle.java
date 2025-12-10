package com.autobots.automanager.controles;

import com.autobots.automanager.entidades.Veiculo;
import com.autobots.automanager.repositorios.VeiculoRepositorio;
import com.autobots.automanager.repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/veiculo")
@CrossOrigin(origins = "*")
public class VeiculoControle {

    @Autowired
    private VeiculoRepositorio repositorio;
    
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @GetMapping
    public ResponseEntity<CollectionModel<Veiculo>> obterVeiculos() {
        List<Veiculo> veiculos = repositorio.findAll();
        for (Veiculo veiculo : veiculos) {
            veiculo.add(linkTo(methodOn(VeiculoControle.class).obterVeiculo(veiculo.getId())).withSelfRel());
        }
        Link selfLink = linkTo(methodOn(VeiculoControle.class).obterVeiculos()).withSelfRel();
        return ResponseEntity.ok(CollectionModel.of(veiculos, selfLink));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Veiculo> obterVeiculo(@PathVariable Long id) {
        return repositorio.findById(id)
                .map(veiculo -> {
                    veiculo.add(linkTo(methodOn(VeiculoControle.class).obterVeiculo(id)).withSelfRel());
                    return ResponseEntity.ok(veiculo);
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<?> cadastrarVeiculo(@RequestBody Veiculo veiculo) {
        // Validar se proprietario existe (se fornecido)
        if (veiculo.getProprietario() != null && veiculo.getProprietario().getId() != null) {
            Long proprietarioId = veiculo.getProprietario().getId();
            if (!usuarioRepositorio.existsById(proprietarioId)) {
                return ResponseEntity.badRequest()
                    .body("Erro: Usuário com id " + proprietarioId + " não existe. Cadastre o usuário primeiro.");
            }
        }
        
        Veiculo salvo = repositorio.save(veiculo);
        salvo.add(linkTo(methodOn(VeiculoControle.class).obterVeiculo(salvo.getId())).withSelfRel());
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarVeiculo(@PathVariable Long id, @RequestBody Veiculo veiculoAtualizado) {
        return repositorio.findById(id)
                .map(veiculo -> {
                    if (veiculoAtualizado.getTipo() != null) {
                        veiculo.setTipo(veiculoAtualizado.getTipo());
                    }
                    if (veiculoAtualizado.getModelo() != null) {
                        veiculo.setModelo(veiculoAtualizado.getModelo());
                    }
                    if (veiculoAtualizado.getPlaca() != null) {
                        veiculo.setPlaca(veiculoAtualizado.getPlaca());
                    }
                    if (veiculoAtualizado.getProprietario() != null) {
                        // Validar se proprietario existe
                        if (veiculoAtualizado.getProprietario().getId() != null) {
                            Long proprietarioId = veiculoAtualizado.getProprietario().getId();
                            if (!usuarioRepositorio.existsById(proprietarioId)) {
                                return ResponseEntity.badRequest()
                                    .body("Erro: Usuário com id " + proprietarioId + " não existe.");
                            }
                        }
                        veiculo.setProprietario(veiculoAtualizado.getProprietario());
                    }
                    Veiculo salvo = repositorio.save(veiculo);
                    salvo.add(linkTo(methodOn(VeiculoControle.class).obterVeiculo(id)).withSelfRel());
                    return ResponseEntity.ok(salvo);
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirVeiculo(@PathVariable Long id) {
        if (repositorio.existsById(id)) {
            repositorio.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
