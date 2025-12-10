package com.autobots.automanager.controles;

import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.repositorios.MercadoriaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/mercadoria")
@CrossOrigin(origins = "*")
public class MercadoriaControle {

    @Autowired
    private MercadoriaRepositorio repositorio;

    @GetMapping
    public ResponseEntity<CollectionModel<Mercadoria>> obterMercadorias() {
        List<Mercadoria> mercadorias = repositorio.findAll();
        for (Mercadoria mercadoria : mercadorias) {
            mercadoria.add(linkTo(methodOn(MercadoriaControle.class).obterMercadoria(mercadoria.getId())).withSelfRel());
        }
        Link selfLink = linkTo(methodOn(MercadoriaControle.class).obterMercadorias()).withSelfRel();
        return ResponseEntity.ok(CollectionModel.of(mercadorias, selfLink));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mercadoria> obterMercadoria(@PathVariable Long id) {
        return repositorio.findById(id)
                .map(mercadoria -> {
                    mercadoria.add(linkTo(methodOn(MercadoriaControle.class).obterMercadoria(id)).withSelfRel());
                    return ResponseEntity.ok(mercadoria);
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<Mercadoria> cadastrarMercadoria(@RequestBody Mercadoria mercadoria) {
        mercadoria.setCadastro(new Date());
        Mercadoria salva = repositorio.save(mercadoria);
        salva.add(linkTo(methodOn(MercadoriaControle.class).obterMercadoria(salva.getId())).withSelfRel());
        return ResponseEntity.status(HttpStatus.CREATED).body(salva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mercadoria> atualizarMercadoria(@PathVariable Long id, @RequestBody Mercadoria mercadoriaAtualizada) {
        return repositorio.findById(id)
                .map(mercadoria -> {
                    if (mercadoriaAtualizada.getNome() != null) {
                        mercadoria.setNome(mercadoriaAtualizada.getNome());
                    }
                    if (mercadoriaAtualizada.getValor() != null) {
                        mercadoria.setValor(mercadoriaAtualizada.getValor());
                    }
                    if (mercadoriaAtualizada.getQuantidade() != null) {
                        mercadoria.setQuantidade(mercadoriaAtualizada.getQuantidade());
                    }
                    if (mercadoriaAtualizada.getDescricao() != null) {
                        mercadoria.setDescricao(mercadoriaAtualizada.getDescricao());
                    }
                    Mercadoria salva = repositorio.save(mercadoria);
                    salva.add(linkTo(methodOn(MercadoriaControle.class).obterMercadoria(id)).withSelfRel());
                    return ResponseEntity.ok(salva);
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirMercadoria(@PathVariable Long id) {
        if (repositorio.existsById(id)) {
            repositorio.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
